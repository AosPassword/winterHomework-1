package org.redrock.gayligayli.service.videoInfo.been;

import lombok.Data;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Barrage {


    private String content;
    private String sendTime;
    private int authorId;
    private String color;
    private int position;
    private String appearTime;
    private int fontSize;

    public Barrage(){}

    public Barrage(ResultSet resultSet) throws SQLException {
        content=resultSet.getString(CONTENT);
        sendTime =resultSet.getString(SEND_TIME_DATA);
        fontSize=resultSet.getInt(FONTSIZE);
        authorId=resultSet.getInt(AUTHOR_ID_DATA);
        color=resultSet.getString(COLOR);
        position=resultSet.getInt(POSITION);
        appearTime =resultSet.getString(APPEAR_TIME_DATA);
    }

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CONTENT,content);
        jsonObject.put(SEND_TIME, sendTime);
        jsonObject.put(AUTHOR_ID,authorId);
        jsonObject.put(COLOR,color);
        jsonObject.put(POSITION,position);
        jsonObject.put(APPEAR_TIME, appearTime);
        return jsonObject.toString();
    }
}