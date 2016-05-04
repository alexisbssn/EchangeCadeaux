package com.alexis.colval.giftrain.DAL;

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
    public static abstract class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "GroupTable";
        public static final String COLUMN_NAME_GROUP_NAME = "group_name";
        public static final String COLUMN_NAME_GROUP_DESCRIPTION = "group_description";
        public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
        public static final String COLUMN_NAME_SCOPE = "scope";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_INTERVAL = "interval";
        public static final String COLUMN_NAME_NEXT_DRAW = "next_draw";
        public static final String COLUMN_NAME_THEME = "theme";
    }
    public static abstract class WishEntry implements BaseColumns{
        public static final String TABLE_NAME = "Wish";
        public static final String COLUMN_NAME_WISH_TITLE = "wish_title";
        public static final String COLUMN_NAME_THEME = "theme";
        public static final String COLUMN_NAME_OWNER_ID = "owner_id";
        public static final String COLUMN_NAME_PRICE = "price";
    }
    public static abstract class ParticipantEntry implements BaseColumns {
        public static final String TABLE_NAME = "Participant";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_PROFILE_ID = "profile_id";
    }
    public static abstract class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Comment";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_PROFILE_ID = "profile_id";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
