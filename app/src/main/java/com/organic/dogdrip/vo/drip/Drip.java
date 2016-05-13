package com.organic.dogdrip.vo.drip;

import android.os.Parcel;
import android.os.Parcelable;

import com.organic.dogdrip.vo.user.User;


/**
 * Created by HanyLuv on 2016-03-14.
 */
public class Drip implements Parcelable {

    private int dripid;
    private String drip;
    private String dripimage;
    private String userid;
    private long createdate;
    private int heartcount;
    private User user = null;

    public Drip(){}

    public Drip(Parcel parcel){
        heartcount = parcel.readInt();
        dripid = parcel.readInt();
        createdate = parcel.readLong();
        userid = parcel.readString();
        drip = parcel.readString();
        dripimage= parcel.readString();
        user = parcel.readParcelable(User.class.getClassLoader());
    }

    public int getDripid() {
        return dripid;
    }

    public void setDripid(int dripid) {
        this.dripid = dripid;
    }

    public String getDrip() {
        return drip;
    }

    public void setDrip(String drip) {
        this.drip = drip;
    }

    public String getDripimage() {
        return dripimage;
    }

    public void setDripimage(String dripimage) {
        this.dripimage = dripimage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Drip{" +
                "dripid=" + dripid +
                ", drip='" + drip + '\'' +
                ", dripimage='" + dripimage + '\'' +
                ", userid='" + userid + '\'' +
                ", createdate=" + createdate +
                ", heartcount=" + heartcount +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(heartcount);
        dest.writeInt(dripid);
        dest.writeLong(createdate);
        dest.writeString(userid);
        dest.writeString(drip);
        dest.writeString(dripimage);
        dest.writeParcelable(user, flags);
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
