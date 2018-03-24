package com.example.ishudohare.finalpre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ishudohare.finalpre.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("check 1");
        setContentView(R.layout.splash);

        System.out.println("check 2");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String filename=reallogin.filename1;

        final SharedPreferences data=getSharedPreferences(filename,0);
        final String aTime=data.getString("name", "Guest").toString();
        System.out.println("atime "+ aTime);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    if(aTime!="Guest"){

                        startActivity(new Intent(Splash.this,StudentCare.class));finish();
                    }
                    else{

                        startActivity(new Intent(Splash.this,reallogin.class));finish();

                    }
                }

            }



        };
        timer.start();


    }
}
