package com.example.ishudohare.finalpre;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import  android.support.v4.content.ContextCompat;
import  android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import com.example.ishudohare.finalpre.db.TaskContract;
import com.example.ishudohare.finalpre.db.TaskDbHelper;
public class StudentCare extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int count;
    private TaskDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_care);
        initialize();
        setschedule();
        setmoney();
        settodos();
        setreminder();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void initialize(){
        mHelper = new TaskDbHelper(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView navname = (TextView) header.findViewById(R.id.uname);
        String filename=reallogin.filename1;

        SharedPreferences data=getSharedPreferences(filename,0);
        String aTime=data.getString("name", "Guest").toString();
        System.out.println("atime "+ aTime);
        navname.setText(aTime);
        final LinearLayout todos=(LinearLayout)findViewById(R.id.Todocard);
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentCare.this,MainActivity.class ));
            }
        });
        final  LinearLayout reminder=(LinearLayout)findViewById(R.id.remindercard);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentCare.this,Main2Activity.class));
            }
        });
        final LinearLayout udhar=(LinearLayout)findViewById(R.id.udhar);
        LinearLayout schedule=(LinearLayout)findViewById(R.id.schedule);
        udhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentCare.this, Udhar.class));
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentCare.this, Overall.class));
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.overall, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.add1) {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camara) {
            startActivity(new Intent(this,Overall.class));
        }
        else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, Tomorrow.class));
        } else if (id == R.id.nav_slideshow) {
            Intent I = new Intent(this, WeeklySchedule.class);

            startActivity(I);
        } else if (id == R.id.nav_manage) {
            startActivityForResult(new Intent(this, AddSubject.class), 0);
        } else if (id ==R.id.nav_share){
            Intent p=new Intent(this,settings.class);
            startActivity(p);
        }

        else if(id == R.id.nav_send) {
            Intent p=new Intent(this,Udhar.class);
            startActivity(p);

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
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }


    public void setschedule(){
        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.count = entry.getCount();
        String timetable="";
        String time="";
        int i = 1;
//        while (i <= entry.getCount()) {
//            System.out.println("we got a true condition ");
//            if (entry.exists((long) i) && entry.class_today((long) i)) {
//                String subname = entry.getName((long) i);
//                String[] timing = entry.getTime((long) i).split("\\.");
//                timetable+=subname+"\n";
//                time+=timing[0]+"-"+ timing[1 ]+"\n";
//            }
//            i++;
//        }
//        entry.close();
      //  this.count = entry.getCount();
        int p=0;
        int [] starttime=new int[1000];
        int [] id=new int[1000];



        while (i<=entry.getCount()){
            if(entry.exists(i) && entry.class_today(i)){
                String[] timing = entry.getTime((long) i).split("\\.");
                String[] start=timing[0].split(":");
                System.out.println("id is "+i+"time is "+Integer.parseInt(start[0]));
                starttime[p]=Integer.parseInt(start[0]);
                id[p]=i;
                p++;

            }
            i++;
        }
        for(int j=0;j<p-1;j++){
            for (int k=j+1;k<p;k++){
                if(starttime[j]>starttime[k]){
                    int y = starttime[j];
                    starttime[j]=starttime[k];
                    starttime[k]=y;
                    int h=id[j];
                    id[j]=id[k];
                    id[k]=h;

                }
            }
        }


        for(int k=0;k<p;k++){

                String subname = entry.getName((long) id[k]);
                String[] timing = entry.getTime((long) id[k]).split("\\.");
                timetable+=subname+"\n";
                time+=timing[0]+"-"+timing[1]+"\n";

        }

        entry.close();
//

        if(timetable!=""){
            TextView sch=(TextView)findViewById(R.id.classesToday);
            sch.setText(timetable.substring(0,timetable.length()-1));
            TextView tim=(TextView)findViewById(R.id.times);
            tim.setText(time.substring(0,time.length()-1));
        }
    }
public void setmoney(){
    String names="";
    String amount="";
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

            names+=name+"\n";
            amount+="₹"+total_money+"\n";
        }

    }
    entry.close();
    if(names!=""){
        TextView nme=(TextView)findViewById(R.id.Name);
        TextView amnt=(TextView)findViewById(R.id.amount);
        nme.setText(names.substring(0,names.length()-1));
        amnt.setText(amount.substring(0,amount.length()-1));
    }
}
    public void settodos(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String res="";
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            //taskList.add(cursor.getString(idx));
            System.out.println("hea "+cursor.getString(idx));
            res+=cursor.getString(idx)+"\n";
        }
        if(res!=""){
            res=res.substring(0,res.length()-1);

            TextView ans=(TextView)findViewById(R.id.todoall);
            ans.setText(res);
        }
    }
    public void setreminder(){
        DatabaseReminder entry = new DatabaseReminder(this);
        String res="";
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        } for (int i = 1; i <= entry.getCount(); i++) {
            System.out.println("we got a true condition ");
            if (entry.exists((long) i)) {
                String ename = entry.getEventName((long) i);
                String date = entry.getDates((long) i);
                int event_id=entry.getEventID(i);
                System.out.println(ename + " " + date);
                res+=ename+"\n";
            }
        }
        TextView rem=(TextView)findViewById(R.id.reminderall);
        if(res!=""){
           res=res.substring(0,res.length()-1);

        rem.setText(res);}
        entry.close();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    Toast.makeText(StudentCare.this, "Some feature wouldn't work", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
