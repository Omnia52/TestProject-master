package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static java.lang.Thread.sleep;

public class Subject_StudentActivity extends AppCompatActivity {
    private String Acc;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__student);
        if (!SharedPrefManager.getmInstance(this).isLogged()) {
            finish();
        }

        ArrayList<SubjectData> subjects ;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait until fetching your data...");
        progressDialog.show();

        Acc = SharedPrefManager.getmInstance(this).getUserAcc();

        String subjectList = SharedPrefManager.getmInstance(this).getSubjectList();

        subjectList=subjectList.substring(1,subjectList.length()-1);

        if (subjectList.equals(""))
            Toast.makeText(this, "Please Check your data in the department... !", Toast.LENGTH_LONG).show();
        else {
            subjects = makeList(subjectList);
            final ListView listView = (ListView) findViewById(R.id.list);
            SubjectsAdapter subjectsAdapter = new SubjectsAdapter(this, subjects);
            listView.setAdapter(subjectsAdapter);
        }
        progressDialog.dismiss();
    }

    private ArrayList<SubjectData> makeList(String list){
        ArrayList<SubjectData> temp=new ArrayList<>();
        String [] array=list.split(",");
        for(int i=0;i<array.length;) {
            SubjectData subjectData = new SubjectData();
            subjectData.setSubjectCode(array[i++].trim());
            subjectData.setDoneEval1(array[i++].trim());
            subjectData.setDoneEval2(array[i++].trim());
            subjectData.setActiveEval1(array[i++].trim());
            subjectData.setActiveEval2(array[i++].trim());
            temp.add(subjectData);
        }
        return temp;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:
                SharedPrefManager.getmInstance(this).logout();
                startActivity(new Intent(Subject_StudentActivity.this,ChooseActivity.class));
                finish();
                break;
            case R.id.menuSetting:
                MyToast.viewToast("You Pressed Settings",this);
                break;
        }
        return true;
    }


    /*private void view_toast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,(ViewGroup)findViewById(R.id.custom_toast_container));
        TextView text = (TextView)layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }*/
}
