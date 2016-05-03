package com.organic.dogdrip.vo.drip;

import android.os.Parcel;
import android.os.Parcelable;


public class Reply implements Parcelable{
	private int id = 0;
	private int dripid = 0;
	private String comment = null;
	private String author = null;
	private long createdate = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDripid() {
		return dripid;
	}
	public void setDripid(int dripid) {
		this.dripid = dripid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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

	public Reply(){

	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", dripid=" + dripid + ", comment=" + comment + ", author=" + author
				+ ", createdate=" + createdate + "]";
	}

	public Reply(Parcel p){
		id = p.readInt();
		dripid = p.readInt();
		comment = p.readString();
		author = p.readString();
		createdate = p.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(dripid);
		dest.writeString(comment);
		dest.writeString(author);
		dest.writeLong(createdate);
	}

	public static Creator<Reply> CREATOR = new Creator<Reply>() {

		@Override
		public Reply createFromParcel(Parcel source) {
			return new Reply(source);
		}

		@Override
		public Reply[] newArray(int size) {
			return new Reply[size];
		}
	};
}
