package com.alexis.colval.giftrain.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexis.colval.giftrain.Model.Group;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexis on 2016-04-12.
 */
public class ParticipantHelper {
    private static ParticipantHelper instance;

    public static ParticipantHelper getInstance() {
        if(instance == null){
            instance = new ParticipantHelper();
        }
        return instance;
    }

    public void addParticipant(Context context, int profileId, int groupId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.ParticipantEntry.COLUMN_NAME_GROUP_ID, groupId);
        values.put(DatabaseContracts.ParticipantEntry.COLUMN_NAME_PROFILE_ID, profileId);

        DatabaseHelper.getInstance(context).insert(
                DatabaseContracts.ParticipantEntry.TABLE_NAME,
                null,
                values);
    }

    public Cursor getParticipantsByGroupId(Context context, int groupId) {
        String[] projection = {
                DatabaseContracts.ParticipantEntry.COLUMN_NAME_PROFILE_ID
        };
        String selection = DatabaseContracts.ParticipantEntry.COLUMN_NAME_GROUP_ID + "=?";
        String[] selectionArgs = {
                String.valueOf(groupId)
        };
        Cursor c = DatabaseHelper.getInstance(context).query(
                DatabaseContracts.GroupEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );

        return c;
    }
}
