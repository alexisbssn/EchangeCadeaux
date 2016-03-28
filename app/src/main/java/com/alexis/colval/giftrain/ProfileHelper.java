package com.alexis.colval.giftrain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexis on 2016-03-27.
 */
public class ProfileHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContracts.ProfileEntry.TABLE_NAME + " (" +
                    DatabaseContracts.ProfileEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE + DATE_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContracts.ProfileEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "giftrain.db";

    public ProfileHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is for testing purposes only, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean idExists(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID
        };
        String selection = DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + "=?";
        String[] selectionArgs = {
                id
        };

        Cursor c = db.query(
                DatabaseContracts.ProfileEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );
        return (c.moveToNext()); //true if found a record
    }
}
