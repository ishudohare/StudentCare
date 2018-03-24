package com.example.ishudohare.finalpre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by REBEL on 9/21/2016.*/
public class reallogin extends AppCompatActivity  {
    Button login;
    ProgressDialog pd;
    SharedPreferences spref;
    public static final String filename1="mysharedpref";
    public SharedPreferences data;

    EditText email,pass;
    AlertDialog ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ad=new AlertDialog.Builder(this).create();
        ad.setTitle("Login Successful!");
        setContentView(R.layout.loginpel);
        login=(Button)findViewById(R.id.blogin);
        email=(EditText) findViewById(R.id.lemail);
        pass=(EditText)findViewById(R.id.lpass);


    }
    public void login(View v){
        String temail=email.getText().toString();
        String tuser=pass.getText().toString();
        Dbconnect dc=new Dbconnect();
        pd=ProgressDialog.show(this,"Please Wait!",
                "Contacting server",true);
        dc.execute(temail,tuser);

    }
    public void tvregister(View v){
        startActivity(new Intent(this,login.class));
        finish();
    }
    public class Dbconnect extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String login_email=params[0];
                String login_password=params[1];
                URL url=new URL("http://studentpack.azurewebsites.net/hello/login.php");
                HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                huc.setDoOutput(true);
                huc.setDoInput(true);
                huc.setRequestMethod("POST");
                OutputStream os=huc.getOutputStream();
                BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("login_email","UTF-8")+"="+URLEncoder.encode(login_email,"UTF-8")+"&"+
                        URLEncoder.encode("login_password","UTF-8")+"="+URLEncoder.encode(login_password,"UTF-8");
                bw.write(data);
                bw.flush();
                bw.close();
                os.close();
                InputStream is= huc.getInputStream();
                BufferedReader bwi=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String line="";
                String res=bwi.readLine();


                while((line=bwi.readLine())!=null){
                    res+=line;
                }

                bwi.close();
                is.close();
                huc.disconnect();
                return res;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            ad.setMessage(s);
            int flag=0;
            ad.setTitle("Login Unsuccessful!");
            if(s.charAt(0)=='H'&&s.charAt(1)=='i')
            {ad.setTitle("Login Successful!");
            flag=1;
                String[] first=s.split("\\.");
                String name="";
                for(int i=4;i<first[0].length();i++){
                    name+=first[0].charAt(i);
                }
                data=getSharedPreferences(filename1,0);
                SharedPreferences.Editor editor=data.edit();
                System.out.println("name is "+name);
                editor.putString("name",name);
                editor.commit();


            }


            ad.setMessage(s);
            ad.show();
            if(flag==1)
            {
                Intent newintent = new Intent(reallogin.this,StudentCare.class);
                finish();
                startActivity(newintent);

        }
    }
}}
