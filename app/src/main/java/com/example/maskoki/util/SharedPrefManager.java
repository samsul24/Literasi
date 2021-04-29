package com.example.maskoki.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_MASKOKI_APP = "spMaskokiApp";

    public static final String SP_ID_USER = "Budiii";
    public static final String SP_NAME = "Budiii";
    public static final String SP_TOKEN = "spToken";

    public static final String SP_LOGIN = "Login";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_MASKOKI_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPName(){
        return sp.getString(SP_NAME, "");
    }

    public String getSPIdUser(){
        return sp.getString(SP_ID_USER, "");
    }

    public String getSPToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public Boolean getSPIsLogin(){
        return sp.getBoolean(SP_LOGIN, false);
    }

}
