package org.redrock.gayligayli.service.videoInfo.been;

import lombok.Data;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;

import java.util.Map;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Author {
    private int id;
    private String name;
    private String description;
    private int level;
    private String photoUrl;

    public Author() {
    }

    public Author(int id)  {
        Map<String, Object> map = UserDao.getAuthor(id);
        this.id = (int) map.get(ID);
        this.name = (String) map.get(NICKNAME);
        this.description = (String) map.get(DESCRIPTION);
        this.level = (int) map.get(LEVEL);
        this.photoUrl = (String) map.get(PHOTO_URL_DATA);
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
