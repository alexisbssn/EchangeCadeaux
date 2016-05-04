package com.alexis.colval.giftrain.DAL;

import android.content.Context;
import android.database.Cursor;

import com.alexis.colval.giftrain.Model.Group;
import com.alexis.colval.giftrain.Model.Profile;
import com.alexis.colval.giftrain.Model.Wish;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexis on 2016-04-13.
 */
public class Repository {

    private static Repository instance;
    private GroupHelper groupHelper;

    private Repository(){
    }

    public static Repository getInstance() {
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public Group getGroupById(int id){
        return null;
    }

    public Profile getProfileById(int id){
        return null;
    }


    public void addGroup(Context context, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, String theme) {
        GroupHelper.getInstance().addGroup(context, groupName, groupDescription, scope, creatorId, drawInterval, theme);
    }

    public void updateGroup(Context context, int id, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, Date nextDraw, String theme) {
        GroupHelper.getInstance().updateGroup(context, id, groupName, groupDescription, scope, creatorId, drawInterval, nextDraw, theme);
    }

    public int getProfileByGoogleId(Context context, String googleId) {
        return ProfileHelper.getInstance().getProfileIdByGoogleId(context, googleId);
    }

    public List<Group> getGroupsByProfileId(Context context, int profileId) {
        return GroupHelper.getInstance().getGroupsByProfileId(context, profileId);
    }
    public List<Group> findGroupsByName(Context context, String search, String theme) {
        return GroupHelper.getInstance().findGroupsByName(context, search, theme);
    }

    public void addParticipant(Context applicationContext, int profileId, int groupId) {
        ParticipantHelper.getInstance().addParticipant(applicationContext, profileId, groupId);
    }

    public List<Profile> getParticipantsByGroupId(Context appContext, int groupId) {
        Cursor c = ParticipantHelper.getInstance().getParticipantsByGroupId(appContext, groupId);

        List<Profile> participants = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(DatabaseContracts.ParticipantEntry.COLUMN_NAME_PROFILE_ID));
            participants.add(ProfileHelper.getInstance().getProfileById(appContext, id));
        }
        return  participants;
    }

    public void addWish(Context context, String title, String theme, int price, int ownerId) {
        WishHelper.getInstance().addWish(context, title, theme, price, ownerId);
    }

    public void updateWish(Context context, int id, String title, String theme, int price, int ownerId) {
        WishHelper.getInstance().updateWish(context, id, title, theme, price, ownerId);
    }

    public List<Wish> getWishesByProfileId(Context context, int profileId) {
        return WishHelper.getInstance().getWishesByProfileId(context, profileId);
    }
}
