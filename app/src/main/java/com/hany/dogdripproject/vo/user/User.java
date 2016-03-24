package com.hany.dogdripproject.vo.user;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class User {
    private String email = null;
    private String nickname = null;
    private String password = null;
    private long createdate = 0;
    private long lastconn = 0;
    private int point = 0;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", createdate=" + createdate +
                ", lastconn=" + lastconn +
                ", point=" + point +
                '}';
    }
}
