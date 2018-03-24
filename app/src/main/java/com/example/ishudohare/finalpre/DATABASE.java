package com.example.ishudohare.finalpre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DATABASE {
    private static final String DATABASE_NAME = "Classes";
    private static final String DATABASE_TABLE = "subjectTable";
    private static final int DATABASE_VERSION = 2;
    public static final String KEY_ABSENT = "absent";
    public static final String KEY_CODE = "sub_code";
    public static final String KEY_DAYS = "days";
    public static final String KEY_NAME = "sub_name";
    public static final String KEY_PRESENT = "presents";
    public static final String KEY_ROWID = "_id";
    private Context ourContext;
    private SQLiteDatabase ourDatabase;
    private DbHelper ourHelper;

    public static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE.DATABASE_NAME, null, DATABASE.DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE subjectTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, sub_name TEXT NOT NULL, sub_code TEXT NOT NULL, days TEXT NOT NULL, absent INTEGER NOT NULL, presents INTEGER NOT NULL);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS subjectTable");
            onCreate(db);
        }
    }

    public DATABASE(Context c) {
        this.ourContext = c;
    }

    public DATABASE open() throws SQLException {
        this.ourHelper = new DbHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.ourHelper.close();
    }

    public long CreateEntry(String name, String code, String days) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CODE, code);
        cv.put(KEY_DAYS, days);
        cv.put(KEY_ABSENT, "");
        cv.put(KEY_PRESENT, "");
        return this.ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData() {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, null, null, null, null, null);
        String result = BuildConfig.FLAVOR;
        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iCode = c.getColumnIndex(KEY_CODE);
        int iDays = c.getColumnIndex(KEY_DAYS);
        int iTotalClasses = c.getColumnIndex(KEY_ABSENT);
        int iPresent = c.getColumnIndex(KEY_PRESENT);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            result = result + c.getString(iRow) + c.getString(iName) + c.getString(iCode) + c.getString(iDays) + "\n";
            c.moveToNext();
        }
        return result;
    }

    public boolean exists(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c != null) {
            c.moveToFirst();
            result = c.getString(1);
        }
        if (!result.equals("##")) {
            return true;
        }
        System.out.println("## encountered");
        return false;
    }

    public String getName(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(1);
    }

    public String getCode(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(2);
    }

    public String getTime(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c != null) {
            c.moveToFirst();
            String[] oneDay = c.getString(3).split(";");
            System.out.println("result and oneday to be printed");
            System.out.println("class_today called");
            for (int i = 0; i < oneDay.length; i++) {
                System.out.println("day to be declared");
                char day = oneDay[i].charAt(0);
                System.out.println("day declared");
                int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                System.out.println("today and daytoint to be printed");
                int daytoint = Integer.parseInt(String.valueOf(day));
                System.out.println("daytoint" + daytoint + "today" + today);
                if (daytoint == today - 2) {
                    System.out.println("a true");
                    String[] timing = oneDay[i].split("-");
                    System.out.println("timing" + timing[1]);
                    return timing[1];
                }
            }
        }
        return null;
    }

    public String getTimeTomorrow(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c != null) {
            c.moveToFirst();
            String[] oneDay = c.getString(3).split(";");
            System.out.println("result and oneday to be printed");
            System.out.println("class_today called");
            for (int i = 0; i < oneDay.length; i++) {
                System.out.println("day to be declared");
                char day = oneDay[i].charAt(0);
                System.out.println("day declared");
                int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                System.out.println("today and daytoint to be printed");
                int daytoint = Integer.parseInt(String.valueOf(day));
                System.out.println("daytoint" + daytoint + "today" + today);
                if (daytoint == today - 1) {
                    System.out.println("a true");
                    String[] timing = oneDay[i].split("-");
                    System.out.println("timing" + timing[1]);
                    return timing[1];
                }
            }
        }
        return null;
    }

    public void markAbsent(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c != null) {
            c.moveToFirst();
            result = c.getString(4);
        }
        long date = System.currentTimeMillis();
        result = result + "," + new SimpleDateFormat("dd MMM yyyy").format(Long.valueOf(date));
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_ABSENT, result);
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public int presentCount(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result ="";
        if (c == null) {
            return 0;
        }
        c.moveToFirst();
        return c.getString(5).split(",").length;
    }

    public int absentCount(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return 0;
        }
        c.moveToFirst();
        return c.getString(4).split(",").length;
    }

    public String getPresent(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(5);
    }

    public String getAbsent(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(4);
    }

    public void updatePresent(long l, String update) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_PRESENT, update);
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public void updateAbsent(long l, String update) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_ABSENT, update);
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public void markPresent(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = BuildConfig.FLAVOR;
        if (c != null) {
            c.moveToFirst();
            result = c.getString(5);
        }
        long date = System.currentTimeMillis();
        result = result + "," + new SimpleDateFormat("dd MMM yyyy").format(Long.valueOf(date));
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_PRESENT, result);
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public void updateData(long l) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_PRESENT, "1");
        cvUpdate.put(KEY_ABSENT, "1");
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public void deleteData(long l) {
        this.ourDatabase.delete(DATABASE_TABLE, "_id=" + l, null);
    }

    public void markDel(long l) {
        String str = BuildConfig.FLAVOR;
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, "##");
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public int getCount() {
        return this.ourDatabase.rawQuery("SELECT  * FROM subjectTable", null).getCount();
    }

    public boolean class_today(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = BuildConfig.FLAVOR;
        if (c != null) {
            c.moveToFirst();
            String[] oneDay = c.getString(3).split(";");
            System.out.println("result and oneday to be printed");
            System.out.println("class_today called");
            for (String charAt : oneDay) {
                System.out.println("day to be declared");
                char day = charAt.charAt(0);
                System.out.println("day declared");
                int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                System.out.println("today and daytoint to be printed");
                int daytoint = Integer.parseInt(String.valueOf(day));
                System.out.println("daytoint" + daytoint + "today" + today);
                if (daytoint == today - 2) {
                    System.out.println("a true");
                    return true;
                }
            }
        }
        return false;
    }

    public String whenclass(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(3);
    }

    public boolean class_tomorrow(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, "_id=" + l, null, null, null, null);
        String result = BuildConfig.FLAVOR;
        if (c != null) {
            c.moveToFirst();
            String[] oneDay = c.getString(3).split(";");
            System.out.println("result and oneday to be printed");
            System.out.println("class_today called");
            for (String charAt : oneDay) {
                System.out.println("day to be declared");
                char day = charAt.charAt(0);
                System.out.println("day declared");
                int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                System.out.println("today and daytoint to be printed");
                int daytoint = Integer.parseInt(String.valueOf(day));
                System.out.println("daytoint" + daytoint + "today" + today);
                if (daytoint == today - 1) {
                    System.out.println("a true");
                    return true;
                }
            }
        }
        return false;
    }
}
