package com.example.ishudohare.finalpre;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initialize();

    }
//
//    int id1;
    Intent Res;
    Bundle yo;
//    String[] all_desc;
//    //int count=0;
//    public static int ADDUDHAR=5;
//    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_udharspecific);
////        Bundle recv=getIntent().getExtras();
////        id1=recv.getInt("id");
////        initialize();
////    }
    public void initialize(){
        Res=new Intent();

        yo=new Bundle();

        DatabaseReminder entry = new DatabaseReminder(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
////        String name = entry.getName((long) id1);
////        Bitmap pic = entry.getImg((long) id1);
//        //System.out.println(subname + " " + subcode);
//     //   Bitmap resized = Bitmap.createScaledBitmap(pic, 100, 100, true);
//       // ImageView personpic=(ImageView)findViewById(R.id.ppic);
//      //  personpic.setImageBitmap(resized);
////        TextView pname=(TextView)findViewById(R.id.name);
////        String desc=entry.getDesc(id1);
////        all_desc=desc.split(";");
////        count=all_desc.length;
//
//        int total_money=0;
//        for(int j=1;j<all_desc.length;j++){
//            String[] oneEvent= all_desc[j].split(":");
//            total_money+=Integer.parseInt(oneEvent[1]);
//            System.out.println("card requested with "+oneEvent[0]+oneEvent[1]);
//            card(oneEvent[0], Integer.parseInt(oneEvent[1]), j);
//        }
//
//       // if(total_money>0)
//         //   pname.setText(name+" owes you ₹ "+total_money);
//        //else if(total_money<0)
//        //    pname.setText("You owe "+name+" ₹"+total_money);
//        //else pname.setText(name +" owes nothing");
//
        for (int i = 1; i <= entry.getCount(); i++) {
            System.out.println("we got a true condition ");
            if (entry.exists((long) i)) {
                String ename = entry.getEventName((long) i);
                String date = entry.getDates((long) i);
                int event_id=entry.getEventID(i);
                System.out.println(ename + " " + date);
                card(ename, date, i,event_id);
            }
        }
        entry.close();
    }
    public void card(final String ename, String date, final int id, final int event_id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.baselayout7);
        final LinearLayout layout2 = new LinearLayout(this);
        final LinearLayout layout3 = new LinearLayout(this);
        layout3.setPadding(30, 10, 0, 10);
        layout3.setBackgroundColor(Color.LTGRAY);
        layout3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        Random random = new Random();
        //layout2.setBackgroundColor(Color.rgb(random.nextInt(100) + 155, random.nextInt(155), random.nextInt(155)));
        layout2.setPadding(30, 10, 5, 10);
        layout2.setId(id);
        TextView name = new TextView(this);
        name.setText(ename);
        name.setId(id+100);
        name.setTextSize(24.0f);
//        if (money > 0)
            name.setTextColor(Color.parseColor("#3F51B5"));
//        else
//            name.setTextColor(Color.RED);
        System.out.println("adding textview in layout 2");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2, -2, 100.0f);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        LinearLayout layout5 = new LinearLayout(this);
        layout5.setLayoutParams(param);
        layout5.addView(name);
        layout5.setGravity(Gravity.CENTER_HORIZONTAL);
       // ImageView arrow = new ImageView(this);
        // arrow.setImageBitmap(pic);
      //  LinearLayout layout4 = new LinearLayout(this);
        //layout4.setLayoutParams(param1);
        // layout4.addView(arrow);
        //layout4.setPadding(10, 10, 10, 10);
//        if (money > 0)
//            layout4.setBackgroundColor(Color.parseColor("#3F51B5"));
//        else
//            layout4.setBackgroundColor(Color.RED);

        LinearLayout layout6 = new LinearLayout(this);
        layout6.setLayoutParams(param1);
        TextView money1 = new TextView(this);
        money1.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            money1.setText(date);
        } else {
            money1.setText(date);
        }
        money1.setTextSize(24.0f);
        layout6.addView(money1);
        layout2.setGravity(Gravity.CENTER_VERTICAL);
       // layout2.addView(layout4);
        layout2.addView(layout5);
        layout2.addView(layout6);
        System.out.println("added textview in layout 2");
        final int i = id;
        layout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);
                alert.setTitle("Want to delete " + ename+"?");
                alert.setMessage("It cannot be undone.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id2) {
                        layout2.removeAllViews();
                        layout3.removeAllViews();
                        DatabaseReminder entry=new DatabaseReminder(Main2Activity.this);

                        try {
                            entry.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        entry.markDel(i);
                        entry.close();
//                        Uri uri = ContentUris.withAppendedId(, Integer.parseInt(id));
//                        getContentResolver().delete(uri, null, null);

//                        int iNumRowsDeleted = 0;
//                        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
//                        Cursor cur = getContentResolver().query(eventsUri, null, null, null, null);
//
//                        while (cur.moveToNext()){
//
//                            Uri eventUri = ContentUris.withAppendedId(eventsUri, event_id);
//                            iNumRowsDeleted = getContentResolver().delete(eventUri, null, null);
//                        }



                        //   for(int k=1;k<all_desc.length;k++){
//                            if(k==i){
//
//                                System.out.println("continued for k ="+k +"and i="+i+"and all_Desc is"+all_desc[k]);
//                                continue;
//                            }
//                            desc1+=all_desc[k]+";";
//                            System.out.println("looped for "+k);
//                        }
//
//                        all_desc=desc1.split(";");
//                        int total_money=0;
//                        for(int j=1;j<all_desc.length;j++){
//                            String[] oneEvent= all_desc[j].split(":");
//                            total_money+=Integer.parseInt(oneEvent[1]);
//                            System.out.println("card requested with " + oneEvent[0] + oneEvent[1]);
//                        }
//                        TextView pname=(TextView)findViewById(R.id.name);
//
//                        String name = entry.getName((long) id1);
//
//                        if(total_money>0)
//                            pname.setText(name+" owes you ₹ "+total_money);
//                        else if(total_money<0)
//                            pname.setText("You owe "+name+" ₹"+total_money);
//                        else pname.setText(name +" owes nothing");
//
//                        System.out.println("deleted the netry and current desc is "+desc1);
//                        entry.updateDesc(id1, desc1);
//
                    }
                });
                alert.show();
            }
        });
//        System.out.println("adding layout2 in layout");
        layout.addView(layout2);
        layout.addView(layout3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(16);
        }
        System.out.println("added");

    }
//   // @Override
//    public void onBackPressed() {
//        setResult(RESULT_OK);
//        yo.putInt("id", id1);
//        Res.putExtras(yo);
//        setResult(RESULT_OK, Res);
//        finish();
//       // super.onBackPressed();
//    }
    public void addudhar(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mview = getLayoutInflater().inflate(R.layout.activity_addreminder, null);
        final EditText eventname = (EditText) mview.findViewById(R.id.eventname);
        final EditText desc = (EditText)mview.findViewById(R.id.edesc);
        final EditText time = (EditText)mview.findViewById(R.id.time);
        final CheckBox alarm=(CheckBox)mview.findViewById(R.id.alarm);
        final RadioGroup rgrp=(RadioGroup)mview.findViewById(R.id.who);
        final String[] repeat = {""};
        DatabaseReminder entry = new DatabaseReminder(Main2Activity.this);

        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RadioButton radio0=(RadioButton)mview.findViewById(R.id.radio0);
        RadioButton radio1=(RadioButton)mview.findViewById(R.id.radio1);
        RadioButton radio2=(RadioButton)mview.findViewById(R.id.radio2);
        RadioButton radio3=(RadioButton)mview.findViewById(R.id.radio3);

//        final String name=entry.getName(id1);
        Button ok=(Button)mview.findViewById(R.id.ok);
        final AlertDialog dialog;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventname.getText().toString().isEmpty() || desc.getText().toString().isEmpty()) {
                    Toast.makeText(Main2Activity.this, "Enter event name and date to save", Toast.LENGTH_SHORT).show();
                } else {


                  //  }
                    int what=rgrp.getCheckedRadioButtonId();
                    switch (what){
                        case R.id.radio0:
                            repeat[0] +="YEARLY";
                            break;
                        case R.id.radio1:
                          repeat[0] +="MONTHLY";
                           break;
                        case R.id.radio2:
                            repeat[0] +="DAILY";
                            break;
                        case R.id.radio3:
                            repeat[0] +="DAILY; COUNT=1";
                            break;

                    }
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy", Locale.ENGLISH);
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    System.out.println(time.getText().toString());
                    Date date = null;
                    try {
                        date = format.parse(time.getText().toString());
                        long millis = date.getTime()-19800000;
                        System.out.println("time in millis "+millis);
                        Random random=new Random();
                        int x=random.nextInt();
                        addAppointmentsToCalender(Main2Activity.this,eventname.getText().toString(),desc.getText().toString(),"",1,millis,alarm.isChecked(),false,x,repeat[0]);

//                    System.out.println("desc1 " + desc1);
                        DatabaseReminder entry = new DatabaseReminder(Main2Activity.this);

                        try {
                            entry.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

//                    entry.updateDesc(id1, desc1);
//                    yo.putInt("id",id1);
//                    Res.putExtras(yo);
                        String []da=time.getText().toString().split(" ");

                        entry.CreateEntry(eventname.getText().toString(),x,da[1]);
                        card(eventname.getText().toString(),da[1],entry.getCount(),x);
                        setResult(RESULT_OK, Res);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(Main2Activity.this, "Enter date as given in hint", Toast.LENGTH_SHORT).show();
                    }
                    finish();

                    ;
                }
            }
        });
        mBuilder.setView(mview);
        dialog=mBuilder.create();
        dialog.show();
    }

    public long addAppointmentsToCalender(Activity curActivity, String title,
                                          String desc, String place, int status, long startDate,
                                          boolean needReminder, boolean needMailService,long eventID,String freq) {
/***************** Event: add event *******************/

        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", title);
            eventValues.put("description", desc);
//            eventValues.put("eventLocation", place);
            eventValues.put(CalendarContract.Events.RRULE,"FREQ="+freq);

            long endDate = startDate + 1000 * 10 * 10; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", status); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT ");
 /*
  * Comment below visibility and transparency column to avoid
  * java.lang.IllegalArgumentException column visibility is invalid
  * error
  */
            // eventValues.put("allDay", 1);
            // eventValues.put("visibility", 0); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent (1).

            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = curActivity.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());

            if (needReminder) {
                /***************** Event: Reminder(with alert) Adding reminder to event ***********        ********/

                String reminderUriString = "content://com.android.calendar/reminders";
                ContentValues reminderValues = new ContentValues();
                reminderValues.put("event_id", eventID);
                reminderValues.put("minutes", 5); // Default value of the
                // system. Minutes is a integer
                reminderValues.put("method", 1); // Alert Methods: Default(0),
                // Alert(1), Email(2),SMS(3)

                Uri reminderUri = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminderValues);
            }

/***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

            if (needMailService) {
                String attendeuesesUriString = "content://com.android.calendar/attendees";
                /********
                 * To add multiple attendees need to insert ContentValues
                 * multiple times
                 ***********/
                ContentValues attendeesValues = new ContentValues();
                attendeesValues.put("event_id", eventID);
                attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
                attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee Email
                attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
                // Relationship_None(0),
                // Organizer(2),
                // Performer(3),
                // Speaker(4)
                attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
                // Required(2),
                // Resource(3)
                attendeesValues.put("attendeeStatus", 0); // NOne(0),
                // Accepted(1),
                // Decline(2),
                // Invited(3),
                // Tentative(4)

                Uri eventsUri = Uri.parse("content://calendar/events");
                Uri url = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(eventsUri, attendeesValues);

                // Uri attendeuesesUri = curActivity.getApplicationContext()
                // .getContentResolver()
                // .insert(Uri.parse(attendeuesesUriString), attendeesValues);
            }
        } catch (Exception ex) {
            // log.error("Error in adding event on calendar" + ex.getMessage());
        }

        return eventID;

    }

}


