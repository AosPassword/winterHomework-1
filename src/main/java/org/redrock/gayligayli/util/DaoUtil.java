package org.redrock.gayligayli.util;

import org.redrock.gayligayli.video.been.Video;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtil {
    private static String AUTHOR_NAME = "authorName";
    private static String NAME = "name";
    private static String TYPE = "type";
    private static String DESCRIPTION = "description";
    private static String TIME = "time";
    private static String LENGTH = "length";
    private static String VIEWS = "views";
    private static String COIN = "coin";
    private static String COLLECTION = "collection";
    private static String PHOTO_URL = "photoUrl";
    private static String VIDEO_URL = "videoUrl";
    private static String SUCCESS = "success";

    public static Video initVedio(ResultSet resultSet) throws SQLException {
        Video video = new Video();
        video.setName(resultSet.getString(NAME));
        video.setAuthorName(resultSet.getString(AUTHOR_NAME));
        video.setType(resultSet.getString(TYPE));
        video.setDescription(resultSet.getString(DESCRIPTION));
        video.setTime(resultSet.getString(TIME));
        video.setLength(resultSet.getString(LENGTH));
        video.setViews(resultSet.getString(VIEWS));
        video.setCoin(resultSet.getInt(COIN));
        video.setCollection(resultSet.getInt(COLLECTION));
        video.setPhotoUrl(resultSet.getString(PHOTO_URL));
        video.setVideoUrl(resultSet.getString(VIDEO_URL));
        return video;
    }
}
