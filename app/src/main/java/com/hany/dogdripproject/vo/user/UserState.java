package com.hany.dogdripproject.vo.user;

import java.util.List;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class UserState {

    private List<String> dripIds = null;
    private List<String> follower = null;
    private List<String> following = null;
    public List<String> getDripIds() {
        return dripIds;
    }
    public void setDripIds(List<String> dripIds) {
        this.dripIds = dripIds;
    }
    public List<String> getFollower() {
        return follower;
    }
    public void setFollower(List<String> follower) {
        this.follower = follower;
    }
    public List<String> getFollowing() {
        return following;
    }
    public void setFollowing(List<String> following) {
        this.following = following;
    }
    @Override
    public String toString() {
        return "UserStatus [dripIds=" + dripIds + ", follower=" + follower + ", following=" + following + "]";
    }

}
