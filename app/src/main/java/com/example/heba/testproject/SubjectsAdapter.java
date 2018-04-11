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
                enableButton(currentSubject.getDoneEval1(), currentSubject.getActiveEval1());
            }
        });
        eval2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                enableButton(currentSubject.getDoneEval2(), currentSubject.getActiveEval2());
            }
        });
        return listItemView;
    }



    private void enableButton(String mDoneEval,String mActiveEval){

        if(mDoneEval.equals("1")){
            MyToast.viewToast("The Evaluation Solved",mCtx);
        }
        else{
            if(mActiveEval.equals("1")){
                questionArray =new ArrayList<>();
                progressDialog  = new ProgressDialog(mCtx);
                builder = new AlertDialog.Builder(mCtx);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                StringRequest stringRequest;
                if(flag==0) {
                    stringRequest = new StringRequest(Request.Method.POST, Constants.Eval1_Questions_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int count = 0; count < jsonArray.length(); count++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                                    questionArray.add(jsonObject.getString("Question").toString());
                                }
                                displayAlert();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
                else{
                    stringRequest = new StringRequest(Request.Method.POST, Constants.Eval2_Questions_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int count = 0; count < jsonArray.length(); count++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                                    questionArray.add(jsonObject.getString("Question").toString());
                                }
                                displayAlert();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
                MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);

            }

            else{
                MyToast.viewToast("The Evaluation is not Active Now",getContext());
            }
        }
    }


    private void displayAlert(){
        progressDialog.dismiss();
        if(questionArray.size()==0){
            builder.setTitle("Fetching data Error !");
            builder.setMessage("Error !");
            builder.setPositiveButton("Ok",null);
        }
        else{
            SharedPrefManager.getmInstance(mCtx).setQuestions(questionArray);
            mCtx.startActivity(new Intent(mCtx,eval_questionsActivity.class));
        }
    }
}