package com.example.ishudohare.finalpre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.SQLException;

public class Udhardatabase {
    private static final String DATABASE_NME = "Udhar";
    private static final String DATABASE_TABLE = "udhartable";
    private static final int DATABASE_VERSION = 11;
    public static final String KEY_NAME = "personname";
    public static final String KEY_PIC = "personpic";
    public static final String KEY_DESC = "Udhardesc";
    public static final String KEY_ROWID = "_id";
    private Context ourContext;
    private SQLiteDatabase ourDatabase;
    private Udhardatabase.DbHelper ourHelper;

    public static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, Udhardatabase.DATABASE_NME, null, Udhardatabase.DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, "
                    + KEY_PIC + " BLOB, " + KEY_DESC +" TEXT NOT NULL " +")");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
            onCreate(db);
        }
    }

    public Udhardatabase(Context c) {
        this.ourContext = c;
    }

    public Udhardatabase open() throws SQLException {
        this.ourHelper = new DbHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.ourHelper.close();
    }

    public void CreateEntry(String name, byte[] pic, String desc) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PIC,pic);
        cv.put(KEY_DESC, "no event:0;");
        System.out.println("database insertion");
        ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public boolean exists(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
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
   // public String getData() {
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


    public String getName (long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(1);
    }

    public Bitmap getImg(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
        Bitmap result;
        if (c == null) {
            return null;
        }
        c.moveToFirst();
         byte[] img=c.getBlob(2);
//        Inflater decompresser = new Inflater();
//        decompresser.setInput(img, 0, compressedDataLength);
//        byte[] result = new byte[100];
//        int resultLength = decompresser.inflate(result);
//        decompresser.end();
        result= BitmapFactory.decodeByteArray(img,0,img.length);
        return result;
    }

    public String getDesc(long l) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
        String result = "";
        if (c == null) {
            return null;
        }
        c.moveToFirst();
        return c.getString(3);

    }

    public void updateDesc(long l, String update) {
        Cursor c = this.ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_PIC, KEY_DESC}, "_id=" + l, null, null, null, null);
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_DESC,update);
        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
    }


    public void updateData(long l) {
//        ContentValues cvUpdate = new ContentValues();
//        cvUpdate.put(KEY_PRESENT, "1");
//        cvUpdate.put(KEY_ABSENT, "1");
//        this.ourDatabase.update(DATABASE_TABLE, cvUpdate, "_id=" + l, null);
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
        return this.ourDatabase.rawQuery("SELECT  * FROM "+ DATABASE_TABLE, null).getCount();
    }


}
