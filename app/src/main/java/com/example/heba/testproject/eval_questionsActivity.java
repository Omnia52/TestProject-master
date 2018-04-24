package com.example.heba.testproject;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Map;

public class eval_questionsActivity extends AppCompatActivity {
    private int position;
    private ArrayList<Integer> answers;
    private RadioButton[] r;
    private RadioGroup radioGroup;
    String questions;
    private Button submitBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_questions);
        position=0;
        final ImageView nextBtn= (ImageView) findViewById(R.id.nextIcon);
        radioGroup=(RadioGroup) findViewById(R.id.answerGroup);
        final ArrayList<String> questionsList;
        answers=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        r= new RadioButton[6];
        r[0]=null;
        r[5]=(RadioButton)findViewById(R.id.Excellent);
        r[4]=(RadioButton)findViewById(R.id.VeryGood);
        r[3]=(RadioButton)findViewById(R.id.Good);
        r[2]=(RadioButton)findViewById(R.id.Weak);
        r[1]=(RadioButton)findViewById(R.id.DontKnow);
        submitBtn=(Button) findViewById(R.id.SubmitEvalButton);
        questions=SharedPrefManager.getmInstance(this).getEvalQuestions();
        questionsList = makeList(questions);
        Log.v("GetEvalQuestionList",questionsList.toString());

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();


                if(questions.equals("")){
                    MyToast.viewToast("Error",eval_questionsActivity.this);
                }
                else{
                    final TextView textView = (TextView) findViewById(R.id.questionText);
                    textView.setText(questionsList.get(position++));
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(position==questionsList.size()-1){
                                 nextBtn.setVisibility(View.INVISIBLE);
                                 submitBtn.setVisibility(View.VISIBLE);
                            }
                            if(r[1].isChecked()||r[2].isChecked()||r[3].isChecked()||r[4].isChecked()||r[5].isChecked()){
                                for(int i=1;i<=5;i++){
                                    if(r[i].isChecked()) {
                                        answers.add(i);
                                        radioGroup.clearCheck();
                                        break;
                                    }
                                }
                                textView.setText(questionsList.get(position++));

                                /*if(position==questionsList.size()){
                                    SharedPrefManager.getmInstance(eval_questionsActivity.this).clearQuestions();
                                    MyToast.viewToast("Thanks for your time",eval_questionsActivity.this);
                                    finish();

                                }
                                else{
                                    textView.setText(questionsList.get(position++));
                                }*/
                            }
                            else {
                                MyToast.viewToast("Please Choose an Answer !",eval_questionsActivity.this);
                            }
                        }
                    });

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(r[1].isChecked()||r[2].isChecked()||r[3].isChecked()||r[4].isChecked()||r[5].isChecked()){
                                for(int i=1;i<=5;i++){
                                    if(r[i].isChecked()) {
                                            answers.add(i);
                                            radioGroup.clearCheck();
                                        break;
                                    }
                                }

                                if(position==questionsList.size()){
                                    SharedPrefManager.getmInstance(eval_questionsActivity.this).clearQuestions();

                                    final JSONArray answersJson = new JSONArray(answers);
                                    Log.v("answersList",answersJson.toString());

                                    progressDialog.setMessage("Please Wait!!");
                                    progressDialog.show();
                                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SUBMIT_EVAL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {

                                                JSONObject jsonObject=(new JSONArray(response)).getJSONObject(0);
                                                displayAlert(jsonObject.getString("message"));
                                            } catch (JSONException e) {
                                                Log.v("ErrorJson",e.toString());
                                                progressDialog.dismiss();
                                                answers.remove(answers.size()-1);
                                                MyToast.viewToast("Error !",eval_questionsActivity.this);
                                            }
                                            displayAlert(response);

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            MyToast.viewToast("Error in Connection !",eval_questionsActivity.this);
                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> params=new HashMap<>();
                                            params.put("answersList",answersJson.toString());
                                            params.put("evalType",SharedPrefManager.getmInstance(eval_questionsActivity.this).getEvalType());
                                            params.put("courseCode",SharedPrefManager.getmInstance(eval_questionsActivity.this).getCourseCode());
                                            params.put("Acc",SharedPrefManager.getmInstance(eval_questionsActivity.this).getUserAcc());
                                            return params;
                                        }
                                    };
                                    MySingleton.getmInstance(eval_questionsActivity.this).addToRequestQueue(stringRequest);
                                }
                            }
                            else {
                                MyToast.viewToast("Please Choose an Answer !",eval_questionsActivity.this);
                            }
                            Log.v("AnswersList",answers.toString());
                        }
                    });
            }
        };

        };
        thread.start();

    }
    private ArrayList<String> makeList(String list){
        ArrayList<String> temp=new ArrayList<>();
        String [] array=list.split(",");
        for(int i=0;i<array.length;) {
            String  question =array[i++].trim();
            temp.add(question);
        }
        return temp;
    }
    private void displayAlert(String message){
        progressDialog.dismiss();
        if(message.contains("InsertEvalAnswer")){
            MyToast.viewToast("Please Try Again Later !",this);
            Log.v("InsertEvalError","This");
        }
        else if (message.equals("InsertIDError")){
            MyToast.viewToast("Please Try Again Later !",this);
            Log.v("InsertIDError","This");
        }
        else if(message.equals("UpdateStudentError")){
            MyToast.viewToast("Please Try Again Later !",this);
            Log.v("UpdateStudentError","This");
        }
        else if(message.equals("Success")){
            SharedPrefManager.getmInstance(eval_questionsActivity.this).clearQuestions();
            MyToast.viewToast("Thanks for your time",eval_questionsActivity.this);
            finish();
        }
    }
}
