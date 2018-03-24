package com.example.ishudohare.finalpre;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Random;

public class Tomorrow extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        intialize();
    }

    public void intialize() {
        DATABASE entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = entry.getCount();
        int i = 1;
        while (i <= entry.getCount()) {
            System.out.println("we got a true condition ");
            if (entry.exists((long) i) && entry.class_tomorrow((long) i)) {
                String subname = entry.getName((long) i);
                String subcode = entry.getCode((long) i);
                String[] timing = entry.getTimeTomorrow((long) i).split("\\.");
                int presents = entry.presentCount((long) i) - 1;
                System.out.println("presents from database returned is " + presents);
                card(subname, subcode, timing, i, entry.absentCount((long) i) - 1, presents);
            }
            i++;
        }
        entry.close();
    }

    public void card(String subname, String code, String[] timing, int id, int absents, int presents) {
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
        layout.addView(linearLayout3);
        System.out.println("layoiy3 in layout2 set");
        layout.addView(linearLayout4);
        layout.setLayoutTransition(new LayoutTransition());
        linearLayout5.setBackgroundColor(Color.GRAY);
        linearLayout5.setPadding(0, 5, 0, 5);
        layout.addView(linearLayout5);
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
            percent.setTextColor(Color.BLACK);
            return;
        }
        if (percentage > 10) {
            percent.setBackgroundColor(Color.RED);
            percent.setTextColor(-1);
        }
    }
}
