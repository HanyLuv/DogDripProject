package com.hany.dogdripproject.vo.drip;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kwonojin on 16. 3. 24..
 */
public class LikeInfo implements Parcelable {

    private int id = 0;
    private String user = null;
    private String author = null;
    private long createdate = 0;

    public LikeInfo(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
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

    public LikeInfo(Parcel parcel){
        id = parcel.readInt();
        author = parcel.readString();
        user = parcel.readString();
        createdate = parcel.readLong();

    }
    @Override
    public String toString() {
        return "LikeDrips [id=" + id + ", user=" + user + ", author=" + author + ", createdate=" + createdate + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(user);
        dest.writeLong(createdate);
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
