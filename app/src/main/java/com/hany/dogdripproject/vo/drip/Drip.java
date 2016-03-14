package com.hany.dogdripproject.vo.drip;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class Drip {
    private int id = 0;
    private String drip = null;
    private String author = null;
    private long createdate = 0;
    private int heartcount = 0;

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
    public String toString() {
        return "Drip{" +
                "id=" + id +
                ", drip='" + drip + '\'' +
                ", author='" + author + '\'' +
                ", createdate=" + createdate +
                ", heartcount=" + heartcount +
                '}';
    }
}
