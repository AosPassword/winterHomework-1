package org.redrock.gayligayli.service.videoInfo.been;

import lombok.Data;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.VideoDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Video implements Comparable {


    private int id;
    private String name;
    private String type;
    private int childType;
    private int avId;
    private String description;
    private String time;
    private String length;
    private int views;
    private int coin;
    private String photoUrl;
    private String videoUrl;
    private Author author;

    public Video() {
    }

    public Video(Map<String,Object> map){
        this.setId((Integer) map.get(ID));
        this.setAvId((Integer) map.get(AV_ID_DATA));
        this.setName((String)map.get(NAME));
        this.setType((String) map.get(TYPE));
        this.setChildType((Integer) map.get(CHILD_TYPE_DATA));
        this.setDescription((String) map.get(DESCRIPTION));
        this.setTime((String) map.get(TIME));
        this.setLength((String) map.get(LENGTH));
        this.setViews((Integer) map.get(VIEWS));
        this.setCoin((Integer) map.get(COIN));
        this.setPhotoUrl((String) map.get(PHOTO_URL_DATA));
        this.setVideoUrl((String) map.get(VIDEO_URL_DATA));
        this.setAuthor(new Author(id));
    }

    public String toCaroselString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    public String toRankString() {
        //videoInfo.views+videoInfo.coin*70
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, author);
        jsonObject.put(TIME, time);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(PHOTO_URL, photoUrl);
        jsonObject.put(VIEWS, views);
        jsonObject.put(COLLECTION, VideoDao.getCollectionNum(id));
        jsonObject.put(COIN, coin);
        jsonObject.put(BARRAGE_NUM, VideoDao.getBarrageNum(id));
        jsonObject.put(SCORE, this.views + this.coin * 70);
        return jsonObject.toString();
    }

    public String toLiveString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, description);
        jsonObject.put(TYPE, type);
        jsonObject.put(VIEWS, views);
        jsonObject.put(PHOTO_URL, photoUrl);
        jsonObject.put(VIDEO_URL, videoUrl);
        return jsonObject.toString();
    }

    public String toSpreadStirng() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(LENGTH, length);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    public String toTopString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, author.toString());
        jsonObject.put(VIEWS, views);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    public String toSearchString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID,id);
        jsonObject.put(NAME,name);
        jsonObject.put(VIEWS,views);
        jsonObject.put(LENGTH,length);
        jsonObject.put(TIME,time);
        jsonObject.put(AUTHOR_ID,author.getId());
        jsonObject.put(AUTHOR_NAME,author.getName());
        jsonObject.put(PHOTO_URL,photoUrl);
        return  jsonObject.toString();
    }

    public String toBriefString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, author.toString());
        jsonObject.put(LENGTH, length);
        jsonObject.put(VIEWS, views);
        jsonObject.put(BARRAGE_NUM, VideoDao.getBarrageNum(id));
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(AUTHOR, author.toString());
        jsonObject.put(TYPE, type);
        jsonObject.put(CHILD_TYPE, childType);
        jsonObject.put(AV_ID, avId);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(TIME, time);
        jsonObject.put(LENGTH, length);
        jsonObject.put(VIEWS, views);
        jsonObject.put(COIN, coin);
        jsonObject.put(COLLECTION, VideoDao.getCollectionNum(id));
        jsonObject.put(BARRAGE_NUM, VideoDao.getBarrageNum(id));
        jsonObject.put(PHOTO_URL, photoUrl);
        jsonObject.put(VIDEO_URL, videoUrl);
        return jsonObject.toString();
    }

    @Override
    public int compareTo(Object o) {
            Video video = (Video) o;
            return this.id-video.getId();
    }
}
