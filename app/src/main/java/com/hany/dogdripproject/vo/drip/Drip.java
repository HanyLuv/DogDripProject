package com.hany.dogdripproject.vo.drip;

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

    public Drip(){}

    public Drip(Parcel parcel){
        heartcount = parcel.readInt();
        id = parcel.readInt();
        createdate = parcel.readLong();
        author = parcel.readString();
        drip = parcel.readString();
    }

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
        dest.writeInt(heartcount);
        dest.writeInt(id);
        dest.writeLong(createdate);
        dest.writeString(author);
        dest.writeString(drip);
    }

    public static Creator<Drip> CREATOR = new Creator<Drip>() {

        @Override
        public Drip createFromParcel(Parcel source) {
            return new Drip(source);
        }

        @Override
        public Drip[] newArray(int size) {
            return new Drip[size];
        }
    };
}
