package com.example.ishudohare.finalpre;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.sql.SQLException;
import java.util.Random;

public class Udhar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public static int UDHARADD=9;
    public static int ADDPERSON=18;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udhar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                 Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
    intialize();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.udhar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_upload) {
            pd=ProgressDialog.show(this,"Please Wait!",
                    "Contacting server",true);
            Dbconnect dbconnect= new Dbconnect();
            dbconnect.execute("guest");

        }
        if(id==R.id.download){
     startActivity(new Intent(this,udharDbDown.class));
        }

        return super.onOptionsItemSelected(item);
    }
    public class Dbconnect extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {
            try {

                URL url=new URL("http://studentpack.azurewebsites.net/hello/addudhar.php");
                String filename=reallogin.filename1;
                String res="";
                SharedPreferences dat=getSharedPreferences(filename,0);
                String loggedin=dat.getString("name", "Guest").toString();
                System.out.println("username "+ loggedin);


                Udhardatabase entry = new Udhardatabase(Udhar.this);
                try {
                    entry.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int count = entry.getCount();
                for (int i = 1; i <= count; i++) {
                    res="";
                    System.out.println("we got a true condition ");
                    if (entry.exists((long) i)) {
                        String name = entry.getName((long) i);
                        String desc=entry.getDesc(i);
                        String[] all_desc=desc.split(";");
                        int total_money=0;
                        for(int j=1;j<all_desc.length;j++){
                            String[] oneEvent= all_desc[j].split(":");
                            total_money+=Integer.parseInt(oneEvent[1]);
                        }
                        HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                        huc.setDoOutput(true);
                        huc.setRequestMethod("POST");
                        OutputStream os=huc.getOutputStream();
                        BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                        String data= URLEncoder.encode("moneyfrom","UTF-8")+"="+URLEncoder.encode(loggedin,"UTF-8")+"&"+
                                URLEncoder.encode("moneyto","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                                URLEncoder.encode("money","UTF-8")+"="+URLEncoder.encode(Integer.toString(total_money),"UTF-8");
                        bw.write(data);
                        bw.flush();
                        bw.close();
                        os.close();
                        InputStream is= huc.getInputStream();
                        BufferedReader bwi=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                        String line="";
                        res=bwi.readLine();


                        while((line=bwi.readLine())!=null){
                            res+=line;
                        }

                        bwi.close();
                        is.close();
                        huc.disconnect();


                    }
                }
                entry.close();




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
            if(s=="")
                s="Error Occured";
            else
               s="Success";

            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camara) {
            finish();
            startActivity(new Intent(this, Overall.class));
        }
        else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, Tomorrow.class));
        } else if (id == R.id.nav_slideshow) {
            Intent I = new Intent(this, WeeklySchedule.class);
            finish();
            startActivity(I);
        } else if (id == R.id.nav_manage) {
            startActivityForResult(new Intent(this, AddSubject.class), 0);
        } else if (id ==R.id.nav_share){
            Intent p=new Intent(this,settings.class);
            startActivity(p);
        }

        else if(id == R.id.nav_send) {
//            Intent p=new Intent(this,Udhar.class);
//            startActivity(p);

        }
        else if(id==R.id.AddNotes)
        {
            Intent newintent=new Intent(this,MainActivityNotes.class);
            startActivity(newintent);
        }
        else if(id==R.id.MicCog)
        {Intent newinten=new Intent(this,MainActivityCognitive.class);
            startActivity(newinten);}
        else if(id==R.id.todo){
            startActivity(new Intent(this,MainActivity.class));
        }
        else if(id==R.id.Reminder){
            startActivity(new Intent(this,Main2Activity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sta(View view){
        Intent i=new Intent(Udhar.this,PersonAddUdhar.class);
        startActivityForResult(i, ADDPERSON);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Udhardatabase entry = new Udhardatabase(this);
                try {
                    entry.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int i = entry.getCount();
                //if (i != this.count) {
                    Snackbar.make(findViewById(android.R.id.content), "Class Added", Snackbar.LENGTH_SHORT).show();
                    card(entry.getName((long) i), entry.getImg((long) i),0, i);
                 //   this.count++;
               // }
                entry.close();

            }
        }
        if(requestCode==ADDPERSON&&resultCode==RESULT_OK){
            Udhardatabase entry = new Udhardatabase(this);
            try {
                entry.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int i = entry.getCount();

            String name = entry.getName((long) i);
            Bitmap pic = entry.getImg((long) i);
            //System.out.println(subname + " " + subcode);
            Bitmap resized = Bitmap.createScaledBitmap(pic, 200, 200, true);
            String desc=entry.getDesc(i);
            String[] all_desc=desc.split(";");
            int total_money=0;
            for(int j=1;j<all_desc.length;j++){
                String[] oneEvent= all_desc[j].split(":");
                total_money+=Integer.parseInt(oneEvent[1]);
            }
            card(name, resized, total_money,i);

        }
        if(requestCode==UDHARADD&&resultCode==RESULT_OK){
            Bundle recv=data.getExtras();
            int id1=recv.getInt("id");
            Udhardatabase entry = new Udhardatabase(this);
            try {
                entry.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //System.out.println(subname + " " + subcode);
            String desc=entry.getDesc(id1);
            String[] all_desc1=desc.split(";");
            int total_money=0;
            for(int j=1;j<all_desc1.length;j++){
                String[] oneEvent= all_desc1[j].split(":");
                total_money+=Integer.parseInt(oneEvent[1]);
            }
            TextView money=(TextView)findViewById(id1+100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                money.setText("₹"+total_money);
            }
            else{
                money.setText("Rs."+ total_money);
            }
        }
       }

    public void intialize() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView navname = (TextView) header.findViewById(R.id.uname);
        String filename=reallogin.filename1;

        SharedPreferences data=getSharedPreferences(filename,0);
        String aTime=data.getString("name", "Guest").toString();
        System.out.println("atime "+ aTime);
        navname.setText(aTime);

        Udhardatabase entry = new Udhardatabase(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = entry.getCount();
        for (int i = 1; i <= count; i++) {
            System.out.println("we got a true condition ");
            if (entry.exists((long) i)) {
                String name = entry.getName((long) i);
                Bitmap pic = entry.getImg((long) i);
                //System.out.println(subname + " " + subcode);
                Bitmap resized = Bitmap.createScaledBitmap(pic, 200, 200, true);
                String desc=entry.getDesc(i);
                String[] all_desc=desc.split(";");
                int total_money=0;
                for(int j=1;j<all_desc.length;j++){
                    String[] oneEvent= all_desc[j].split(":");
                    total_money+=Integer.parseInt(oneEvent[1]);
                }
                card(name, resized, total_money,i);
            }
        }
        entry.close();
    }

    @TargetApi(16)
    public void card(String pname, Bitmap pic,int desc, int id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.baselayout3);
        LinearLayout layout2 = new LinearLayout(this);
        LinearLayout layout3 = new LinearLayout(this);
        layout3.setPadding(30, 10, 0, 10);
        layout3.setBackgroundColor(-1);
        layout3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        Random random = new Random();
        //layout2.setBackgroundColor(Color.rgb(random.nextInt(100) + 155, random.nextInt(155), random.nextInt(155)));
        layout2.setPadding(30, 10, 5, 10);
        layout2.setId(id);
        TextView name = new TextView(this);
        name.setText(pname);
        name.setTextSize(24.0f);
        name.setTextColor(Color.parseColor("#3F51B5"));
        System.out.println("adding textview in layout 2");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2, -2, 100.0f);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        LinearLayout layout5 = new LinearLayout(this);
        layout5.setLayoutParams(param);
        layout5.addView(name);
        layout5.setGravity(Gravity.CENTER_HORIZONTAL);
        ImageView arrow = new ImageView(this);
        arrow.setImageBitmap(pic);
        LinearLayout layout4 = new LinearLayout(this);
        layout4.setLayoutParams(param1);
        layout4.addView(arrow);
        layout4.setPadding(10, 10, 10, 10);
        layout4.setBackgroundColor(Color.parseColor("#A4C639"));
        LinearLayout layout6=new LinearLayout(this);
        layout6.setLayoutParams(param1);
        TextView money=new TextView(this);
        money.setId(id+100);
        money.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            money.setText("₹"+desc);
        }
        else{
            money.setText("Rs."+ desc);
        }
        money.setTextSize(24.0f);
    layout6.addView(money);
        layout2.setGravity(Gravity.CENTER_VERTICAL);
        layout2.addView(layout4);
        layout2.addView(layout5);
        layout2.addView(layout6);
        System.out.println("added textview in layout 2");
        final Vibrator myVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final int i = id;
        layout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Udhar.this, udharspecific.class);
                Bundle which = new Bundle();
                which.putInt("id", i);
                intent.putExtras(which);
                startActivityForResult(intent, UDHARADD);
            }
        });
        // i = id;
        System.out.println("adding layout2 in layout");
        layout.addView(layout2);
        layout.addView(layout3);
        System.out.println("added");
    }

}
