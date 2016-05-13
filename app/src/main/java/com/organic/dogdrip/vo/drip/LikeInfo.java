package com.organic.dogdrip.vo.drip;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kwonojin on 16. 3. 24..
 */
public class LikeInfo implements Parcelable {

    private int likeid = 0;
    private String dripid = null;
    private String userid = null;
    private long likedate = 0;

    public LikeInfo(){}

    public int getLikeid() {
        return likeid;
    }

    public void setLikeid(int likeid) {
        this.likeid = likeid;
    }

    public String getDripid() {
        return dripid;
    }

    public void setDripid(String dripid) {
        this.dripid = dripid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getLikedate() {
        return likedate;
    }

    public void setLikedate(long likedate) {
        this.likedate = likedate;
    }

    public LikeInfo(Parcel parcel){
        likeid = parcel.readInt();
        dripid = parcel.readString();
        userid = parcel.readString();
        likedate = parcel.readLong();

    }

    @Override
    public String toString() {
        return "LikeInfo{" +
                "likeid=" + likeid +
                ", dripid='" + dripid + '\'' +
                ", userid='" + userid + '\'' +
                ", likedate=" + likedate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(likeid);
        dest.writeString(dripid);
        dest.writeString(userid);
        dest.writeLong(likedate);
    }

    public static Parcelable.Creator<LikeInfo> CREATOR = new Creator<LikeInfo>() {
        @Override
        public LikeInfo createFromParcel(Parcel source) {
            return new LikeInfo(source);
        }

        @Override
        public LikeInfo[] newArray(int size) {
            return new LikeInfo[size];
        }
    };

}
