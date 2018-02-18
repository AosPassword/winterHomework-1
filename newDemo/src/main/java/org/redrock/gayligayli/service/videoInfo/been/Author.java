package org.redrock.gayligayli.service.videoInfo.been;

import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class Author {
    private int id;
    private String name;
    private String description;
    private int level;
    private String photoUrl;

    public Author() {
    }

    public Author(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(ID);
        name = resultSet.getString(NAME);
        description = resultSet.getString(DESCRIPTION);
        level = resultSet.getInt(LEVEL);
        photoUrl = resultSet.getString(PHOTO_URL_DATA);
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(LEVEL, level);
        jsonObject.put(PHOTO_URL, photoUrl);
        return jsonObject.toString();
    }
}
