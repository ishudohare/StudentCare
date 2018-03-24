package com.example.ishudohare.finalpre;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.sql.SQLException;
import java.util.Random;

public class WeeklySchedule extends AppCompatActivity implements OnNavigationItemSelectedListener, OnClickListener {
    Button add;
    int count;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        intialize();
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weekly_schedule, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.add1) {

            return super.onOptionsItemSelected(item);
        }
        fab();
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add /*2131493004*/:
                startActivityForResult(new Intent(this, AddSubject.class), 0);
                return;
            default:
                return;
        }
    }

    public void fab() {
        startActivityForResult(new Intent(this, AddSubject.class), 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = entry.getCount();
        if (i != this.count) {
            Snackbar.make(findViewById(android.R.id.content), "Class Added", Snackbar.LENGTH_SHORT).show();
            card(entry.getName((long) i), entry.getCode((long) i), i);
            this.count++;
        }
        entry.close();
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

        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.count = entry.getCount();
        for (int i = 1; i <= entry.getCount(); i++) {
            System.out.println("we got a true condition ");
            if (entry.exists((long) i)) {
                String subname = entry.getName((long) i);
                String subcode = entry.getCode((long) i);
                System.out.println(subname + " " + subcode);
                card(subname, subcode, i);
            }
        }
        entry.close();
    }

    @TargetApi(16)
    public void card(String subname, String code, int id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.baselayout1);
        LinearLayout layout2 = new LinearLayout(this);
        LinearLayout layout3 = new LinearLayout(this);
        layout3.setPadding(30, 10, 0, 10);
        layout3.setBackgroundColor(-1);
        layout3.setLayoutParams(new LayoutParams(-1, -2));
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new LayoutParams(-1, -2));
        Random random = new Random();
        layout2.setBackgroundColor(Color.rgb(random.nextInt(100) + 155, random.nextInt(155), random.nextInt(155)));
        layout2.setPadding(30, 10, 5, 10);
        layout2.setPadding(30,50,5,50);
        layout2.setId(id);
        TextView name = new TextView(this);
        name.setText(subname);
        name.setTextSize(22.0f);
        name.setTextColor(-1);
        System.out.println("adding textview in layout 2");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2, -2, 1000.0f);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        LinearLayout layout5 = new LinearLayout(this);
        layout5.setLayoutParams(param);
        layout5.addView(name);
        ImageView arrow = new ImageView(this);
        arrow.setBackground(getResources().getDrawable(R.drawable.rightarrow));
        LinearLayout layout4 = new LinearLayout(this);
        layout4.setLayoutParams(param1);
        layout4.addView(arrow);
        layout2.addView(layout5);
        layout2.addView(layout4);
        System.out.println("added textview in layout 2");
        final Vibrator myVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final int i = id;
        layout2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(WeeklySchedule.this, MainActivitySchedule.class);
                Bundle which = new Bundle();
                which.putInt("id", i);
                intent.putExtras(which);
                WeeklySchedule.this.startActivity(intent);
            }
        });
       // i = id;
        layout2.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                CharSequence[] options = new CharSequence[]{"Edit Present Record", "Edit Absent Record"};
                myVib.vibrate(50);
                Builder builder = new Builder(WeeklySchedule.this);
                builder.setTitle("Edit Subject record");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 /*0*/:
                                Bundle recordNo = new Bundle();
                                recordNo.putInt("recordNo", i);
                                recordNo.putChar("PorA", 'P');
                                Intent a = new Intent(WeeklySchedule.this.getBaseContext(), dates.class);
                                a.putExtras(recordNo);
                                WeeklySchedule.this.startActivity(a);
                                return;
                            case 1 /*1*/:
                                Bundle recordN = new Bundle();
                                recordN.putInt("recordNo", i);
                                recordN.putChar("PorA", 'A');
                                Intent ab = new Intent(WeeklySchedule.this.getBaseContext(), dates.class);
                                ab.putExtras(recordN);
                                WeeklySchedule.this.startActivity(ab);
                                return;
                            default:
                                return;
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        System.out.println("adding layout2 in layout");
        layout.addView(layout2);
        layout.addView(layout3);
        System.out.println("added");
    }

    public int percentage(int presents, int absents) {
        if (presents == 0 && absents == 0) {
            return 0;
        }
        return (int) ((((float) presents) / (((float) presents) + ((float) absents))) * 100.0f);
    }

    public void display(LinearLayout layout9, int percentage, TextView percent) {
        if (percentage > 75) {
            percent.setBackgroundColor(Color.GREEN);
            //-16711936
            percent.setTextColor(Color.WHITE);
            //-16777216
            return;
        }
        if (percentage > 10) {
            percent.setBackgroundColor(Color.RED);
            //-65536
            percent.setTextColor(-1);
        }
//        else {
//            percent.setBackgroundColor(-65536);
//            percent.setTextColor(-1);
//        }
    }
}
