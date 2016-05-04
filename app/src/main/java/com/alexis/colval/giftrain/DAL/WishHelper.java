package com.alexis.colval.giftrain.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alexis.colval.giftrain.Model.Wish;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexis on 2016-05-02.
 */
public class WishHelper {
    private static WishHelper instance;

    private static final String COMMA_SEP = ",";
    private static final String SQL_GET_INSERT_ID = "SELECT last_insert_rowid()";

    public WishHelper() {

    }

    public static WishHelper getInstance() {
        if(instance == null){
            instance = new WishHelper();
        }
        return instance;
    }

    public int addWish(Context context, String title, String theme, int price, int ownerId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_WISH_TITLE, title);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_THEME, theme);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_PRICE, price);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_OWNER_ID, ownerId);

        DatabaseHelper.getInstance(context).insert(
                DatabaseContracts.WishEntry.TABLE_NAME,
                null,
                values);
        Cursor c = DatabaseHelper.getInstance(context).rawQuery(SQL_GET_INSERT_ID, null);
        if(c.moveToFirst())
            return c.getInt(0);
        else
            return -1;
    }

    public void updateWish(Context context, int id, String title, String theme, int price, int ownerId){
        String strFilter = DatabaseContracts.WishEntry._ID + "=?";

        ContentValues values = new ContentValues();
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_WISH_TITLE, title);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_THEME, theme);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_PRICE, price);
        values.put(DatabaseContracts.WishEntry.COLUMN_NAME_OWNER_ID, ownerId);

        DatabaseHelper.getInstance(context).update(DatabaseContracts.WishEntry.TABLE_NAME, values, strFilter, new String[]{String.valueOf(id)});
    }

    public List<Wish> getWishesByProfileId(Context context, int ownerId) {
        String[] projection = {
                DatabaseContracts.WishEntry._ID,
                DatabaseContracts.WishEntry.COLUMN_NAME_WISH_TITLE,
                DatabaseContracts.WishEntry.COLUMN_NAME_THEME,
                DatabaseContracts.WishEntry.COLUMN_NAME_PRICE
        };
        String selection = DatabaseContracts.WishEntry.COLUMN_NAME_OWNER_ID + "=?";
        String[] selectionArgs = {
                String.valueOf(ownerId)
        };
        Cursor c = DatabaseHelper.getInstance(context).query(
                DatabaseContracts.WishEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort/order by
        );
        List<Wish> wishes = new LinkedList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(DatabaseContracts.WishEntry._ID));
            String title = c.getString(c.getColumnIndex(DatabaseContracts.WishEntry.COLUMN_NAME_WISH_TITLE));
            String theme = c.getString(c.getColumnIndex(DatabaseContracts.WishEntry.COLUMN_NAME_THEME));
            int price = c.getInt(c.getColumnIndex(DatabaseContracts.WishEntry.COLUMN_NAME_PRICE));
            wishes.add(new Wish(id, title, theme, price, ownerId));
        }
        return wishes;
    }
}
