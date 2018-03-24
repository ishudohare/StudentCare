package com.example.ishudohare.finalpre;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class MainActivitySchedule extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainschedule);
        final int id = getIntent().getExtras().getInt("id");
        initialize(id);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ((Button) findViewById(R.id.fab98)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder alert = new Builder(MainActivitySchedule.this);
                alert.setTitle("Are you sure to delete this subject?");
                alert.setMessage("It cannot be undone.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id2) {
                        DATABASE entry = new DATABASE(MainActivitySchedule.this);
                        try {
                            entry.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        entry.markDel((long) id);
                        entry.close();
                        Toast.makeText(MainActivitySchedule.this, "Will appear deleted after restart", Toast.LENGTH_SHORT).show();
                        MainActivitySchedule.this.finish();
                    }
                });
                alert.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initialize(int id) {
        TextView subjectname = (TextView) findViewById(R.id.subjectname);
        TextView monday = (TextView) findViewById(R.id.monday);
        TextView tuesday = (TextView) findViewById(R.id.tuesday);
        TextView wednesday = (TextView) findViewById(R.id.wednesday);
        TextView thursday = (TextView) findViewById(R.id.thursday);
        TextView friday = (TextView) findViewById(R.id.friday);
        TextView saturday = (TextView) findViewById(R.id.saturday);
        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String mon = "";
        String tue = "";
        String wed = "";
        String thurs = "";
        String fri = "";
        String sat = "";
        subjectname.setText(entry.getName((long) id));
        String[] classes = entry.whenclass((long) id).split(";");
        System.out.println("classes split");
        for (int i = 0; i < classes.length; i++) {
            int per;
            System.out.println("classes loop");
            char day = classes[i].charAt(0);
            System.out.println("classes char at 0");
            TextView attendance = (TextView) findViewById(R.id.attendance);
            int presents = entry.presentCount((long) id) - 1;
            int absents = entry.absentCount((long) id) - 1;
            if (presents == 0 && absents == 0) {
                per = 0;
            } else {
                per = (int) ((((float) presents) / (((float) presents) + ((float) absents))) * 100.0f);
            }
            attendance.setText("Attendance: " + per + "%");
            switch (day) {
                case '0' /*48*/:
                    String[] time = classes[i].split("-")[1].split("\\.");
                    mon = mon + time[0] + " - " + time[1] + ", ";
                    break;
                case '1' /*49*/:
                    String[] time1 = classes[i].split("-")[1].split("\\.");
                    tue = tue + time1[0] + " - " + time1[1] + ", ";
                    break;
                case '2' /*50*/:
                    String[] time2 = classes[i].split("-")[1].split("\\.");
                    wed = wed + time2[0] + " - " + time2[1] + ", ";
                    break;
                case '3' /*51*/:
                    String[] time3 = classes[i].split("-")[1].split("\\.");
                    thurs = thurs + time3[0] + " - " + time3[1] + ", ";
                    break;
                case '4' /*52*/:
                    String[] time4 = classes[i].split("-")[1].split("\\.");
                    fri = fri + time4[0] + " - " + time4[1] + ", ";
                    break;
                case '5' /*53*/:
                    String[] time5 = classes[i].split("-")[1].split("\\.");
                    sat = sat + time5[0] + " - " + time5[1] + ", ";
                    break;
                default:
                    break;
            }
        }
        if (mon != "") {
            monday.setText(mon);
        }
        if (tue != "") {
            tuesday.setText(tue);
        }
        if (wed != "") {
            wednesday.setText(wed);
        }
        if (thurs != "") {
            thursday.setText(thurs);
        }
        if (fri != "") {
            friday.setText(fri);
        }
        if (sat != "") {
            saturday.setText(sat);
        }
    }
}
