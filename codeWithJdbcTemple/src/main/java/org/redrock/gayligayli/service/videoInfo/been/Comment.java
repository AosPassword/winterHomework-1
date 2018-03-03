package org.redrock.gayligayli.service.videoInfo.been;

import lombok.Data;

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
}
