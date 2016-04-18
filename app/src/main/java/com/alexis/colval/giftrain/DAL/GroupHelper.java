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
public class GroupHelper {

    private static GroupHelper instance;

    private static final String COMMA_SEP = ",";
    private static final String SQL_GET_INSERT_ID = "SELECT last_insert_rowid()";

    public GroupHelper() {

    }

    public static GroupHelper getInstance() {
        if(instance == null){
            instance = new GroupHelper();
        }
        return instance;
    }

    public int addGroup(Context context, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, String theme) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME, groupName);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION, groupDescription);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE, scope);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID, creatorId);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL, drawInterval);

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, drawInterval);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW, dateFormat.format(calendar.getTime()));
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_THEME, theme);

        DatabaseHelper.getInstance(context).insert(
                DatabaseContracts.GroupEntry.TABLE_NAME,
                null,
                values);
        Cursor c = DatabaseHelper.getInstance(context).rawQuery(SQL_GET_INSERT_ID, null);
        if(c.moveToFirst())
            return c.getInt(0);
        else
            return -1;
    }

    public void updateGroup(Context context, int id, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, Date nextDraw, String theme){
        String strFilter = DatabaseContracts.GroupEntry._ID + "=?";

        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME, groupName);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION, groupDescription);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE, scope);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID, creatorId);
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL, drawInterval);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW, dateFormat.format(nextDraw));
        values.put(DatabaseContracts.GroupEntry.COLUMN_NAME_THEME, theme);

        DatabaseHelper.getInstance(context).update(DatabaseContracts.GroupEntry.TABLE_NAME, values, strFilter, new String[]{String.valueOf(id)});
    }

    public List<Group> getGroupsByProfileId(Context context, int profileId) {
        String[] projection = {
                DatabaseContracts.GroupEntry._ID,
                DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME,
                DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION,
                DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW,
                DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL,
                DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE,
                DatabaseContracts.GroupEntry.COLUMN_NAME_RATING,
                DatabaseContracts.GroupEntry.COLUMN_NAME_THEME
        };
        String selection = DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID + "=?";
        String[] selectionArgs = {
                String.valueOf(profileId)
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
        List<Group> groups = new LinkedList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry._ID));
            String name = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME));
            String descr = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION));
            String datetime = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW));
            int interval = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL));
            int scope = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE));
            double rating = c.getDouble(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_RATING));
            String theme = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_THEME));

            Date nextDraw = null;
            DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                nextDraw = iso8601Format.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            groups.add(new Group(id, name, descr, scope, profileId, interval, nextDraw, rating, theme));
        }
        return groups;
    }

    public List<Group> findGroupsByName(Context context, String search, String theme) {
        String[] projection = {
                DatabaseContracts.GroupEntry._ID,
                DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME,
                DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION,
                DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW,
                DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL,
                DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE,
                DatabaseContracts.GroupEntry.COLUMN_NAME_RATING,
                DatabaseContracts.GroupEntry.COLUMN_NAME_THEME,
                DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID
        };
        String selection;
        String[] selectionArgs;
        if(!theme.equals("")) {
            selection = DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME + " LIKE ? AND " + DatabaseContracts.GroupEntry.COLUMN_NAME_THEME + "=?";
            selectionArgs = new String[]{
                    "%" + search + "%",
                    theme
            };
        }else{
            selection = DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME + " LIKE ?";
            selectionArgs = new String[]{
                    "%" + search + "%"
            };
        }
        Cursor c = DatabaseHelper.getInstance(context).query(
                DatabaseContracts.GroupEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );
        List<Group> groups = new LinkedList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry._ID));
            String name = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME));
            String descr = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION));
            String datetime = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW));
            int interval = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL));
            int scope = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE));
            double rating = c.getDouble(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_RATING));
            String them = c.getString(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_THEME));
            int creatorId = c.getInt(c.getColumnIndex(DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID));

            Date nextDraw = null;
            DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                nextDraw = iso8601Format.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            groups.add(new Group(id, name, descr, scope, creatorId, interval, nextDraw, rating, them));
        }
        return groups;
    }
}
