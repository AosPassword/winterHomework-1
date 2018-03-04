package org.redrock.gayligayli.service.videoInfo.util;

import org.redrock.gayligayli.Dao.VideoDao;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class VideoInfoUtil {
    //http://p3qhkqnrm.bkt.clouddn.com/image/png/initAvatar.png
    private static String url = "http://p3qhkqnrm.bkt.clouddn.com/";
    private static String photoBucket = "image/videoInfo/photo/";
    private static String videoBucket = "videoInfo/";

    private static final String[] partition =
            {CARTOON, ANIME, CREATED_BY_NATIVE, MUSIC, DANCE,
                    GAME, SCIENCE, LIFE, AUTOTUNE_REMIX, FASHION,
                    ADVERTISEMENT, ENTERTAINMENT, MOVIES, SCREENING_HALL};

    private static final String[] partitionChinese =
            {"动画", "番剧", "国创", "音乐", "舞蹈",
                    "游戏", "科技", "生活", "鬼畜", "时尚",
                    "广告", "娱乐", "影视", "放映厅"};

    public static boolean isVideoExist(int id) {
        return VideoDao.getVideoInfo(id) != null;
    }

    public static boolean isVideoExist(String avId) {
        return VideoDao.getVideoId(AV_ID_DATA, avId) != -1;
    }

    public static int getAvid() {
        int avId = (int) Math.ceil(Math.random() * 100000000);
        while (VideoDao.getVideoId(AV_ID_DATA, String.valueOf(avId)) != -1) {
            avId = (int) Math.ceil(Math.random() * 100000000);
        }
        return avId;
    }

    public static String getPhotoUrl(int avId, String ends) {
        return url + photoBucket + avId + ends;
    }

    public static String getVideoUrl(int avId, String ends) {
        return url + videoBucket + avId + ends;
    }

    public static int isType(String data) {
        for (int i=0;i<partitionChinese.length;i++){
            if(data.equals(partitionChinese[i])){
                return i;
            }
        }
        return -1;
    }

    ;

    public static String getType(int flag) {
        return partition[flag];
    }

    public static String getPage(int page) {
        StringBuilder sb = new StringBuilder();
        sb.append((page-1)*10).append(",").append(page*10);
        return sb.toString();
    }
}
