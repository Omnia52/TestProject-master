package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

/**
 * Created by SM on 4/8/2018.
 */

public class DoctorSubjectAdapter extends ArrayAdapter<SubjectData>{

    //private Button active1,active2;
    private Context mCtx;
    private ProgressDialog progressDialog;

    public DoctorSubjectAdapter(@NonNull Context context, @NonNull List<SubjectData> objects) {
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
        //active1 = (Button)listItemView.findViewById(R.id.active1);
        //active2 = (Button)listItemView.findViewById(R.id.active2);
        final Button lactive1,lactive2;
        lactive1=(Button)listItemView.findViewById(R.id.active1);
        lactive2=(Button)listItemView.findViewById(R.id.active2);

        subjectView.setText(currentSubject.getSubjectCode());

        setButtonText(currentSubject.getActiveEval1(),lactive1,1);

        setButtonText(currentSubject.getActiveEval2(),lactive2,2);


        progressDialog =new ProgressDialog(mCtx);

        lactive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enableButton(currentSubject.getActiveEval1());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Active_Eval_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.setMessage("Please Wait...");
                                progressDialog.show();
                                try {
                                    JSONArray jsonArray=new JSONArray(response);
                                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    if(jsonObject.getString("message").equals("Success")){
                                        if(lactive1.getText().equals("DisActive Eval 1")){
                                            lactive1.setText("Active Eval 1");
                                        }
                                        else if (lactive1.getText().equals("Active Eval 1")){
                                            lactive1.setText("DisActive Eval 1");
                                        }
                                        progressDialog.dismiss();

                                    }
                                    else{
                                        MyToast.viewToast("There is an Error!",mCtx);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                MyToast.viewToast("Error In Connection ! ",mCtx);
                            }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("subjectCode",currentSubject.getSubjectCode());
                        params.put("evalNo",""+1);
                        params.put("activeStat",lactive1.getText().toString());
                        return params;
                    }
                };
                MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


            }
        });
        lactive2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enableButton(currentSubject.getActiveEval2());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Active_Eval_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.setMessage("Please Wait...!");
                                progressDialog.show();
                                try {
                                 JSONArray   jsonArray = new JSONArray(response);
                                 JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    if(jsonObject.getString("message").equals("Success")){
                                        if(lactive2.getText().equals("DisActive Eval 2")){
                                            lactive2.setText("Active Eval 2");
                                        }
                                        else if (lactive2.getText().equals("Active Eval 2")){
                                            lactive2.setText("DisActive Eval 2");
                                        }
                                        progressDialog.dismiss();
                                    }
                                    else{
                                        MyToast.viewToast("There is an Error!",mCtx);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyToast.viewToast("Error In Connection",mCtx);

                    }
                })
                {   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("subjectCode",currentSubject.getSubjectCode());
                        params.put("evalNo",""+2);
                        params.put("activeStat",lactive2.getText().toString());
                        return params;
                    }
                };
                MySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
            }
        });
        return listItemView;
    }
    private void enableButton(String mActive) {
        if(mActive.equals("1")){
            Toast.makeText(getContext(),"Is Already Active",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Active", Toast.LENGTH_LONG).show();


        }
    }

    private void setButtonText(String mActive,Button mBtn,int n){
        if(n==1){
            if(mActive.equals("1"))
                mBtn.setText("DisActive Eval 1");
        }
        else if(n==2){
            if(mActive.equals("1"))
                mBtn.setText("DisActive Eval 2");
        }
    }

}
