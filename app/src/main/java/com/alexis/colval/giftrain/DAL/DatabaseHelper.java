package com.alexis.colval.giftrain.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexis on 2016-04-17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "giftrain.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SEMICOLON_SEP = ";";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContracts.ProfileEntry.TABLE_NAME + SEMICOLON_SEP +
                    "DROP TABLE IF EXISTS " + DatabaseContracts.GroupEntry.TABLE_NAME + SEMICOLON_SEP +
                    "DROP TABLE IF EXISTS " + DatabaseContracts.CommentEntry.TABLE_NAME + SEMICOLON_SEP +
                    "DROP TABLE IF EXISTS " + DatabaseContracts.ParticipantEntry.TABLE_NAME;


    private static final String SQL_CREATE_ENTRIES_PROFILE =
            "CREATE TABLE " + DatabaseContracts.ProfileEntry.TABLE_NAME + " (" +
                    DatabaseContracts.ProfileEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_GOOGLE_ID + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_FNAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_LNAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_BDATE + DATE_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_REGION + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.ProfileEntry.COLUMN_NAME_PHOTO_URL + TEXT_TYPE +
                    " );";
    private static final String SQL_CREATE_ENTRIES_GROUP =
            "CREATE TABLE " + DatabaseContracts.GroupEntry.TABLE_NAME + "(" +
                    DatabaseContracts.GroupEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_GROUP_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_SCOPE + INTEGER_TYPE + COMMA_SEP +
                    // Scope: 0 for Public, 1 for creator-invite, 2 for member-invite, maybe other values for other uses in the future
                    DatabaseContracts.GroupEntry.COLUMN_NAME_INTERVAL + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_NEXT_DRAW + DATE_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_RATING + REAL_TYPE + COMMA_SEP +
                    DatabaseContracts.GroupEntry.COLUMN_NAME_THEME + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY(" + DatabaseContracts.GroupEntry.COLUMN_NAME_CREATOR_ID + ") REFERENCES " +
                    DatabaseContracts.ProfileEntry.TABLE_NAME + "(" + DatabaseContracts.GroupEntry._ID + ")" +
                    " );";
    private static final String SQL_CREATE_ENTRIES_WISH =
            "CREATE TABLE " + DatabaseContracts.WishEntry.TABLE_NAME + "(" +
                    DatabaseContracts.WishEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DatabaseContracts.WishEntry.COLUMN_NAME_OWNER_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.WishEntry.COLUMN_NAME_WISH_TITLE + TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.WishEntry.COLUMN_NAME_THEME+ TEXT_TYPE + COMMA_SEP +
                    DatabaseContracts.WishEntry.COLUMN_NAME_PRICE + INTEGER_TYPE + COMMA_SEP +
                    " FOREIGN KEY(" + DatabaseContracts.WishEntry.COLUMN_NAME_OWNER_ID + ") REFERENCES " +
                    DatabaseContracts.ProfileEntry.TABLE_NAME + "(" + DatabaseContracts.WishEntry._ID + ")" +
                    " );";
    private static final String SQL_CREATE_ENTRIES_COMMENT =
            "CREATE TABLE " + DatabaseContracts.CommentEntry.TABLE_NAME + " (" +
                    DatabaseContracts.CommentEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DatabaseContracts.CommentEntry.COLUMN_NAME_GROUP_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.CommentEntry.COLUMN_NAME_PROFILE_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.CommentEntry.COLUMN_NAME_TIMESTAMP + DATE_TYPE + COMMA_SEP +
                    DatabaseContracts.CommentEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY (" + DatabaseContracts.CommentEntry.COLUMN_NAME_GROUP_ID + ") REFERENCES " +
                    DatabaseContracts.GroupEntry.TABLE_NAME + " (" + DatabaseContracts.GroupEntry._ID + ")" + COMMA_SEP +
                    " FOREIGN KEY (" + DatabaseContracts.CommentEntry.COLUMN_NAME_PROFILE_ID + ") REFERENCES " +
                    DatabaseContracts.ProfileEntry.TABLE_NAME + " (" + DatabaseContracts.GroupEntry._ID + ")" +
                    " );";
    private static final String SQL_CREATE_ENTRIES_PARTICIPANT =
            "CREATE TABLE " + DatabaseContracts.ParticipantEntry.TABLE_NAME + " (" +
                    DatabaseContracts.ParticipantEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DatabaseContracts.ParticipantEntry.COLUMN_NAME_GROUP_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseContracts.ParticipantEntry.COLUMN_NAME_PROFILE_ID + INTEGER_TYPE + COMMA_SEP +
                    " FOREIGN KEY (" + DatabaseContracts.ParticipantEntry.COLUMN_NAME_GROUP_ID + ") REFERENCES " +
                    DatabaseContracts.GroupEntry.TABLE_NAME + " (" + DatabaseContracts.GroupEntry._ID + ")" + COMMA_SEP +
                    " FOREIGN KEY (" + DatabaseContracts.ParticipantEntry.COLUMN_NAME_PROFILE_ID + ") REFERENCES " +
                    DatabaseContracts.ProfileEntry.TABLE_NAME + " (" + DatabaseContracts.GroupEntry._ID + ")" +
                    " );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_PROFILE);
        db.execSQL(SQL_CREATE_ENTRIES_GROUP);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENT);
        db.execSQL(SQL_CREATE_ENTRIES_PARTICIPANT);
        db.execSQL(SQL_CREATE_ENTRIES_WISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is for testing purposes only, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(
                table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }

    public Cursor rawQuery(String sql, String[] selectionArgs){
        return getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        return getWritableDatabase().insert(table, nullColumnHack, values);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return getWritableDatabase().update(table, values, whereClause, whereArgs);
    }

    //SQLiteDatabase db = this.getReadableDatabase();
}
