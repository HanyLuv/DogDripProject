package com.organic.dogdrip.vo.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class User implements Parcelable {
    private String userid = null;
    private String nickname = null;
    private String password = null;
    private String userimage = null;
    private long joindate = 0;
    private long lastconn = 0;
    private int point = 0;

    public User() {
    }

    public User(Parcel parcel) {
        point = parcel.readInt();
        joindate = parcel.readLong();
        lastconn = parcel.readLong();
        userid = parcel.readString();
        password = parcel.readString();
        userimage = parcel.readString();
        nickname = parcel.readString();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public long getJoindate() {
        return joindate;
    }

    public void setJoindate(long joindate) {
        this.joindate = joindate;
    }

    public long getLastconn() {
        return lastconn;
    }

    public void setLastconn(long lastconn) {
        this.lastconn = lastconn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", userimage='" + userimage + '\'' +
                ", joindate=" + joindate +
                ", lastconn=" + lastconn +
                ", point=" + point +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(point);
        dest.writeLong(joindate);
        dest.writeLong(lastconn);
        dest.writeString(userid);
        dest.writeString(password);
        dest.writeString(userimage);
        dest.writeString(nickname);
    }

    public static Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User();
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


}
