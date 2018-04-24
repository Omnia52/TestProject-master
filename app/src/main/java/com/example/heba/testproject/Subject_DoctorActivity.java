package com.example.heba.testproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/*
public class Subject_DoctorActivity extends AppCompatActivity {
    private String Acc;
    private ProgressDialog progressDialog;
    private Button graphBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__doctor);
        if (!SharedPrefManager.getmInstance(this).isLogged()) {
            finish();
        }
        graphBtn = (Button) findViewById(R.id.result);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Subject_DoctorActivity.this,SelectGraphActivity.class));
            }
        });
        ArrayList<SubjectData> subjects ;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait until fetching your data...");
        progressDialog.show();

        Acc = SharedPrefManager.getmInstance(this).getUserAcc();

        String subjectList = SharedPrefManager.getmInstance(this).getSubjectList();

        subjectList=subjectList.substring(1,subjectList.length()-1);

        if(subjectList.equals("")){
            Toast.makeText(this,"Please Check your data in the department... !",Toast.LENGTH_LONG).show();
        }
        else{
            subjects = makeList(subjectList);
            final ListView listView = (ListView) findViewById(R.id.listDoc);
            DoctorSubjectAdapter doctorSubjectAdapter = new DoctorSubjectAdapter(this, subjects);
            listView.setAdapter(doctorSubjectAdapter);
        }
        progressDialog.dismiss();
    }
    private ArrayList<SubjectData> makeList(String list){
        ArrayList<SubjectData> temp=new ArrayList<>();
        String [] array=list.split(",");
        for(int i=0;i<array.length;) {
            SubjectData subjectData = new SubjectData();
            subjectData.setSubjectCode(array[i++].trim());
            subjectData.setActiveEval1(array[i++].trim());
            subjectData.setActiveEval2(array[i++].trim());
            subjectData.setDoneEval1("");
            subjectData.setDoneEval2("");
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
                startActivity(new Intent(Subject_DoctorActivity.this,ChooseActivity.class));
                finish();
                break;
            case R.id.menuSetting:
                MyToast.viewToast("You Pressed Settings",this);
                break;
        }
        return true;
    }

}
*/

public class Subject_DoctorActivity extends android.support.v4.app.Fragment {
    private String Acc;
    private ProgressDialog progressDialog;
    private Button graphBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_subject__doctor, container, false);
        if (!SharedPrefManager.getmInstance(getActivity()).isLogged()) {
            getActivity().finish();
        }
        ArrayList<SubjectData> subjects ;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait until fetching your data...");
        progressDialog.show();

        Acc = SharedPrefManager.getmInstance(getActivity()).getUserAcc();

        String subjectList = SharedPrefManager.getmInstance(getActivity()).getSubjectList();

        subjectList=subjectList.substring(1,subjectList.length()-1);

        if(subjectList.equals("")){
            Toast.makeText(getActivity(),"Please Check your data in the department... !",Toast.LENGTH_LONG).show();
        }
        else{
            subjects = makeList(subjectList);
            final ListView listView = (ListView) rootView.findViewById(R.id.listDoc);
            DoctorSubjectAdapter doctorSubjectAdapter = new DoctorSubjectAdapter(getActivity(), subjects);
            listView.setAdapter(doctorSubjectAdapter);
        }
        progressDialog.dismiss();
        return rootView;

    }

    private ArrayList<SubjectData> makeList(String list){
        ArrayList<SubjectData> temp=new ArrayList<>();
        String [] array=list.split(",");
        for(int i=0;i<array.length;) {
            SubjectData subjectData = new SubjectData();
            subjectData.setSubjectCode(array[i++].trim());
            subjectData.setActiveEval1(array[i++].trim());
            subjectData.setActiveEval2(array[i++].trim());
            subjectData.setDoneEval1("");
            subjectData.setDoneEval2("");
            temp.add(subjectData);
        }
        return temp;
    }

}