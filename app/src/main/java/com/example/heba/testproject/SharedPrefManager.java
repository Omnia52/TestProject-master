package com.example.heba.testproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by SM on 3/2/2018.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance ;
    private static final String SHARED_PREF_NAME = "mySharedPref";
    private static final String USER_ACC_KEY = "userAcc";
    private static final String USER_TYPE="userType";
    private static final String USER_PASS="userPass";
    private static final String USER_SUBJECTS = "userSubjects";
    private static final String EVAL_QUESTIONS = "evalQuestions";
    private static final String EVAL_TYPE = "evalType";
    private static final String COURSE_CODE = "courseCode";
    private static final String X_VALUES = "xValues";
    private static final String Y_VALUES = "yValues";
    private static Context mCtx;

    private SharedPrefManager(Context context){
        mCtx=context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context){
        if(mInstance==null)
            mInstance=new SharedPrefManager(context);
        return mInstance;
    }

    public boolean userLogin(String Acc, ArrayList<String> subjectList,String pass,String type){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ACC_KEY,Acc);
        editor.putString(USER_SUBJECTS,subjectList.toString());
        editor.putString(USER_PASS,pass);
        editor.putString(USER_TYPE,type);
        editor.apply();
        return true;
    }

    public boolean setQuestions(ArrayList<String> questionList,String type,String courseCode){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String questions=questionList.toString().substring(1,questionList.toString().length()-1);
        editor.putString(EVAL_QUESTIONS,questions);
        editor.putString(EVAL_TYPE,type);
        editor.putString(COURSE_CODE,courseCode);
        editor.apply();
        return true;
    }

    public boolean setValues(ArrayList<String> xValues,ArrayList<String> yValues,String evalNo,String courseCode){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String y=yValues.toString().substring(1,yValues.toString().length()-1);
        String x=xValues.toString().substring(1,xValues.toString().length()-1);
        editor.putString(X_VALUES,x);
        editor.putString(Y_VALUES,y);
        editor.putString(COURSE_CODE,courseCode);
        editor.putString(EVAL_TYPE,evalNo);
        editor.apply();
        return true;
    }


    public boolean isLogged(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USER_ACC_KEY,null)!=null)
            return true;
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public boolean clearQuestions(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(EVAL_QUESTIONS);
        editor.apply();
        return true;
    }

    public String getUserAcc(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ACC_KEY,null);
    }
    public String getUserPass(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PASS,null);
    }
    public String getSubjectList(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_SUBJECTS,null);
    }

    public String getEvalQuestions(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EVAL_QUESTIONS,null);
    }
    public String getType(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TYPE,null);
    }

    public String getEvalType(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EVAL_TYPE,null);

    }

    public String getCourseCode(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_CODE,null);

    }
    public String getxValues(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(X_VALUES,null);

    }
    public String getyValues(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Y_VALUES,null);

    }

}
