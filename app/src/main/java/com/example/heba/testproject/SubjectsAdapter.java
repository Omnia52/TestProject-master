package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Heba on 2/25/2018.
 */

public class SubjectsAdapter extends ArrayAdapter<SubjectData> {
    private int flag = 0;
    private ArrayList<String> questionArray;
    private Context mCtx;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;

    public SubjectsAdapter(@NonNull Context context, @NonNull List<SubjectData> objects) {
        super(context, 0, objects);
        mCtx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.mylist, parent, false);
        }
        final SubjectData currentSubject = getItem(position);
        final TextView subjectView = (TextView) listItemView.findViewById(R.id.subjectCode);
        Button eval1 = (Button) listItemView.findViewById(R.id.eval1);
        Button eval2 = (Button) listItemView.findViewById(R.id.eval2);
        subjectView.setText(currentSubject.getSubjectCode());
        eval1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                clickButton(currentSubject.getSubjectCode());
            }
        });
        eval2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                clickButton(currentSubject.getSubjectCode());
            }
        });
        return listItemView;
    }



    private void clickButton(final String subjectCode){
        progressDialog  = new ProgressDialog(mCtx);
        builder = new AlertDialog.Builder(mCtx);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        StringRequest stringRequest;

        if(flag==0){
            checkActived(Constants.IS_ACTIVE_EVAL1,subjectCode);
        }
        else{
            checkActived(Constants.IS_ACTIVE_EVAL2,subjectCode);
        }

    }

    private void checkActived(String URL,final String subjectCode){

        StringRequest stringRequest=new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    displayAlertActive(jsonObject.getString("message"),subjectCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyToast.viewToast("Error !",mCtx);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyToast.viewToast("Error in connection !",mCtx);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String> params=new HashMap<>();
                params.put("Acc",SharedPrefManager.getmInstance(mCtx).getUserAcc());
                params.put("CourseCode",subjectCode);
                return params;
            }
        };
        MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);

    }


    private void displayAlertActive(String message,String subjectCode){
        progressDialog.dismiss();
        if(message.equals("Actived")){
            questionArray =new ArrayList<>();
            if(flag==0)
                makeQuestionsList(Constants.Eval1_Questions_URL,"before",subjectCode);
            else if(flag==1)
                makeQuestionsList(Constants.Eval2_Questions_URL,"after",subjectCode);
        }
        else if(message.equals("Disactived")){
            MyToast.viewToast("The Evaluation isn't active!",mCtx);
        }
        else if(message.equals("The Evaluation is Solved")||message.equals("Error!")){
            MyToast.viewToast(message,mCtx);
        }
    }

    private void makeQuestionsList(String URL,final String type, final String subjectCode){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int count = 0; count < jsonArray.length(); count++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        questionArray.add(jsonObject.getString("Question").toString());
                    }
                    displayAlert(type,subjectCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyToast.viewToast("Error in connection !",mCtx);

            }
        });
        MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
    }

    private void displayAlert(String type,String subjectCode){
        if(questionArray.size()==0){
            builder.setTitle("Fetching data Error !");
            builder.setMessage("Error !");
            builder.setPositiveButton("Ok",null);
        }
        else{
            SharedPrefManager.getmInstance(mCtx).setQuestions(questionArray,type,subjectCode);
            mCtx.startActivity(new Intent(mCtx,eval_questionsActivity.class));
        }
    }
}