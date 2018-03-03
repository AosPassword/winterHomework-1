package org.redrock.gayligayli.service.videoInfo.util;

import org.redrock.gayligayli.Dao.VideoDao;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoInfoUtil {
    //http://p3qhkqnrm.bkt.clouddn.com/image/png/initAvatar.png
    private static String url = "http://p3qhkqnrm.bkt.clouddn.com/";
    private static String photoBucket ="image/videoInfo/photo/";
    private static String videoBucket = "videoInfo/";
    public static boolean isVideoExist(int id){
        return VideoDao.getVideoInfo(id) != null;
    }

    public static boolean isVideoExist(String avId){
        return VideoDao.getVideoId(AV_ID_DATA,avId)!=-1;
    }

    public static int getAvid(){
        int avId=(int) Math.ceil(Math.random()*100000000);
        while(VideoDao.getVideoId(AV_ID_DATA,String.valueOf(avId))!=-1){
            avId=(int) Math.ceil(Math.random()*100000000);
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
