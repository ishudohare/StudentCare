
package com.example.ishudohare.finalpre;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.sql.SQLException;

public class AddSubject extends AppCompatActivity implements OnClickListener {
    static final int TIME_DIALOG_ID_END = 1;
    static final int TIME_DIALOG_ID_START = 0;
    Button addFri;
    Button addMon;
    Button addSat;
    Button addThur;
    Button addTue;
    Button addWed;
    int friday;
    private OnTimeSetListener mTimeSetListener = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            AddSubject.this.pHour = hourOfDay;
            AddSubject.this.pMinute = minute;
        }
    };
    private OnTimeSetListener mendTimeSetListener = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            AddSubject.this.pHourEnd = hourOfDay;
            AddSubject.this.pMinuteEnd = minute;
        }
    };
    int monday;
    private int pHour;
    private int pHourEnd;
    private int pMinute;
    private int pMinuteEnd;
    int saturday;
    Button sav;
    String schdeule;
    TabHost th;
    int thursday;
    int tuesday;
    int wednesday;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        initialize();
    }

    public void initialize() {
        this.saturday = TIME_DIALOG_ID_END;
        this.friday = TIME_DIALOG_ID_END;
        this.thursday = TIME_DIALOG_ID_END;
        this.wednesday = TIME_DIALOG_ID_END;
        this.tuesday = TIME_DIALOG_ID_END;
        this.monday = TIME_DIALOG_ID_END;
        this.schdeule = BuildConfig.FLAVOR;
        this.sav = (Button) findViewById(R.id.save);
        this.addMon = (Button) findViewById(R.id.addMon);
        this.addTue = (Button) findViewById(R.id.addTue);
        this.addWed = (Button) findViewById(R.id.addWed);
        this.addThur = (Button) findViewById(R.id.addThur);
        this.addFri = (Button) findViewById(R.id.addFri);
        this.addSat = (Button) findViewById(R.id.addSat);
        this.addMon.setOnClickListener(this);
        this.addTue.setOnClickListener(this);
        this.addWed.setOnClickListener(this);
        this.addThur.setOnClickListener(this);
        this.addFri.setOnClickListener(this);
        this.addSat.setOnClickListener(this);
        this.th = (TabHost) findViewById(R.id.tabHost);
        this.th.setup();
        TabSpec spec = this.th.newTabSpec("Mon");
        spec.setContent(R.id.mon);
        spec.setIndicator("M");
        this.th.addTab(spec);
        spec = this.th.newTabSpec("Tue");
        spec.setContent(R.id.tue);
        spec.setIndicator("T");
        this.th.addTab(spec);
        spec = this.th.newTabSpec("Wed");
        spec.setContent(R.id.wed);
        spec.setIndicator("W");
        this.th.addTab(spec);
        spec = this.th.newTabSpec("Thur");
        spec.setContent(R.id.thur);
        spec.setIndicator("T");
        this.th.addTab(spec);
        spec = this.th.newTabSpec("Fri");
        spec.setContent(R.id.fri);
        spec.setIndicator("F");
        this.th.addTab(spec);
        spec = this.th.newTabSpec("Sat");
        spec.setContent(R.id.sat);
        spec.setIndicator("S");
        this.th.addTab(spec);
    }

    public void addViewsForPicking(int i) {
        LinearLayout layout = (LinearLayout) findViewById(i);
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        final EditText startTime = new EditText(this);
        startTime.setHint("Start Time");
        layout2.addView(startTime);
        TextView seperator = new TextView(this);
        seperator.setText("-");
        seperator.setTextSize(15.0f);
        layout2.addView(seperator);
        final EditText endTime = new EditText(this);
        endTime.setHint("End Time");
        switch (this.th.getCurrentTab()) {
            case 0 /*0*/:
                startTime.setId(this.monday + 1000);
                endTime.setId(this.monday + 2000);
                this.monday += TIME_DIALOG_ID_END;
                break;
            case 1/*1*/:
                startTime.setId(this.tuesday + 1100);
                endTime.setId(this.tuesday + 2100);
                this.tuesday += TIME_DIALOG_ID_END;
                break;
            case 2 /*2*/:
                startTime.setId(this.wednesday + 1200);
                endTime.setId(this.wednesday + 2200);
                this.wednesday += TIME_DIALOG_ID_END;
                break;
            case 3 /*3*/:
                startTime.setId(this.thursday + 1300);
                endTime.setId(this.thursday + 2300);
                this.thursday += TIME_DIALOG_ID_END;
                break;
            case 4 /*4*/:
                startTime.setId(this.friday + 1400);
                endTime.setId(this.friday + 2400);
                this.friday += TIME_DIALOG_ID_END;
                break;
            case 5 /*5*/:
                startTime.setId(this.saturday + 1500);
                endTime.setId(this.saturday + 2500);
                System.out.println("sat");
                this.saturday += TIME_DIALOG_ID_END;
                break;
        }
        endTime.setFocusable(false);
        startTime.setFocusable(false);
        layout2.addView(endTime);
        layout.addView(layout2);
        startTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(AddSubject.this, new OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(new StringBuilder().append(AddSubject.pad(hourOfDay)).append(":").append(AddSubject.pad(minute)));
                    }
                }, AddSubject.this.pHour, AddSubject.this.pMinute, false).show();
            }
        });
        endTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(AddSubject.this, new OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(new StringBuilder().append(AddSubject.pad(hourOfDay)).append(":").append(AddSubject.pad(minute)));
                    }
                }, AddSubject.this.pHourEnd, AddSubject.this.pMinuteEnd, false).show();
            }
        });
    }

    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        }
        return "0" + String.valueOf(c);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID_START /*0*/:
                return new TimePickerDialog(this, this.mTimeSetListener, this.pHour, this.pMinute, false);
            case TIME_DIALOG_ID_END /*1*/:
                return new TimePickerDialog(this, this.mendTimeSetListener, this.pHourEnd, this.pMinuteEnd, false);
            default:
                return null;
        }
    }

    private void updateDisplay(int id1, int hour, int minute) {
        ((EditText) findViewById(id1)).setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
    }

    public boolean validate(String name, String code, String days) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Subject name not given.Can't be saved", Toast.LENGTH_SHORT).show();
            return false;
        } else if (code.isEmpty()) {
            Toast.makeText(this, "Subject code not given.Can't be saved",Toast.LENGTH_SHORT).show();
            return false;
        } else if (days != "") {
            return true;
        } else {
            Toast.makeText(this, "Timing of classes not given correctly.Can't be saved", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void done(View view) {
        int i;
        String sname = ((TextView) findViewById(R.id.subname)).getText().toString();
        String scode = ((TextView) findViewById(R.id.subcode)).getText().toString();
        String dys = "";

        int error = 0;
        for (i = 1; i < this.monday; i += TIME_DIALOG_ID_END) {
            String strttime = ((EditText) findViewById(i + 1000)).getText().toString();
            String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2000)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()){
                dys = dys + String.valueOf(new StringBuilder().append("0").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        for (i = TIME_DIALOG_ID_END; i < this.tuesday; i += TIME_DIALOG_ID_END) {
          String  strttime = ((EditText) findViewById(i + 1100)).getText().toString();
           String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2100)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()){
                dys = dys + String.valueOf(new StringBuilder().append("1").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        for (i = TIME_DIALOG_ID_END; i < this.wednesday; i += TIME_DIALOG_ID_END) {
            String strttime = ((EditText) findViewById(i + 1200)).getText().toString();
            String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2200)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()) {
                dys = dys + String.valueOf(new StringBuilder().append("2").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        for (i = TIME_DIALOG_ID_END; i < this.thursday; i += TIME_DIALOG_ID_END) {
            String strttime = ((EditText) findViewById(i + 1300)).getText().toString();
            String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2300)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()) {
                dys = dys + String.valueOf(new StringBuilder().append("3").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        for (i = TIME_DIALOG_ID_END; i < this.friday; i += TIME_DIALOG_ID_END) {
            String strttime = ((EditText) findViewById(i + 1400)).getText().toString();
            String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2400)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()) {
                dys = dys + String.valueOf(new StringBuilder().append("4").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        for (i = TIME_DIALOG_ID_END; i < this.saturday; i += TIME_DIALOG_ID_END) {
            String strttime = ((EditText) findViewById(i + 1500)).getText().toString();
            String[] start = strttime.split(":");
            String ndtime = ((EditText) findViewById(i + 2500)).getText().toString();
            String[] end = ndtime.split(":");
            if (strttime.isEmpty() || ndtime.isEmpty()) {
                if (!strttime.isEmpty() && ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
                if (strttime.isEmpty() && !ndtime.isEmpty()) {
                    error = TIME_DIALOG_ID_END;
                }
            }
            if(!strttime.isEmpty()&&!ndtime.isEmpty()) {
                dys = dys + String.valueOf(new StringBuilder().append("5").append("-").append(pad(Integer.parseInt(start[0]))).append(":").append(pad(Integer.parseInt(start[TIME_DIALOG_ID_END]))).append(".").append(pad(Integer.parseInt(end[0]))).append(":").append(pad(Integer.parseInt(end[TIME_DIALOG_ID_END]))).append(";"));
            }
        }
        if (error == TIME_DIALOG_ID_END) {
            dys = "";
        }
        if (validate(sname, scode, dys)) {
            DATABASE entry = new DATABASE(this);
            try {
                entry.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            entry.CreateEntry(sname, scode, dys);
            setResult(RESULT_OK);
            finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMon /*2131492969*/:
                addViewsForPicking(R.id.finalMon);
                return;
            case R.id.addTue /*2131492972*/:
                addViewsForPicking(R.id.finalTue);
                return;
            case R.id.addWed /*2131492975*/:
                addViewsForPicking(R.id.finalWed);
                return;
            case R.id.addThur /*2131492978*/:
                addViewsForPicking(R.id.finalThur);
                return;
            case R.id.addFri /*2131492981*/:
                addViewsForPicking(R.id.finalFri);
                return;
            case R.id.addSat /*2131492984*/:
                addViewsForPicking(R.id.finalSat);
                return;
            default:
                return;
        }
    }
}
