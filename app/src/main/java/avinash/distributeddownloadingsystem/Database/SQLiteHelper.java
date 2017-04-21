package avinash.distributeddownloadingsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Avinash Sharma on 14-Apr-17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Downloads";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_INFO = "Download_Info";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_FILENAME = "FileName";
    //public static final String COLUMN_EXT = "Extension";
    public static final String COLUMN_URL = "URL";
    public static final String COLUMN_KEY = "Key";
    public static final String COLUMN_isADMIN="isAdmin";
   // public static final String COLUMN_STATUS="Status";
    //public static final String COLUMN_REASON = "Reason"
    //private String[] allColumns = { COLUMN_ID, COLUMN_FILENAME, COLUMN_URL, COLUMN_KEY, COLUMN_isADMIN};


    private static final String DATABASE_CREATE = "create table "
            + TABLE_INFO + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_FILENAME
            + " text not null, " + COLUMN_URL

            + " text not null, " + COLUMN_isADMIN + " integer not null, "
            + COLUMN_KEY  + " text not null" + ");";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
    }
    public Cursor GetListCursor()
    {    SQLiteDatabase db=getWritableDatabase();
         String query = "SELECT * FROM " + TABLE_INFO;
        Cursor c = db.rawQuery(query, null);
        //Cursor c = db.query(TABLE_INFO, allColumns, null, null, null, null, null);

        return c;
    }

    public void addRow(Download_Info DI)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID,DI.getId());
        values.put(COLUMN_FILENAME,DI.getFileName());
        values.put(COLUMN_URL, DI.getURL());

         values.put(COLUMN_isADMIN,DI.getAdmin());
        values.put(COLUMN_KEY, DI.getKey());

        db.insert(TABLE_INFO, null, values);
        db.close();
    }

    public Cursor getKeys(String key)
    {SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_KEY +" FROM " + TABLE_INFO + " WHERE " + COLUMN_KEY + " = " + "'"+key+"'";
        return db.rawQuery(query,null);
    }


   /* public void deleteRow(String filename)
    {

    }*/




}
