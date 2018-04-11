package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class DocRegActivity extends AppCompatActivity {
    private String mPasswordConfirm, mPassword, mEmail;
    private EditText mPasswordConfirmE, mPasswordE, mEmailE;
    private Button regBtn;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_reg);
        //mPassword=mPasswordConfirm="";
        regBtn = (Button) findViewById(R.id.SubmitButton);
        mEmailE = (EditText) findViewById(R.id.doctorEmail);
        mPasswordConfirmE = (EditText) findViewById(R.id.doctorPassRegConfirm);
        mPasswordE = (EditText) findViewById(R.id.doctorPassReg);

        mPasswordConfirmE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPassword = mPasswordE.getText().toString();
                mPasswordConfirm = mPasswordConfirmE.getText().toString();

                if (charSequence.toString().trim().length() == 0 || mPassword.trim().length() == 0 || mPassword.length() < 8) {
                    regBtn.setEnabled(false);
                } else {
                    if (mPassword.equals(mPasswordConfirm))
                        regBtn.setEnabled(true);
                    else
                        regBtn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPassword = mPasswordE.getText().toString();
                mPasswordConfirm = mPasswordConfirmE.getText().toString();

                if (charSequence.toString().trim().length() == 0 || mPassword.trim().length() == 0 || mPassword.length() < 8) {
                    regBtn.setEnabled(false);
                } else {
                    if (mPassword.equals(mPasswordConfirm))
                        regBtn.setEnabled(true);
                    else
                        regBtn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPasswordConfirm = mPasswordConfirmE.getText().toString();

            }
        });
        mPasswordE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPasswordConfirm = mPasswordConfirmE.getText().toString();
                mPassword = mPasswordE.getText().toString();

                if (charSequence.toString().trim().length() == 0 || mPasswordConfirm.equals("") || mPassword.length() < 8) {
                    regBtn.setEnabled(false);
                } else {
                    if (mPassword.equals(mPasswordConfirm))
                        regBtn.setEnabled(true);
                    else
                        regBtn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPasswordConfirm = mPasswordConfirmE.getText().toString();
                mPassword = mPasswordE.getText().toString();

                if (charSequence.toString().trim().length() == 0 || mPasswordConfirm.equals("") || mPassword.length() < 8) {
                    regBtn.setEnabled(false);
                } else {
                    if (mPassword.equals(mPasswordConfirm))
                        regBtn.setEnabled(true);
                    else
                        regBtn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPassword = mPasswordE.getText().toString();

            }
        });
        progressDialog = new ProgressDialog(this);

        builder = new AlertDialog.Builder(DocRegActivity.this);
        //**************************************************//

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail = mEmailE.getText().toString();
                mPassword = mPasswordE.getText().toString();
                mPasswordConfirm = mPasswordConfirmE.getText().toString();
                progressDialog.setMessage("Signing up...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DOCTOR_REG_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            builder.setTitle("Response");
                            builder.setMessage(message);
                            displayAlert(code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        builder.setTitle("Response");
                        builder.setMessage("Error on Connection !");
                        displayAlert("Fail");
                        Log.d("Response", "" + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Password", mPassword);
                        params.put("Email", mEmail);
                        return params;
                    }
                };
                MySingleton.getmInstance(DocRegActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    private void displayAlert(final String code) {
        progressDialog.dismiss();
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("Done") || code.equals("AlreadyCreated")) {
                    finish();
                } else if (code.equals("Fail")) {
                    mPasswordConfirmE.setText("");
                    mPasswordE.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
