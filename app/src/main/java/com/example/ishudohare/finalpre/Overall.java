package com.example.ishudohare.finalpre;

import android.animation.LayoutTransition;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION;
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
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;

public class Overall extends AppCompatActivity implements OnNavigationItemSelectedListener, OnClickListener {
    Button add;
    int count;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall);
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
        getMenuInflater().inflate(R.menu.overall, menu);
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
            if (entry.class_today((long) i)) {
                int absents = entry.absentCount((long) i) - 1;
                card(entry.getName((long) i), entry.getCode((long) i), entry.getTime((long) i).split("\\."), i, absents, entry.presentCount((long) i) - 1);
                this.count++;
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
        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.count = entry.getCount();
        int i = 1;
        int p=0;
        int [] starttime=new int[1000];
        int [] id=new int[1000];
//        while (i <= entry.getCount()) {
//            System.out.println("we got a true condition ");
//            if (entry.exists((long) i) && entry.class_today((long) i)) {
//                String subname = entry.getName((long) i);
//                String subcode = entry.getCode((long) i);
//                String[] timing = entry.getTime((long) i).split("\\.");
//                int presents = entry.presentCount((long) i) - 1;
//                System.out.println("presents from database returned is " + presents);
//                card(subname, subcode, timing, i, entry.absentCount((long) i) - 1, presents);
//            }
//            i++;
//        }
        while (i<=entry.getCount()){
            if(entry.exists(i) && entry.class_today(i)){
                String[] timing = entry.getTime((long) i).split("\\.");
                String[] start=timing[0].split(":");
                System.out.println(Integer.parseInt(start[0]));
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
        if (entry.exists((long) id[k]) && entry.class_today((long) id[k])) {
                String subname = entry.getName((long) id[k]);
                String subcode = entry.getCode((long) id[k]);
                String[] timing = entry.getTime((long) id[k]).split("\\.");
                int presents = entry.presentCount((long) id[k]) - 1;
                System.out.println("presents from database returned is " + presents);
                card(subname, subcode, timing, id[k], entry.absentCount((long) id[k]) - 1, presents);
            }

        }

        entry.close();


    }

    public void card(final String subname, String code, final String[] timing, int id, int absents, int presents) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.baselayout);
        LinearLayout linearLayout2 = new LinearLayout((Context)this);
        LinearLayout linearLayout3 = new LinearLayout((Context)this);
        final LinearLayout linearLayout4 = new LinearLayout((Context)this);
        LinearLayout linearLayout5 = new LinearLayout((Context)this);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setLayoutParams(new LayoutParams(-1, -2));
        Random random = new Random();
        int colorhead = Color.rgb(random.nextInt(100) + 155, random.nextInt(155), random.nextInt(155));
        linearLayout3.setBackgroundColor(colorhead);
        linearLayout3.setPadding(30, 5, 0, 5);
        ImageView clock = new ImageView(this);
        linearLayout4.setId(id);
        TextView subject = new TextView(this);
        subject.setText(subname);
        subject.setTextSize(18.0f);
        subject.setTextColor(-1);
        //linearLayout3.setShowDividers();
        linearLayout3.addView(subject);
        System.out.println("time in layout2 set");
        linearLayout4.setOrientation(LinearLayout.VERTICAL);
        linearLayout4.setPadding(30, 0, 30, 5);
        TextView subcode = new TextView(this);
        subcode.setText(code);
        subcode.setTextSize(16.0f);
        subcode.setTypeface(null, 1);
        subcode.setAllCaps(true);
        subcode.setTextColor(colorhead);
        linearLayout4.addView(subcode);
        LinearLayout linearLayout6 = new LinearLayout(this);
        linearLayout6.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout6.setGravity(1);
        final TextView start = new TextView(this);
        start.setText("Starts On  ");
        linearLayout6.addView(start);
        TextView end = new TextView(this);
        end.setText(" Ends On");
        linearLayout6.addView(end);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setGravity(1);
        TextView strts = new TextView(this);
        strts.setText(timing[0] + " ");
        int color = Color.rgb(random.nextInt(155) + 100, random.nextInt(170), 200);
        strts.setTextColor(color);
        strts.setTextSize(22.0f);
        linearLayout2.addView(strts);
        LinearLayout linearLayout7 = new LinearLayout(this);
        linearLayout7.setLayoutParams(new LayoutParams(4, 90));
        linearLayout7.setPadding(10, 0, 10, 0);
        linearLayout7.setBackgroundColor(colorhead);
        TextView ends = new TextView(this);
        ends.setText(" " + timing[1]);
        ends.setTextColor(color);
        ends.setTextSize(22.0f);
        linearLayout2.addView(linearLayout7);
        linearLayout2.addView(ends);
        linearLayout4.addView(linearLayout6);
        linearLayout4.addView(linearLayout2);
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final int i = id;
        linearLayout4.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                CharSequence[] options = new CharSequence[]{"Edit Present Record", "Edit Absent Record","Set Alarm reminder"};
                vibrator.vibrate(50);
                Builder builder = new Builder(Overall.this);
                builder.setTitle("Edit your selection");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 /*0*/:
                                Bundle recordNo = new Bundle();
                                recordNo.putInt("recordNo", i);
                                recordNo.putChar("PorA", 'P');
                                Intent a = new Intent(Overall.this.getBaseContext(), dates.class);
                                a.putExtras(recordNo);
                                Overall.this.startActivity(a);
                                return;
                            case 1 /*1*/:
                                Bundle recordN = new Bundle();
                                recordN.putInt("recordNo", i);
                                recordN.putChar("PorA", 'A');
                                Intent ab = new Intent(Overall.this.getBaseContext(), dates.class);
                                ab.putExtras(recordN);
                                Overall.this.startActivity(ab);
                                return;
                            case 2:
                                Calendar calNow = Calendar.getInstance();
                                Calendar calSet = (Calendar) calNow.clone();
                                String[] startTime=timing[0].split(":");
                                System.out.println("hour is " + startTime[0]);
                                System.out.println("hour is " + startTime[1]);


                                calSet.set(Calendar.SECOND, 0);
                                calSet.set(Calendar.MILLISECOND, 0);
                                String filename=settings.filename;
                                SharedPreferences data=getSharedPreferences(filename,0);
                                String aTime=data.getString("alarmtime", "30").toString();
                                int alarmtime=Integer.parseInt(aTime);
                                if(Integer.parseInt(startTime[1])-alarmtime<0){
                                calSet.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime[0]) - (alarmtime / 60));
                                calSet.set(Calendar.MINUTE, Integer.parseInt(startTime[1])*(-1) + (alarmtime % 60)-60);}
                           else{
                                calSet.set(Calendar.MINUTE, Integer.parseInt(startTime[1]) - (alarmtime % 60));
                            }
                            if (calSet.compareTo(calNow) <= 0)
                                { //Today Set time passed, count to tomorrow
                                 calSet.add(Calendar.DATE, 1); }
                                calNow.add(Calendar.SECOND, 1);

                                System.out.println("call to set alarm");

                                setAlarm(calSet,subname);
                                break;
                            default:
                                return;
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        TextView att = new TextView(this);
        att.setText("Current Attendance: ");
        att.setTextSize(16.0f);
        LinearLayout linearLayout8 = new LinearLayout(this);
        linearLayout8.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout8.setLayoutParams(new LayoutParams(-1, -2));
        linearLayout8.setPadding(0, 15, 0, 10);
        linearLayout8.addView(att);
        LinearLayout linearLayout9 = new LinearLayout(this);
        linearLayout9.setOrientation(LinearLayout.HORIZONTAL);
        int percentage = percentage(presents, absents);
        TextView per = new TextView(this);
        final LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        //layoutParams2.setMargins(0, 0, 50, 0);
        per.setLayoutParams(layoutParams2);
        display(linearLayout2, percentage, per);
        linearLayout9.setGravity(1);
        linearLayout9.setId(id + 2000);

        per.setText((percentage + "") + "%");
        per.setId(id + 900);
        per.setTextSize(16.0f);
        final TextView P = new TextView(this);
        P.setLayoutParams(layoutParams);
        P.setTextColor(Color.rgb(34, 139, 34));
        P.setTypeface(Typeface.DEFAULT_BOLD);
        final TextView A = new TextView(this);
        A.setLayoutParams(layoutParams);
        A.setTextColor(Color.RED);
        A.setTypeface(Typeface.DEFAULT_BOLD);
        DATABASE database = new DATABASE(this);
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        P.setText(" P: " + Integer.toString(database.presentCount((long) id) - 1));
        P.setTextSize(16.0f);
        P.setId(id + 6000);
        A.setText(" A: " + Integer.toString(database.absentCount((long) id) - 1));
        A.setId(id + 7000);
        A.setTextSize(16.0f);
        database.close();
        //new int[1][0] = absents;
        //new int[1][0] = presents;
        final boolean[] i2 = new boolean[]{true};
        final LinearLayout linearLayout10 = new LinearLayout(this);
       // linearLayout2 = new LinearLayout(this);
        linearLayout10.setId(id + 4000);

        final LinearLayout finalAttendanceholder = linearLayout2;
        final int i3 = id;
        linearLayout4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LayoutTransition transition;
                if (i2[0]) {
                    i2[0] = false;
                    LinearLayout attendance = new LinearLayout(Overall.this);
                    attendance.setOrientation(LinearLayout.HORIZONTAL);
                    Button Present = new Button(Overall.this);
                    Present.setLayoutParams(layoutParams);

                    Present.setText("Present today");
                    Present.setId(i3 + 100);
                    attendance.addView(Present);
                    Present.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            DATABASE entry = new DATABASE(Overall.this);
                            try {
                                entry.open();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            entry.markPresent((long) i3);
                            int percen = Overall.this.percentage(entry.presentCount((long) i3) - 1, entry.absentCount((long) i3) - 1);
                            TextView yes = (TextView) Overall.this.findViewById(i3 + 900);
                            yes.setText(percen + " %");
                            Overall.this.display((LinearLayout) Overall.this.findViewById(i3 + 2000), percen, yes);
                            P.setText(" P: " + (entry.presentCount((long) i3) - 1));
                            entry.close();
                        }
                    });

                    Button Absent = new Button(Overall.this);
                    Absent.setText("Absent today");
                    Absent.setLayoutParams(layoutParams);
                    Absent.setId(i3 + 500);
                    attendance.addView(Absent);
                    attendance.setId(i3 + 700);
                    Absent.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            DATABASE entry = new DATABASE(Overall.this);
                            try {
                                entry.open();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            entry.markAbsent((long) i3);
                            int percen = Overall.this.percentage(entry.presentCount((long) i3) - 1, entry.absentCount((long) i3) - 1);
                            TextView yes = (TextView) Overall.this.findViewById(i3 + 900);
                            yes.setText(percen + " %");
                            Overall.this.display((LinearLayout) Overall.this.findViewById(i3 + 2000), percen, yes);
                            A.setText(" A: " + (entry.absentCount((long) i3) - 1));
                            entry.close();
                        }
                    });
                    transition = new LayoutTransition();
                    if (VERSION.SDK_INT >= 16) {
                        transition.enableTransitionType(2);
                    }
                    linearLayout4.setLayoutTransition(transition);
                    //linearLayout4.addView(finalAttendanceholder);
                    //  finalAttendanceholder.addView(attendance);
                    linearLayout10.addView(attendance);
                    linearLayout4.addView(linearLayout10);
                } else {
                    LinearLayout attendance = (LinearLayout) Overall.this.findViewById(i3 + 700);
                    Button present = (Button) Overall.this.findViewById(i3 + 100);
                    Button absent = (Button) Overall.this.findViewById(i3 + 500);
                    //LinearLayout child = finalLayout;
                    transition = new LayoutTransition();
                    if (VERSION.SDK_INT >= 16) {
                        transition.enableTransitionType(4);
                    }
                    linearLayout4.setLayoutTransition(transition);
                    linearLayout10.removeAllViews();
                    attendance.removeAllViews();
                    linearLayout4.removeView(linearLayout10);
                    i2[0] = true;
                }
            }
        });
        LinearLayout linearLayout11 = new LinearLayout(this);
        linearLayout11.setLayoutParams(new LayoutParams(-1, 4));
        linearLayout11.setBackgroundColor(colorhead);
        LinearLayout linearLayout12 = new LinearLayout(this);
        linearLayout12.setLayoutParams(new LayoutParams(-1, 4));
        linearLayout12.setBackgroundColor(colorhead);
        linearLayout9.addView(per);
        linearLayout8.addView(linearLayout9);
        linearLayout8.addView(P);
        linearLayout8.addView(A);
        linearLayout4.addView(linearLayout11);
        linearLayout4.addView(linearLayout8);
        linearLayout4.addView(linearLayout12);
        System.out.println("sub in layout3 set");
        if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout3.setElevation(16);
        }
        layout.addView(linearLayout3);

        System.out.println("layoiy3 in layout2 set");
        layout.addView(linearLayout4);
        layout.setLayoutTransition(new LayoutTransition());
        linearLayout5.setBackgroundColor(Color.GRAY);
        linearLayout5.setPadding(0, 5, 0, 5);
        layout.addView(linearLayout5);
    }

    private void setAlarm(Calendar targetCal,String subname) {

        System.out.println("set alarm called");
       // textAlarmPrompt.setText("\n\n***\n" + "Alarm is set@ " + targetCal.getTime() + "\n" + "***\n");
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        String filename=settings.filename;
        SharedPreferences data=getSharedPreferences(filename,0);
        String aTime=data.getString("alarmtime", "30").toString();

        Bundle b=new Bundle();
        b.putString("subname",subname);
        b.putString("time",aTime);
        intent.putExtras(b);
        int RQS_1=1;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm worked.", Toast.LENGTH_LONG).show();
        System.out.println("tome"+  targetCal.getTime());
    }
    public int percentage(int presents, int absents) {
        if (presents == 0 && absents == 0) {
            return 0;
        }
        return (int) ((((float) presents) / (((float) presents) + ((float) absents))) * 100.0f);
    }

    public void display(LinearLayout layout9, int percentage, TextView percent) {
        String filename=settings.filename;
        SharedPreferences data=getSharedPreferences(filename,0);
        String min=data.getString("minatt", "75").toString();
        int minatt=Integer.parseInt(min);
        if (percentage > minatt) {
            percent.setBackgroundColor(Color.GREEN);
            percent.setTextColor(Color.BLACK);
            return;
        }
        if (percentage > 10) {
            percent.setBackgroundColor(Color.RED);
            percent.setTextColor(-1);
        }
    }
}
