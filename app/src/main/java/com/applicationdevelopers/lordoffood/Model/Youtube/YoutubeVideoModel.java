package com.applicationdevelopers.lordoffood.Model.Youtube;

public class YoutubeVideoModel {
    private String video_url;
    private int id;
    private String name;
    private String is_featured;
    private String video;

    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "video_url='" + video_url + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", is_featured='" + is_featured + '\'' +
                ", video='" + video + '\'' +
                '}';
    }

    public YoutubeVideoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(String is_featured) {
        this.is_featured = is_featured;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public YoutubeVideoModel(String video_url, int id, String name, String is_featured, String video) {
        this.video_url = video_url;
        this.id = id;
        this.name = name;
        this.is_featured = is_featured;
        this.video = video;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public YoutubeVideoModel(String video_url) {
        this.video_url = video_url;
    }
}
