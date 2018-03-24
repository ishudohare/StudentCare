package com.example.ishudohare.finalpre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ishudohare.finalpre.R;

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

public class udharDbDown extends AppCompatActivity {
ProgressDialog pd;
    TextView md;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dbconnect dc=new Dbconnect();
        pd= ProgressDialog.show(this,"Please Wait!",
                "Contacting server",true);
        dc.execute("guestpelu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udhardisplay);
        md=(TextView)findViewById(R.id.udhardisplaypel) ;

    }





    public class Dbconnect extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url=new URL("http://studentpack.azurewebsites.net/hello/checkudhar.php");
                HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                huc.setDoOutput(true);
                huc.setDoInput(true);
                huc.setRequestMethod("POST");
                OutputStream os=huc.getOutputStream();
                BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String filename=reallogin.filename1;
                SharedPreferences dat=getSharedPreferences(filename,0);
                String loggedin=dat.getString("name", "Guest").toString();
                System.out.println("name of user "+loggedin);
                String data= URLEncoder.encode("moneyfrom","UTF-8")+"="+URLEncoder.encode(loggedin,"UTF-8");
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
                String [] hawas=res.split(";");
                String k="";
                for(int i=0;i<hawas.length;i++){
                    k+=hawas[i]+"\n";
                }
                bwi.close();
                is.close();
                huc.disconnect();
                return k;

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
            if(s==null|| s==""){
                s="You Dont Have any Udhar/Loans pending.";
            }
            md.setText(s);

        }
    }
}
