package com.alexis.colval.giftrain.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by alexis on 2016-04-13.
 */
public class Group implements Parcelable{

    private String groupName, groupDescription, theme;
    private int id, scope, creatorId, drawInterval;
    Date nextDraw;
    double rating;

    private List<Profile> participants;
    private List<Comment> comments;

    public Group(int id, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, String theme) {
        this.id = id;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.scope = scope;
        this.creatorId = creatorId;
        this.drawInterval = drawInterval;
        this.rating = 0.0;
        this.theme = theme;

        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, drawInterval);
        this.nextDraw = c.getTime();

        participants = new ArrayList<>();
        comments = new ArrayList<>();
    }

    protected Group(Parcel in) {
        //Watch out, this is a shallow clone which doesnt contain Participants nor Comments.
        String[] data = new String[8];
        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.groupName = data[1];
        this.groupDescription = data[2];
        this.scope = Integer.parseInt(data[3]);
        this.creatorId = Integer.parseInt(data[4]);
        this.drawInterval = Integer.parseInt(data[5]);
        this.theme = data[6];
        this.nextDraw = new Date(Long.parseLong(data[7]));
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public Group(int id, String groupName, String groupDescription, int scope, int creatorId, int drawInterval, Date nextDraw, double rating, String theme) {
        this.id = id;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.scope = scope;
        this.creatorId = creatorId;
        this.drawInterval = drawInterval;
        this.rating = 0.0;
        this.theme = theme;
        this.nextDraw = nextDraw;
        this.rating = rating;

        participants = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.id),
                this.groupName,
                this.groupDescription,
                String.valueOf(this.scope),
                String.valueOf(this.creatorId),
                String.valueOf(this.drawInterval),
                this.theme,
                String.valueOf(this.nextDraw.getTime())
        });
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public List<Profile> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Profile> participants) {
        this.participants = participants;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDrawInterval() {
        return drawInterval;
    }

    public void setDrawInterval(int drawInterval) {
        this.drawInterval = drawInterval;
    }

    public Date getNextDraw() {
        return nextDraw;
    }

    public void setNextDraw(Date nextDraw) {
        this.nextDraw = nextDraw;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
