package com.example.ishudohare.finalpre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**

 */public class DatabaseReminder {
    private static final String DATABASE_NAME = "Reminder";
    private static final String DATABASE_TABLE = "ReminderTable";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ENAME = "eventname";
    public static final String KEY_EID = "eventid";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_DATE="date";
    private Context ourContext;
    private SQLiteDatabase ourDatabase;
    private DbHelper ourHelper;

    public static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ENAME + " TEXT NOT NULL, "
                    + KEY_EID + " INT, " + KEY_DATE +" TEXT NOT NULL " +")");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DatabaseReminder(Context c) {
        this.ourContext = c;
    }

    public DatabaseReminder open() throws SQLException {
        this.ourHelper = new DbHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.ourHelper.close();
    }

    public void CreateEntry(String ename, int EventID, String date) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ENAME, ename);
        cv.put(KEY_EID,EventID);
        cv.put(KEY_DATE, date);
        System.out.println("database insertion");
        ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public boolean exists(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_ENAME, KEY_EID, KEY_DATE}, "_id=" + l, null, null, null, null);
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
//    // public String getData() {
//        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_CODE, KEY_DAYS, KEY_ABSENT, KEY_PRESENT}, null, null, null, null, null);
    //      String result = "";
//        int iRow = c.getColumnIndex(KEY_ROWID);
//        int iName = c.getColumnIndex(KEY_NAME);
//        int iCode = c.getColumnIndex(KEY_CODE);
//        int iDays = c.getColumnIndex(KEY_DAYS);
//        int iTotalClasses = c.getColumnIndex(KEY_ABSENT);
//        int iPresent = c.getColumnIndex(KEY_PRESENT);
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//            result = result + c.getString(iRow) + c.getString(iName) + c.getString(iCode) + c.getString(iDays) + "\n";
//            c.moveToNext();
//        }
//        return result;
    //  }


    public String getEventName (long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_ENAME, KEY_EID, KEY_DATE}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(1);
    }

    public int getEventID (long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_ENAME, KEY_EID, KEY_DATE}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return 0;
        }
        c.moveToFirst();
        return c.getInt(2);
    }
    public String getDates(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_ENAME, KEY_EID, KEY_DATE}, "_id=" + l, null, null, null, null);
        if (c == null) {
            return null;
        }
        c.moveToFirst();
//        Inflater decompresser = new Inflater();
//        decompresser.setInput(img, 0, compressedDataLength);
//        byte[] result = new byte[100];
//        int resultLength = decompresser.inflate(result);
//        decompresser.end();

        return c.getString(3);
    }

//    public String getDesc(long l) {
//        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
//        String result = "";
//        if (c == null) {
//            return null;
//        }
//        c.moveToFirst();
//        return c.getString(3);
//
//    }
//
//    public void updateDesc(long l, String update) {
//        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
//        ContentValues cvUpdate = new ContentValues();
//        cvUpdate.put(KEY_DESC,update);
//        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
//    }
//

    //    public void updateData(long l) {
//        ContentValues cvUpdate = new ContentValues();
//        cvUpdate.put(KEY_PRESENT, "1");
//        cvUpdate.put(KEY_ABSENT, "1");
//        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    //  }
//
//    public void deleteData(long l) {
//        this.ourDatabase.delete(DATABASE_TABLE, "_id=" + l, null);
//    }
//
    public void markDel(long l) {
        String str = "";
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_ENAME, "##");
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }

    public int getCount() {
        return this.ourDatabase.rawQuery("SELECT  * FROM "+ DATABASE_TABLE, null).getCount();
    }


}
