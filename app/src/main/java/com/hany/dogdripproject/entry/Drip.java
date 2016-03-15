package com.hany.dogdripproject.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HanyLuv on 2016-03-14.
 */
public class Drip implements Parcelable {

    private int id;
    private String drip;
    private String author;
    private long createdate;
    private int heartcount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrip() {
        return drip;
    }

    public void setDrip(String drip) {
        this.drip = drip;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public int getHeartcount() {
        return heartcount;
    }

    public void setHeartcount(int heartcount) {
        this.heartcount = heartcount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getHeartcount());
        dest.writeInt(getId());
        dest.writeLong(getCreatedate());
        dest.writeString(getAuthor());
        dest.writeString(getDrip());
    }
}
