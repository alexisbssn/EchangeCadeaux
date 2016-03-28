package com.alexis.colval.giftrain;

import android.provider.BaseColumns;

/**
 * Created by alexis on 2016-03-27.
 */
public class DatabaseContracts {
    private DatabaseContracts(){};
    public static abstract class ProfileEntry implements BaseColumns {
        public static final String TABLE_NAME = "Profile";
        public static final String COLUMN_NAME_GOOGLE_ID = "google_id";
        public static final String COLUMN_NAME_FNAME = "first_name";
        public static final String COLUMN_NAME_LNAME = "last_name";
        public static final String COLUMN_NAME_BDATE = "birth_date";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_REGION = "region";
        public static final String COLUMN_NAME_PHOTO_URL = "photo_url";
    }
}
