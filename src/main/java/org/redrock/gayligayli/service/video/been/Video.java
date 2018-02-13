package org.redrock.gayligayli.service.video.been;

import lombok.Data;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Video {


    private int id;
    private String name;
    private String type;
    private String childType;
    private int avId;
    private String description;
    private String time;
    private String length;
    private String views;
    private int coin;
    private String photoUrl;
    private String videoUrl;
    private Author author;

    public Video() {
    }

    public Video(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt(ID));
        this.setName(resultSet.getString(NAME));
        this.setType(resultSet.getString(TYPE));
        this.setChildType(resultSet.getString(CHILD_TYPE_DATA));
        this.setDescription(resultSet.getString(DESCRIPTION));
        this.setTime(resultSet.getString(TIME));
        this.setLength(resultSet.getString(LENGTH));
        this.setViews(resultSet.getString(VIEWS));
        this.setCoin(resultSet.getInt(COIN));
        this.setPhotoUrl(resultSet.getString(PHOTO_URL_DATA));
        this.setVideoUrl(resultSet.getString(VIDEO_URL_DATA));
        this.setAuthor(new Author(resultSet));
    }

    public String toCaroselString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    public String toRankString(){
        //video.views+video.coin*70
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID,id);
        jsonObject.put(NAME,name);
      jsonObject.put(AUTHOR,author);
        jsonObject.put(TIME,time);
        jsonObject.put(DESCRIPTION,description);
        jsonObject.put(PHOTO_URL,photoUrl);
        jsonObject.put(VIEWS,views);
        jsonObject.put(COLLECTION,VideoDao.getCollectionNum(id));
        jsonObject.put(COIN,coin);
        jsonObject.put(BARRAGE_NUM,VideoDao.getBarrageNum(id));
        jsonObject.put(SCORE,this.views+this.coin*70);
        return jsonObject.toString();
    }

    public String toLiveString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME,name);
        jsonObject.put(AUTHOR,childType);
        jsonObject.put(TYPE,type);
        jsonObject.put(VIEWS,views);
        jsonObject.put(PHOTO_URL,photoUrl);
        jsonObject.put(VIDEO_URL,videoUrl);
        return jsonObject.toString();
    }

    public String toSpreadStirng(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID,id);
        jsonObject.put(NAME,name);
        jsonObject.put(LENGTH,length);
        jsonObject.put(PHOTO_URL,photoUrl);
        return jsonObject.toString();
    }

    public String toTopString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR,author);
        jsonObject.put(VIEWS, views);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    public String toBriefString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR,author);
        jsonObject.put(LENGTH, length);
        jsonObject.put(VIEWS, views);
        jsonObject.put(BARRAGE_NUM, VideoDao.getBarrageNum(id));
        jsonObject.put(PHOTO_URL,photoUrl);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, author);
        jsonObject.put(TYPE, type);
        jsonObject.put(CHILD_TYPE,childType);
        jsonObject.put(AV_ID, avId);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(TIME, time);
        jsonObject.put(LENGTH, length);
        jsonObject.put(VIEWS, views);
        jsonObject.put(COIN, coin);
        jsonObject.put(COLLECTION, VideoDao.getCollectionNum(id));
        jsonObject.put(BARRAGE_NUM,VideoDao.getBarrageNum(id));
        jsonObject.put(PHOTO_URL, photoUrl);
        jsonObject.put(VIDEO_URL, videoUrl);
        return jsonObject.toString();
    }
}
