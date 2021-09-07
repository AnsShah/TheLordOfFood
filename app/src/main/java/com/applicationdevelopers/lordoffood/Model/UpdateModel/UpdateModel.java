package com.applicationdevelopers.lordoffood.Model.UpdateModel;

public class UpdateModel {
    public UpdateModel(String app_name, String app_version, String app_status) {
        this.app_name = app_name;
        this.app_version = app_version;
        this.app_status = app_status;
    }

    public UpdateModel() {
    }

    @Override
    public String toString() {
        return "UpdateModel{" +
                "app_name='" + app_name + '\'' +
                ", app_version='" + app_version + '\'' +
                ", app_status='" + app_status + '\'' +
                '}';
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    private String app_name,app_version,app_status;
}
