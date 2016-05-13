package com.organic.dogdrip.vo.drip;

import android.os.Parcel;
import android.os.Parcelable;

import com.organic.dogdrip.vo.user.User;


public class Reply implements Parcelable{

	private int replyid = 0;
	private int dripid = 0;
	private String comment = null;
	private String userid = null;
	private long replydate = 0;

	private Drip drip = null;

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getReplydate() {
		return replydate;
	}

	public void setReplydate(long replydate) {
		this.replydate = replydate;
	}

	public Drip getDrip() {
		return drip;
	}

	public void setDrip(Drip drip) {
		this.drip = drip;
	}

	public Reply(){

	}

	@Override
	public String toString() {
		return "Reply{" +
				"replyid=" + replyid +
				", dripid=" + dripid +
				", comment='" + comment + '\'' +
				", userid='" + userid + '\'' +
				", replydate=" + replydate +
				", drip=" + drip +
				'}';
	}

	public Reply(Parcel p){
		replyid = p.readInt();
		dripid = p.readInt();
		comment = p.readString();
		userid = p.readString();
		replydate = p.readLong();
		drip = p.readParcelable(Drip.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(replyid);
		dest.writeInt(dripid);
		dest.writeString(comment);
		dest.writeString(userid);
		dest.writeLong(replydate);
		dest.writeParcelable(drip, flags);
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
