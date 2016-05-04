package com.alexis.colval.giftrain.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexis on 2016-05-02.
 */
public class Wish implements Parcelable {
    private String wishTitle, wishTheme;
    private int id, wishPrice, ownerId;

    public Wish(int id, String wishTitle, String wishTheme, int wishPrice, int ownerId) {
        this.wishTitle = wishTitle;
        this.wishTheme = wishTheme;
        this.id = id;
        this.wishPrice = wishPrice;
        this.ownerId = ownerId;
    }

    protected Wish(Parcel in) {
        wishTitle = in.readString();
        wishTheme = in.readString();
        id = in.readInt();
        wishPrice = in.readInt();
        ownerId = in.readInt();
    }

    public static final Creator<Wish> CREATOR = new Creator<Wish>() {
        @Override
        public Wish createFromParcel(Parcel in) {
            return new Wish(in);
        }

        @Override
        public Wish[] newArray(int size) {
            return new Wish[size];
        }
    };

    public String getWishTitle() {
        return wishTitle;
    }

    public void setWishTitle(String wishTitle) {
        this.wishTitle = wishTitle;
    }

    public String getWishTheme() {
        return wishTheme;
    }

    public void setWishTheme(String wishTheme) {
        this.wishTheme = wishTheme;
    }

    public int getId() {
        return id;
    }

    public int getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(int wishPrice) {
        this.wishPrice = wishPrice;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wishTitle);
        dest.writeString(wishTheme);
        dest.writeInt(id);
        dest.writeInt(wishPrice);
        dest.writeInt(ownerId);
    }
}
