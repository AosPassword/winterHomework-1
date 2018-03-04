package org.redrock.gayligayli.service.videoInfo.been;

import lombok.Data;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Comment {
    private int id;
    private Author author;
    private String content;
    private int num;
    private String time;
    private String device;
    private int praise;
    private ArrayList<Comment> childComment;


    public Comment() {
    }

    public Comment(Map<String,Object> map) {
        id = (int) map.get(ID);
        author=new Author(id);
        content = (String) map.get(CONTENT);
        num = (int) map.get(NUM);
        time = (String) map.get(TIME);
        device = (String) map.get(DEVICE);
        praise = (int) map.get(PRAISE);
        childComment = new ArrayList<>();
    }

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID,id);
        jsonObject.put(AUTHOR,author.toString());
        jsonObject.put(CONTENT,content);
        jsonObject.put(NUM,num);
        jsonObject.put(TIME,time);
        jsonObject.put(DEVICE,device);
        jsonObject.put(PRAISE,praise);
        jsonObject.put(CHILD_COMMENT,childComment.toString());
        return jsonObject.toString();
    }
}
