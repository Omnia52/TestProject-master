package com.example.heba.testproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SM on 4/24/2018.
 */

public class PermissionUri {
    private Context mCtx;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionUri(Context mCtx) {
        this.mCtx = mCtx;
        sharedPreferences= mCtx.getSharedPreferences("PERMISSION_PREFERENCES",mCtx.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void updatePermission(String permission){
        switch (permission){
            case "storage":
                editor.putBoolean("PERMISSION_STORAGE",true);
                editor.commit();
                break;
        }
    }

    public boolean checkPermission(String permission){
        boolean isShown=false;
        switch (permission){
            case "storage":
                isShown=sharedPreferences.getBoolean("PERMISSION_STORAGE",false);
                break;
        }

        return isShown;
    }
}
