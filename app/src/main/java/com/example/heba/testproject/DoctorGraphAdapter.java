package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Heba on 4/22/2018.
 */

public class DoctorGraphAdapter extends ArrayAdapter<SubjectData> {


    private Context mCtx;
    private ProgressDialog progressDialog;

    private ArrayList<String> yValues,xValues;

    public DoctorGraphAdapter(@NonNull Context context, @NonNull List<SubjectData> objects) {
        super(context, 0, objects);
        mCtx=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.doc_list, parent, false);
        }


        final SubjectData currentSubject = getItem(position);
        final TextView subjectView = (TextView) listItemView.findViewById(R.id.subjectCodeDoc);
        final Button Graph1,Graph2;
        Graph1=(Button)listItemView.findViewById(R.id.active1);
        Graph2=(Button)listItemView.findViewById(R.id.active2);

        Graph1.setText("Graph 1");
        Graph2.setText("Graph 2");
        subjectView.setText(currentSubject.getSubjectCode());

        progressDialog =new ProgressDialog(mCtx);

        Graph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.GET_GRAPH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            ///-----------------------------------------------------------///
                            //-----------if we need to change the number don't forget to change in the displayAlert method------------///
                            if(Integer.parseInt(jsonObject.getString("Count"))>=3){
                                yValues=new ArrayList<>();
                                xValues=new ArrayList<>();
                                for(int i=1;i<jsonArray.length();i++){
                                    JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                    yValues.add(jsonObject2.getString("Average"));
                                    xValues.add(jsonObject2.getString("X"));
                                }
                                displayAlert("Success","before",currentSubject.getSubjectCode());
                            }
                            else {
                                displayAlert("Less","before",currentSubject.getSubjectCode());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        MyToast.viewToast("Error in connection",mCtx);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("courseCode",currentSubject.getSubjectCode());
                        params.put("evalType","before");
                        return params;
                    }
                };
                MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
            }
        });

        Graph2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.GET_GRAPH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            ///-----------------------------------------------------------///
                            //-----------if we need to change the number don't forget to change in the displayAlert method------------///
                            if(Integer.parseInt(jsonObject.getString("Count"))>=3){
                                yValues=new ArrayList<>();
                                xValues=new ArrayList<>();
                                for(int i=1;i<jsonArray.length();i++){
                                    JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                    yValues.add(jsonObject2.getString("Average"));
                                    xValues.add(jsonObject2.getString("X"));
                                }
                                displayAlert("Success","after",currentSubject.getSubjectCode());
                            }
                            else {
                                displayAlert("Less","after",currentSubject.getSubjectCode());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            MyToast.viewToast("Error in connection",mCtx);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        MyToast.viewToast("Error in connection",mCtx);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("courseCode",currentSubject.getSubjectCode());
                        params.put("evalType","after");
                        return params;
                    }
                };
                MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
            }
        });
        return listItemView;
    }
    private void displayAlert(String message, String evalType,String courseCode){
        progressDialog.dismiss();
        if(message.equals("Success")){
            SharedPrefManager.getmInstance(mCtx).setValues(xValues,yValues,evalType,courseCode);
            mCtx.startActivity(new Intent(mCtx,DoctorGraphActivity.class));
        }
        else{
            MyToast.viewToast("Less than 3 answered",mCtx);
        }
    }

}
