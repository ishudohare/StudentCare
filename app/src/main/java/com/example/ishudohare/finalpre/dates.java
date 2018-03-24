package com.example.ishudohare.finalpre;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class dates extends AppCompatActivity {
    String[] date;
    int id;
    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Bundle recv = getIntent().getExtras();
        this.id = recv.getInt("recordNo");
        initialize(this.id, Character.valueOf(recv.getChar("PorA")));
    }

    public void initialize(final int id, Character PorA) {
        DATABASE entry;
        String[] dates;
        int i;
        if (PorA.charValue() == 'P') {
            entry = new DATABASE(this);
            try {
                entry.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dates = entry.getPresent((long) id).split(",");
            entry.close();
            this.date = new String[(dates.length - 1)];
            for (i = 1; i < dates.length; i++) {
                this.date[i - 1] = dates[i];
            }
            this.list = (ListView) findViewById(R.id.list);
            this.list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.date));
            TextView heading = (TextView) findViewById(R.id.heading);
            heading.setText("PRESENT DATES:");
            heading.setTextColor(Color.rgb(34, 139, 34));
            heading.setTextSize(20.0f);
            this.list.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id1) {
                    Builder alert = new Builder(dates.this);
                    alert.setTitle("Are you sure to delete this date?");
                    alert.setMessage("It cannot be undone.");
                    alert.setPositiveButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id2) {
                            String[] yo = new String[(dates.this.date.length - 1)];
                            String update = BuildConfig.FLAVOR;
                            for (int i = 0; i < dates.this.date.length; i++) {
                                if (i == position) {
                                    System.out.println("i eql to pos");
                                } else if (i < position) {
                                    System.out.println("i less than pos");
                                    yo[i] = dates.this.date[i];
                                    update = update + "," + dates.this.date[i];
                                } else if (i > position) {
                                    yo[i - 1] = dates.this.date[i];
                                    update = update + "," + dates.this.date[i];
                                    System.out.println("i grtr than pos");
                                }
                            }
                            dates.this.list.setAdapter(new ArrayAdapter(dates.this, android.R.layout.simple_list_item_1, android.R.id.text1, yo));
                            DATABASE entry = new DATABASE(dates.this);
                            try {
                                entry.open();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            entry.updatePresent((long) id, update);
                            entry.close();
                            Toast.makeText(dates.this, "Deleted " + dates.this.date[position] + " will be updated after restart", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.show();
                }
            });
            return;
        }
        TextView heading = (TextView) findViewById(R.id.heading);
        heading.setText("ABSENT DATES:");
        heading.setTextColor(Color.RED);
        heading.setTextSize(20.0f);
        entry = new DATABASE(this);
        try {
            entry.open();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        String datesFromDatabase = entry.getAbsent((long) id);
        entry.close();
        dates = datesFromDatabase.split(",");
        this.date = new String[(dates.length - 1)];
        for (i = 1; i < dates.length; i++) {
            this.date[i - 1] = dates[i];
        }
        this.list = (ListView) findViewById(R.id.list);
        this.list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.date));
        this.list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id1) {
                Builder alert = new Builder(dates.this);
                alert.setTitle("Are you sure to delete this date?");
                alert.setMessage("It cannot be undone.");
                alert.setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id2) {
                        String[] yo = new String[(dates.this.date.length - 1)];
                        String update = BuildConfig.FLAVOR;
                        for (int i = 0; i < dates.this.date.length; i++) {
                            if (i != position) {
                                if (i < position) {
                                    yo[i] = dates.this.date[i];
                                    update = update + "," + dates.this.date[i];
                                } else if (i > position) {
                                    yo[i - 1] = dates.this.date[i];
                                    update = update + "," + dates.this.date[i];
                                }
                            }
                        }
                        dates.this.list.setAdapter(new ArrayAdapter(dates.this, android.R.layout.simple_list_item_1, android.R.id.text1, yo));
                        DATABASE entry = new DATABASE(dates.this);
                        try {
                            entry.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        entry.updateAbsent((long) id, update);
                        entry.close();
                        Toast.makeText(dates.this, "Deleted " + dates.this.date[position] + " will be updated after restart", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        });
    }
}
