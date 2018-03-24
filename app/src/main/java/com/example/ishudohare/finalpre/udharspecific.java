package com.example.ishudohare.finalpre;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Random;

public class udharspecific extends AppCompatActivity {
    int id1;
    Intent Res;
    Bundle yo;
    String[] all_desc;
    //int count=0;
    public static int ADDUDHAR=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udharspecific);
        Bundle recv=getIntent().getExtras();
        id1=recv.getInt("id");
    initialize();
    }
    public void initialize(){
        Res=new Intent();

        yo=new Bundle();

        Udhardatabase entry = new Udhardatabase(this);
        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String name = entry.getName((long) id1);
        Bitmap pic = entry.getImg((long) id1);
        //System.out.println(subname + " " + subcode);
        Bitmap resized = Bitmap.createScaledBitmap(pic, 100, 100, true);
        ImageView personpic=(ImageView)findViewById(R.id.ppic);
        personpic.setImageBitmap(resized);
        TextView pname=(TextView)findViewById(R.id.name);
        String desc=entry.getDesc(id1);
        all_desc=desc.split(";");
//        count=all_desc.length;

        int total_money=0;
        for(int j=1;j<all_desc.length;j++){
            String[] oneEvent= all_desc[j].split(":");
                total_money+=Integer.parseInt(oneEvent[1]);
            System.out.println("card requested with "+oneEvent[0]+oneEvent[1]);
                card(oneEvent[0], Integer.parseInt(oneEvent[1]), j);
        }

        if(total_money>0)
            pname.setText(name+" owes you ₹ "+total_money);
        else if(total_money<0)
            pname.setText("You owe "+name+" ₹"+total_money);
        else pname.setText(name +" owes nothing");

    }
    public void card(final String ename, int money, int id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.baselayout4);
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
        if (money > 0)
            name.setTextColor(Color.parseColor("#3F51B5"));
        else
            name.setTextColor(Color.RED);
        System.out.println("adding textview in layout 2");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2, -2, 100.0f);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        LinearLayout layout5 = new LinearLayout(this);
        layout5.setLayoutParams(param);
        layout5.addView(name);
        layout5.setGravity(Gravity.CENTER_HORIZONTAL);
        ImageView arrow = new ImageView(this);
        // arrow.setImageBitmap(pic);
        LinearLayout layout4 = new LinearLayout(this);
        layout4.setLayoutParams(param1);
        // layout4.addView(arrow);
        layout4.setPadding(10, 10, 10, 10);
        if (money > 0)
            layout4.setBackgroundColor(Color.parseColor("#3F51B5"));
        else
            layout4.setBackgroundColor(Color.RED);

        LinearLayout layout6 = new LinearLayout(this);
        layout6.setLayoutParams(param1);
        TextView money1 = new TextView(this);
        money1.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            money1.setText("₹" + money);
        } else {
            money1.setText("Rs." + money);
        }
        money1.setTextSize(24.0f);
        layout6.addView(money1);
        layout2.setGravity(Gravity.CENTER_VERTICAL);
        layout2.addView(layout4);
        layout2.addView(layout5);
        layout2.addView(layout6);
        System.out.println("added textview in layout 2");
        final int i = id;
        layout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(udharspecific.this);
                alert.setTitle("Want to settle " + ename+"?");
                alert.setMessage("It cannot be undone.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id2) {
                        layout2.removeAllViews();
                        layout3.removeAllViews();
                     String desc1="noeevnt:0;";
                        Udhardatabase entry=new Udhardatabase(udharspecific.this);

                        try {
                            entry.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        for(int k=1;k<all_desc.length;k++){
                         if(k==i){

                             System.out.println("continued for k ="+k +"and i="+i+"and all_Desc is"+all_desc[k]);
                             continue;
                         }
                            desc1+=all_desc[k]+";";
        System.out.println("looped for "+k);
                        }

                        all_desc=desc1.split(";");
                        int total_money=0;
                        for(int j=1;j<all_desc.length;j++){
                            String[] oneEvent= all_desc[j].split(":");
                            total_money+=Integer.parseInt(oneEvent[1]);
                            System.out.println("card requested with " + oneEvent[0] + oneEvent[1]);
                        }
                        TextView pname=(TextView)findViewById(R.id.name);

                        String name = entry.getName((long) id1);

                        if(total_money>0)
                            pname.setText(name+" owes you ₹ "+total_money);
                        else if(total_money<0)
                            pname.setText("You owe "+name+" ₹"+total_money);
                        else pname.setText(name +" owes nothing");

                        System.out.println("deleted the netry and current desc is "+desc1);
                        entry.updateDesc(id1, desc1);

                    }
                });
                alert.show();
            }
        });
        System.out.println("adding layout2 in layout");
        layout.addView(layout2);
        layout.addView(layout3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(16);
        }
        System.out.println("added");

    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        yo.putInt("id", id1);
        Res.putExtras(yo);
        setResult(RESULT_OK, Res);
        finish();
        super.onBackPressed();
    }
    public void addudhar(View view) {
//        Intent i= new Intent(this,AddUdhar.class);
//        startActivityForResult(i,ADDUDHAR);
//        Bundle sent=new Bundle();
//        sent.putInt("id",id1);
//        i.putExtras(sent);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mview = getLayoutInflater().inflate(R.layout.activity_add_udhar, null);
        final EditText eventname = (EditText) mview.findViewById(R.id.eventname);
        final EditText amount = (EditText)mview.findViewById(R.id.amount);
        final RadioGroup rgrp=(RadioGroup)mview.findViewById(R.id.who);
        Udhardatabase entry = new Udhardatabase(udharspecific.this);

        try {
            entry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RadioButton radio0=(RadioButton)mview.findViewById(R.id.radio0);
        RadioButton radio1=(RadioButton)mview.findViewById(R.id.radio1);
       final String name=entry.getName(id1);
        radio0.setText(name+" took money from you");
        radio1.setText("You took money from "+name);
        Button ok=(Button)mview.findViewById(R.id.ok);
        final AlertDialog dialog;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventname.getText().toString().isEmpty() || amount.getText().toString().isEmpty()) {
                    Toast.makeText(udharspecific.this, "Enter Amount and Event name to save", Toast.LENGTH_SHORT).show();
                } else {
                    String desc1="";
                    for (int k = 0; k < all_desc.length; k++) {
                        desc1 += all_desc[k] + ";";

                    }
                    int what=rgrp.getCheckedRadioButtonId();
                    switch (what){
                        case R.id.radio0:

                            String addthis=eventname.getText().toString()+":"+amount.getText().toString()+";";
                            desc1+=addthis;

                            all_desc=desc1.split(";");
                            card(eventname.getText().toString(),Integer.parseInt(amount.getText().toString()),all_desc.length);
                            break;
                        case R.id.radio1:
                            String addthis1=eventname.getText().toString()+":-"+amount.getText().toString()+";";
                            desc1+=addthis1;

                            all_desc=desc1.split(";");
                            card(eventname.getText().toString(),Integer.parseInt(amount.getText().toString())*-1,all_desc.length);
                            break;

                    }
                    System.out.println("desc1 " + desc1);
                    Udhardatabase entry = new Udhardatabase(udharspecific.this);

                    try {
                        entry.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    entry.updateDesc(id1, desc1);
                    yo.putInt("id",id1);
                    Res.putExtras(yo);
                    setResult(RESULT_OK, Res);
                    finish();

                    ;
                }
            }
        });
        mBuilder.setView(mview);
        dialog=mBuilder.create();
        dialog.show();
    }
}
