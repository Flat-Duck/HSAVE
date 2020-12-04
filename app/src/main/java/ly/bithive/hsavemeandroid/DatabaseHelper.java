package ly.bithive.hsavemeandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ly.bithive.hsavemeandroid.model.Clink;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "save_ةe";

    // Table Names
    private static final String TABLE_CLINKS = "clinks";

    private static final String TABLE_STATS = "stats";
    private static final String TABLE_LOCALE = "locale";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_LOCALE = "locale";

    // CLINKS Table - column nmaes
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone_number";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";
    private static final String KEY_ADDRESS = "address";
    //   private static final String KEY_STATUS = "status";
    //  private static final String KEY_VISIBLE = "visible";


    // TAGS Table - column names
    private static final String KEY_TIMESTAMP = "time_stamp";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_CLINKS = "CREATE TABLE "
            + TABLE_CLINKS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_PHONE + " TEXT," + KEY_LAT
            + " REAL," + KEY_LNG + " REAL," + KEY_ADDRESS + " TEXT" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_STATS = "CREATE TABLE " + TABLE_STATS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIMESTAMP + " INTEGER" + ")";

 private static final String CREATE_TABLE_LOCALE = "CREATE TABLE " + TABLE_LOCALE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LOCALE + " TEXT" + ")";

    private static final String CREATE_TIME_STAMP = "INSERT INTO " + TABLE_STATS + "(" + KEY_TIMESTAMP + ") VALUES(" + 0L +");";
    private static final String CREATE_LOCALE = "INSERT INTO " + TABLE_LOCALE + "(" + KEY_LOCALE + ") VALUES('ar');";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CLINKS);
        db.execSQL(CREATE_TABLE_STATS);
        db.execSQL(CREATE_TIME_STAMP);
        db.execSQL(CREATE_TABLE_LOCALE);
        db.execSQL(CREATE_LOCALE);
      //  createTimeStamp();
    }

    private void createTimeStamp() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(KEY_TIMESTAMP, 0L);
        db.insert(TABLE_STATS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        // create new tables
        onCreate(db);
    }

    // ------------------------ "CLINKS" table methods ----------------//


    public long createClink(Clink clink) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, clink.getId());
        values.put(KEY_NAME, clink.getName());
        values.put(KEY_PHONE, clink.getPhone_number());
        values.put(KEY_LAT, clink.getLatitude());
        values.put(KEY_LNG, clink.getLongitude());
        values.put(KEY_ADDRESS, clink.getAddress());

        // insert row
        long clink_id = db.insert(TABLE_CLINKS, null, values);

        return clink_id;
    }

    /**
     * get single todo
     */
    public Clink getClink(long clink_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CLINKS + " WHERE " + KEY_ID + " = " + clink_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Clink clink = new Clink();
        clink.setId(c.getInt(c.getColumnIndex(KEY_ID)))
                .setName(c.getString(c.getColumnIndex(KEY_NAME)))
                .setPhone_number(c.getString(c.getColumnIndex(KEY_PHONE)))
                .setLatitude(c.getString(c.getColumnIndex(KEY_LAT)))
                .setLongitude(c.getString(c.getColumnIndex(KEY_LNG)))
                //.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)))
                //.setVisible(c.getInt(c.getColumnIndex(KEY_VISIBLE)))
                .setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));

        return clink;
    }

    /**
     * getting all clinks
     */
    public List<Clink> getAllClinks() {
        List<Clink> clinks = new ArrayList<Clink>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLINKS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clink clink = new Clink();
                clink
                        .setId(c.getInt(c.getColumnIndex(KEY_ID)))
                        .setName(c.getString(c.getColumnIndex(KEY_NAME)))
                        .setPhone_number(c.getString(c.getColumnIndex(KEY_PHONE)))
                        .setLatitude(c.getString(c.getColumnIndex(KEY_LAT)))
                        .setLongitude(c.getString(c.getColumnIndex(KEY_LNG)))
                        .setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));

                // adding to clinks list
                clinks.add(clink);
            } while (c.moveToNext());
        }

        return clinks;
    }


    /**
     * getting clinks count
     */
    public int getClinksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLINKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void deleteAllClinks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM CLINKS");
    }

    public void resetTimeStamp(long timeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TIMESTAMP, timeStamp);
        // db.update(TABLE_STATS,)
        if (db.update(TABLE_STATS, cv, KEY_ID + " = ?", new String[]{"1"}) == 1) {

        }
//        //SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
    }

    public void resetLocale(String locale) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_LOCALE, locale);
        if (db.update(TABLE_LOCALE, cv, KEY_ID + " = ?", new String[]{"1"}) == 1) {
        }
    }

    public long getTimeStamp() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STATS, new String[]{"*"}, null,
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getLong(1);
            }
            cursor.close();
        }
        db.close();
        return 0L;

    }
    public String getLocale() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCALE, new String[]{"*"}, null,
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getString(1);
            }
            cursor.close();
        }
        db.close();
        return "ar";

    }
}