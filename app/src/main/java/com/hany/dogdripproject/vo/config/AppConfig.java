package com.hany.dogdripproject.vo.config;

/**
 * Created by kwonojin on 16. 3. 14..
 */
public class AppConfig {
    private String appId = null;
    private String appSercureKey = null;
    private String appLatestVersion = null;
    private String appVersionName = null;
    private String appMarketUrl = null;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSercureKey() {
        return appSercureKey;
    }

    public void setAppSercureKey(String appSercureKey) {
        this.appSercureKey = appSercureKey;
    }

    public String getAppLatestVersion() {
        return appLatestVersion;
    }

    public void setAppLatestVersion(String appLatestVersion) {
        this.appLatestVersion = appLatestVersion;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppMarketUrl() {
        return appMarketUrl;
    }

    public void setAppMarketUrl(String appMarketUrl) {
        this.appMarketUrl = appMarketUrl;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "appId='" + appId + '\'' +
                ", appSercureKey='" + appSercureKey + '\'' +
                ", appLatestVersion='" + appLatestVersion + '\'' +
                ", appVersionName='" + appVersionName + '\'' +
                ", appMarketUrl='" + appMarketUrl + '\'' +
                '}';
    }
}
