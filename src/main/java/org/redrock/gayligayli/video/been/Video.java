package org.redrock.gayligayli.video.been;

import lombok.Data;
import net.sf.json.JSONObject;

@Data
public class Video {
    private static final String AUTHOR_NAME = "authorName";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String AV_ID="avId";
    private static final String DESCRIPTION = "description";
    private static final String TIME = "time";
    private static final String LENGTH = "length";
    private static final String VIEWS = "views";
    private static final String COIN = "coin";
    private static final String COLLECTION = "collection";
    private static final String PHOTO_URL = "photoUrl";
    private static final String VIDEO_URL = "videoUrl";
    private static final String SUCCESS = "success";

    private String name;
    private String authorName;
    private String type;
    private int avId;
    private String description;
    private String time;
    private String length;
    private String views;
    private int coin;
    private int collection;
    private String photoUrl;
    private String videoUrl;

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME,name);
        jsonObject.put(AUTHOR_NAME,authorName);
        jsonObject.put(TYPE,type);
        jsonObject.put(AV_ID,avId);
        jsonObject.put(DESCRIPTION,description);
        jsonObject.put(TIME,time);
        jsonObject.put(LENGTH,length);
        jsonObject.put(VIEWS,views);
        jsonObject.put(COIN,coin);
        jsonObject.put(COLLECTION,collection);
        jsonObject.put(PHOTO_URL,photoUrl);
        jsonObject.put(VIDEO_URL,videoUrl);
        return jsonObject.toString();
    }
}
