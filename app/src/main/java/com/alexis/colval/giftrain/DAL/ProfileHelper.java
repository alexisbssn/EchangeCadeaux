package com.alexis.colval.giftrain.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexis.colval.giftrain.DAL.DatabaseContracts;
import com.alexis.colval.giftrain.Model.Profile;

/**
 * Created by alexis on 2016-03-27.
 */
public class ProfileHelper{
    private static ProfileHelper instance;

    public boolean googleIdExists(Context context, String id){


        String[] projection = {
                DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID
        };
        String selection = DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + "=?";
        String[] selectionArgs = {
                id
        };

        Cursor c = DatabaseHelper.getInstance(context).query(
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

    public static ProfileHelper getInstance() {
        if(instance == null){
            instance = new ProfileHelper();
        }
        return instance;
    }

    public int getProfileIdByGoogleId(Context context, String googleId) {
        String[] projection = {
                DatabaseContracts.ProfileEntry._ID
        };
        String selection = DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + "=?";
        String[] selectionArgs = {
                googleId
        };
        Cursor c = DatabaseHelper.getInstance(context).query(
                DatabaseContracts.ProfileEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );
        c.moveToFirst();
        return c.getInt(c.getColumnIndex(DatabaseContracts.ParticipantEntry._ID));
    }

    public Profile getProfileById(Context appContext, int id) {
        String[] projection = {
                DatabaseContracts.ProfileEntry._ID,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL,
                DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION
        };
        String selection = DatabaseContracts.ProfileEntry._ID + "=?";
        String[] selectionArgs = {
                String.valueOf(id)
        };
        Cursor c = DatabaseHelper.getInstance(appContext).query(
                DatabaseContracts.ProfileEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );
        c.moveToFirst();
        Profile p = new Profile(
                id,
                c.getInt(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION)),
                c.getString(c.getColumnIndex(DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL))
        );
        return p;
    }
}
