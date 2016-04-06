package com.hany.dogdripproject.vo.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class User implements Parcelable {
    private String email = null;
    private String nickname = null;
    private String password = null;
    private String imageurl = null;
    private long createdate = 0;
    private long lastconn = 0;
    private int point = 0;

    public User() {
    }

    public User(Parcel parcel) {
        point = parcel.readInt();
        createdate = parcel.readLong();
        lastconn = parcel.readLong();
        password = parcel.readString();
        imageurl = parcel.readString();
        nickname = parcel.readString();
        email = parcel.readString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
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
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", createdate=" + createdate +
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
        dest.writeLong(createdate);
        dest.writeLong(lastconn);
        dest.writeString(email);
        dest.writeString(nickname);
        dest.writeString(password);
        dest.writeString(imageurl);
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
