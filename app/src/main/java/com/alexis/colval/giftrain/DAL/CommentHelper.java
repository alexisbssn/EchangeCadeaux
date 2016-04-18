package com.alexis.colval.giftrain.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexis.colval.giftrain.Model.Comment;

/**
 * Created by alexis on 2016-04-12.
 */
public class CommentHelper {
    private static CommentHelper instance;

    public static CommentHelper getInstance() {
        if(instance == null){
            instance = new CommentHelper();
        }
        return instance;
    }
}
