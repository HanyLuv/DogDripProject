package com.hany.dogdripproject.entry;

/**
 * Created by HanyLuv on 2016-03-14.
 */
public class User {

    private String email;
    private String nickname;
    private String password;
    private long createdate;
    private int lastconn;
    private int point;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public int getLastconn() {
        return lastconn;
    }

    public void setLastconn(int lastconn) {
        this.lastconn = lastconn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
