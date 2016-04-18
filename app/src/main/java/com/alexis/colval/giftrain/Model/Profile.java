package com.alexis.colval.giftrain.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexis on 2016-04-13.
 */
public class Profile implements Parcelable {

    private int id, googleId;
    private String firstName, lastName, birthDate, email, region, photoUrl;

    public Profile(int id, int googleId, String firstName, String lastName, String birthDate, String email, String region, String photoUrl) {
        this.id = id;
        this.googleId = googleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.region = region;
        this.photoUrl = photoUrl;
    }

    protected Profile(Parcel in) {
        String[] data= new String[8];
        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.googleId = Integer.parseInt(data[1]);
        this.firstName = data[2];
        this.lastName = data[3];
        this.birthDate = data[4];
        this.email = data[5];
        this.region = data[6];
        this.photoUrl = data[7];
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(id), String.valueOf(googleId), firstName, lastName, birthDate, email, region, photoUrl});
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getGoogleId() {
        return googleId;
    }

    public void setGoogleId(int googleId) {
        this.googleId = googleId;
    }
}
