package com.example.heba.testproject;

/**
 * Created by Heba on 3/5/2018.
 */

public class SubjectData {

    private String subjectCode;
    private String doneEval1;
    private String doneEval2;
    private String activeEval1;
    private String activeEval2;


    public SubjectData() {
        subjectCode=doneEval1=doneEval2=activeEval1=activeEval2="";
    }

    public SubjectData(String subjectCode, String doneEval1, String doneEval2, String activeEval1, String activeEval2) {
        this.subjectCode = subjectCode;
        this.doneEval1 = doneEval1;
        this.doneEval2 = doneEval2;
        this.activeEval1 = activeEval1;
        this.activeEval2 = activeEval2;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getDoneEval1() {
        return doneEval1;
    }

    public String getDoneEval2() {
        return doneEval2;
    }

    public String getActiveEval1() {
        return activeEval1;
    }

    public String getActiveEval2() {
        return activeEval2;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setDoneEval1(String doneEval1) {
        this.doneEval1 = doneEval1;
    }

    public void setDoneEval2(String doneEval2) {
        this.doneEval2 = doneEval2;
    }

    public void setActiveEval1(String activeEval1) {
        this.activeEval1 = activeEval1;
    }

    public void setActiveEval2(String activeEval2) {
        this.activeEval2 = activeEval2;
    }
}

