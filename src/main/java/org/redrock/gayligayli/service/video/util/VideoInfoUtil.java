package org.redrock.gayligayli.service.video.util;

import org.redrock.gayligayli.Dao.VideoDao;

import java.util.ArrayList;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoInfoUtil {
    //http://p3qhkqnrm.bkt.clouddn.com/image/png/initAvatar.png
    private static String url = "http://p3qhkqnrm.bkt.clouddn.com/";
    private static String photoBucket ="image/video/photo/";
    private static String videoBucket = "video/";
    public static boolean isVideoExist(int id){
        return VideoDao.getVideoInfo(id) != null;
    }

    public static int getAvid(){
        int avId=(int) Math.ceil(Math.random()*100000);
        while(VideoDao.getVideoId(AV_ID,String.valueOf(avId))!=-1){
            avId=(int) Math.ceil(Math.random()*100000);
        }
        return avId;
    }

    public static String getPhotoUrl(int avId,String ends) {
        return url+photoBucket+avId+ends;
    }

    public static String getVideoUrl(int avId,String ends) {
        return url+videoBucket+avId+ends;
    }
}
