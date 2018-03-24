package com.example.ishudohare.finalpre;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**

 */
public class login extends AppCompatActivity implements View.OnClickListener {
    EditText iemail,iname,ipassword,icontact;
    ProgressDialog pd;
    SharedPreferences spref;

    Button submit;
    int ctr;

    public static final String filename="mysharedpref";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpel);
        iemail=(EditText)findViewById(R.id.email);
        spref= getSharedPreferences(filename,0);


        iname=(EditText)findViewById(R.id.name);
        icontact=(EditText)findViewById(R.id.contact);

        ipassword=(EditText)findViewById(R.id.password);
        submit=(Button)findViewById(R.id.submit);
         submit.setOnClickListener(this);

    }
    public void rlogin (View v){
        startActivity(new Intent(this,reallogin.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:

                String name=iname.getText().toString();
                String email=iemail.getText().toString();
                String contact=icontact.getText().toString();
                String password=ipassword.getText().toString();
                ConnectivityManager cm= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni=cm.getActiveNetworkInfo();
                if(ni!=null && ni.isConnected()){
                    pd=ProgressDialog.show(this,"Please Wait!",
                            "Contacting server",true);
                Dbconnect dbconnect= new Dbconnect();
                dbconnect.execute(name,email,contact,password);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Get Some Connection Bro :/", Toast.LENGTH_SHORT).show();
                }

        }

    }
    public class Dbconnect extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String name=params[0];
                String email=params[1];
                String contact =params[2];
                String password=params[3];
                URL url=new URL("http://studentpack.azurewebsites.net/hello/addinfo.php");
                HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                huc.setDoOutput(true);
                huc.setRequestMethod("POST");
                OutputStream os=huc.getOutputStream();
                BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
            spref= getSharedPreferences(filename,0);

            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        }
    }
}
