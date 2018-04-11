package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.Map;

public class DocLoginActivity extends AppCompatActivity {

    private String mDocAcc,mDocPass;
    private EditText mDocAccE,mDocPassE;
    private Button mLoginBtn;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    ArrayList<String> subjectList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login);

        mDocAccE=(EditText) findViewById(R.id.doctorAcc);
        mDocPassE=(EditText) findViewById(R.id.doctorPass);
        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mDocAcc=mDocPass="" ;

        mDocAccE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDocAcc=mDocAccE.getText().toString();
                mDocPass=mDocPassE.getText().toString();
                if(mDocPass.length()>=8&&mDocAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDocAcc=mDocAccE.getText().toString();
                mDocPass=mDocPassE.getText().toString();
                if(mDocPass.length()>=8&&mDocAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDocPassE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDocAcc=mDocAccE.getText().toString();
                mDocPass=mDocPassE.getText().toString();
                if(mDocPass.length()>=8&&mDocAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDocAcc=mDocAccE.getText().toString();
                mDocPass=mDocPassE.getText().toString();
                if(mDocPass.length()>=8&&mDocAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //----------------------------------------------------------------------//

        builder = new AlertDialog.Builder(DocLoginActivity.this);
        progressDialog=new ProgressDialog(this);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!SharedPrefManager.getmInstance(DocLoginActivity.this).isLogged()){
                    mDocAcc=mDocAccE.getText().toString();
                    mDocPass=mDocPassE.getText().toString();
                }
                else{
                    mDocAcc=SharedPrefManager.getmInstance(DocLoginActivity.this).getUserAcc().toString();
                    mDocPass=SharedPrefManager.getmInstance(DocLoginActivity.this).getUserPass().toString();
                }
                progressDialog.setMessage("Signing in...");
                progressDialog.show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.DOCTOR_LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            for(int count=1;count<jsonArray.length();count++){
                                JSONObject jsonObject2 = jsonArray.getJSONObject(count);
                                subjectList.add(jsonObject2.getString("subjectCode").toString());
                                subjectList.add(jsonObject2.getString("ActiveEval1").toString());
                                subjectList.add(jsonObject2.getString("ActiveEval2").toString());
                            }

                            displayAlert(jsonObject.getString("code"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayAlert("Fail","Connection Error !");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String , String> params=new HashMap<String, String>();
                        params.put("Acc",mDocAcc);
                        params.put("Password",mDocPass);
                        return params;
                    }
                };
                MySingleton.getmInstance(DocLoginActivity.this).addToRequestQueue(stringRequest);
            }
        });

        if(SharedPrefManager.getmInstance(this).isLogged()){
            mLoginBtn.callOnClick();
            return;
        }
    }

    public void displayAlert(String code , String message){
        progressDialog.dismiss();
        if(code.equals("Success")){
            SharedPrefManager.getmInstance(this).userLogin(mDocAcc,subjectList,mDocPass,"Doc");
            startActivity(new Intent(DocLoginActivity.this,Subject_DoctorActivity.class));
            finish();
        }
        else if(message.equals("Wrong Password!")){
            builder.setTitle("Login Error !");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDocPassE.setText("");
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        else{
            builder.setTitle("Login Error !");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDocAccE.setText("");
                    mDocPassE.setText("");
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    public void regDoc(View view) {
        startActivity(new Intent(this, DocRegActivity.class).putExtra("Acc",mDocAcc));
    }
}
