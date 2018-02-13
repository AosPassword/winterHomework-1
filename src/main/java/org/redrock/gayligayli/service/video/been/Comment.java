package org.redrock.gayligayli.service.video.been;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Comment(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(ID);
        author=new Author(resultSet);
        content = resultSet.getString(CONTENT);
        num = resultSet.getInt(NUM);
        time = resultSet.getString(TIME);
        device = resultSet.getString(DEVICE);
        praise = resultSet.getInt(PRAISE);
        childComment = new ArrayList<>();
    }
}
