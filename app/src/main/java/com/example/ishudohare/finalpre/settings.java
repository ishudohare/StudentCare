package com.example.ishudohare.finalpre;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class settings extends AppCompatActivity {
    EditText alarm,minatt;
    public static String filename= "settingsdata";
    public SharedPreferences data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
       // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initialize();        data=getSharedPreferences(filename,0);


    }
    public void initialize(){
        alarm=(EditText)findViewById(R.id.alarmtime);
        data=getSharedPreferences(filename,0);
        minatt=(EditText)findViewById(R.id.minatt);
        String alarmtime=data.getString("alarmtime","30");
        String att=data.getString("minatt","75");
        alarm.setText(alarmtime);
        minatt.setText(att);
    }
    public void onBackPressed(){
        String alarmtime=alarm.getText().toString();
        String minattendance=minatt.getText().toString();
        SharedPreferences.Editor editor=data.edit();
        editor.putString("alarmtime",alarmtime);
        editor.putString("minatt",minattendance);
        editor.commit();
        super.finish();
    }

    protected void onPause(){
        super.onPause();
        String alarmtime=alarm.getText().toString();
        SharedPreferences.Editor editor=data.edit();
        editor.putString("alarmtime",alarmtime);
        String minattendance=minatt.getText().toString();

        editor.putString("minatt",minattendance);

        editor.commit();
        super.finish();
    }
}
