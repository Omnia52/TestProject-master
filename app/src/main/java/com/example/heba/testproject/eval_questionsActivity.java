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

import java.util.ArrayList;

public class eval_questionsActivity extends AppCompatActivity {
    private int position;
    private ArrayList<Integer> answers;
    private RadioButton[] r;
    private RadioGroup radioGroup;
    String questions;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_questions);
        position=0;
        final ImageView nextBtn= (ImageView) findViewById(R.id.nextIcon);
        radioGroup=(RadioGroup) findViewById(R.id.answerGroup);
        final ArrayList<String> questionsList;
        answers=new ArrayList<>();
        r= new RadioButton[6];
        r[0]=null;
        r[1]=(RadioButton)findViewById(R.id.Excellent);
        r[2]=(RadioButton)findViewById(R.id.VeryGood);
        r[3]=(RadioButton)findViewById(R.id.Good);
        r[4]=(RadioButton)findViewById(R.id.Weak);
        r[5]=(RadioButton)findViewById(R.id.DontKnow);
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
                            if(r[1].isChecked()||r[2].isChecked()||r[3].isChecked()||r[4].isChecked()||r[5].isChecked()){
                                for(int i=1;i<=5;i++){
                                    if(r[i].isChecked()) {
                                        {
                                            answers.add(i);
                                            radioGroup.clearCheck();
                                        }
                                        break;
                                    }
                                }
                                if(position==questionsList.size()){
                                    SharedPrefManager.getmInstance(eval_questionsActivity.this).clearQuestions();
                                    MyToast.viewToast("Thanks for your time",eval_questionsActivity.this);
                                    finish();

                                }
                                else{
                                    textView.setText(questionsList.get(position++));
                                }
                            }
                            else {
                                MyToast.viewToast("Please Choose an Answer !",eval_questionsActivity.this);
                            }
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
}
