package org.redrock.gayligayli.service;

import com.aliyuncs.exceptions.ClientException;
import com.qiniu.util.Auth;
import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.Dao.VideoDao;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.service.loginAndRegister.util.LoginUtil;
import org.redrock.gayligayli.service.loginAndRegister.util.SendUtil;
import org.redrock.gayligayli.service.videoInfo.been.Barrage;
import org.redrock.gayligayli.service.videoInfo.been.Comment;
import org.redrock.gayligayli.service.videoInfo.been.Video;
import org.redrock.gayligayli.service.videoInfo.util.VideoInfoUtil;
import org.redrock.gayligayli.util.JsonUtil;
import org.redrock.gayligayli.util.SecretUtil;
import org.redrock.gayligayli.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

@Data
public class Receiver {
    private JSONObject requestJson;
    private int flag;
    private JSONObject responseJson;
    private Token token;


    public Receiver(String jsonStr) {
        this.requestJson = JSONObject.fromObject(jsonStr);
        responseJson = new JSONObject();
    }

    public Receiver(String jsonStr, Token token) {
        this.token = token;
        this.responseJson = JSONObject.fromObject(jsonStr);
        responseJson = new JSONObject();
    }

    public String getResponse() {
        return responseJson.toString();
    }

    private boolean isSignatureTrue() {
        String signature;
        if (requestJson != null) {
            if ((signature = requestJson.getString(SIGNATURE)) != null) {
                Iterator it = requestJson.keys();
                StringBuilder sb = new StringBuilder();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    if (SIGNATURE.equals(key)) {
                        sb.delete(sb.length() - 1, sb.length());
                        break;
                    }
                    sb.append(requestJson.getString(key)).append(SIGNATURE_SEPARATOR);
                }
                System.out.println("json  " + sb.toString());
                if (SecretUtil.isSecret(sb.toString(), signature)) {
                    System.out.println("secretYes");
                    if (TimeUtil.isNotOverTime(requestJson.getString(TIMESTAMP), REQUEST_OVERTIME_SECOND)) {
                        return true;
                    } else {
                        flag = -1;
                    }
                } else {
                    System.out.println("notSecret");
                    flag = 0;
                }
            }
        }
        return false;
    }

    private void errorString() {
        System.out.println(flag);
        if (flag == 0) {
            responseJson.put(RESULT, SIGNATURE_ERROR);
        } else {
            responseJson.put(RESULT, REQUEST_OVERTIME);
        }
    }


    public void login() {
        if (isSignatureTrue()) {
            String usernameType = requestJson.getString(USERNAME_TYPE);
            String username = requestJson.getString(USERNAME);
            String password = requestJson.getString(PASSWORD);

            if (usernameType != null && username != null && password != null) {
                if (!LoginUtil.hasSomeCharacter(username) && LoginUtil.isPass(usernameType, username, password)) {
                    Token token = new Token();
                    token.setSub(AUTHOR);
                    token.setTime(TOKEN_OVERTIME_SECOND);
                    token.setData(usernameType, username);

                    responseJson.put(RESULT, SUCCESS);
                    responseJson.put(JWT, token.getToken());
                } else {
                    responseJson.put(RESULT, PASSWORD_ERROR);
                }
            } else {
                responseJson.put(RESULT, REQUEST_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void RefreshToken(String jwt) {
        if (isSignatureTrue()) {
            Token originToken = new Token(jwt);
            if (originToken.isToken()) {
                if (originToken.isNotTokenOverTime()) {
                    Token newToken = new Token();
                    newToken.setSub(AUTHOR);
                    newToken.setData(NICKNAME, originToken.getNickname());
                    newToken.setTime(TOKEN_OVERTIME_SECOND);
                    responseJson.put(RESULT, SUCCESS);
                    responseJson.put(JWT, newToken.getToken());
                } else {
                    responseJson.put(RESULT, TOKEN_OVERTIME);
                }
            } else {
                responseJson.put(RESULT, TOKEN_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void Register() {
        if (isSignatureTrue()) {
            String usernameType = requestJson.getString(USERNAME_TYPE);
            String username = requestJson.getString(USERNAME);
            String nickname = requestJson.getString(NICKNAME);
            String password = requestJson.getString(PASSWORD);
            if (!LoginUtil.hasUser(usernameType, username)) {
                UserDao.insertNewUser(nickname, password, username, usernameType);
                Token token = new Token();
                token.setSub(AUTHOR);
                token.setTime(TOKEN_OVERTIME_SECOND);
                token.setData(usernameType, username);
                responseJson.put(RESULT, SUCCESS);
                responseJson.put(JWT, token.getToken());
            } else {
                responseJson.put(RESULT, USER_EXIST);
            }
        } else {
            errorString();
        }
    }

    public void VerificationCode() {
        if (isSignatureTrue()) {
            String telephone = requestJson.getString(TELEPHONE);
            String code = null;
            try {
                code = SendUtil.sendSms(telephone);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            if (code != null) {
                responseJson.put(RESULT, SUCCESS);
                responseJson.put(VERIFICATION, SecretUtil.encoderHs256(code));
            } else {
                responseJson.put(RESULT, SEND_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void homePage() {
        Object[] partition = responseJson.getJSONArray(CONTENT).toArray();

        //类型数量
        JSONObject typeNumJson = JSONObject.fromObject(VideoDao.getTypesNumMap());
        responseJson.put(TYPE_NUM, typeNumJson);

        //轮播那一条
        JSONArray carouselJson = new JSONArray();
        JSONArray topInfoJson = new JSONArray();
        Set<Video> carouselList = VideoDao.getCarouselVideoSet((long) Math.ceil(new Date().getTime() / 1000));
        {
            int i = 0;
            for (Video video : carouselList) {
                if (i >= 5) {
                    carouselJson.element(video.toCaroselString());
                } else {
                    topInfoJson.element(video.toTopString());
                }
                i++;
            }
            responseJson.put(CAROUSEL, carouselJson);
            responseJson.put(TOP_INFO, topInfoJson);
        }

        //推广那一条
        JSONArray spreadJson = new JSONArray();
        Set<Video> spreadList = VideoDao.getSpreadVideoSet();
        for (Video video : spreadList) {
            spreadJson.element(video.toSpreadStirng());
        }
        responseJson.put(SPREAD, spreadJson);

        //直播
        JSONArray liveVideoJson = new JSONArray();
        Set<Video> liveVideoList = VideoDao.getLiveVideoListSet();
        for (Video video : liveVideoList) {
            liveVideoJson.element(video.toLiveString());
        }
        responseJson.put(LIVE, liveVideoJson);

        //各个分区
        for (Object str : partition) {
            JSONObject partitionJson = new JSONObject();

            JSONArray partitionInfoJson = new JSONArray();
            Set<Video> partitionInfoSet = VideoDao.getPartitionInfoSet((String) str);
            for (Video video : partitionInfoSet) {
                partitionInfoJson.element(video.toBriefString());
            }
            partitionJson.element(INFO, partitionInfoJson);

            JSONArray partitionRankJson = new JSONArray();
            Set<Video> partitionRankSet = VideoDao.getPartitionRankSet((String) str, (long) Math.ceil(new Date().getTime() / 1000));
            {
                int i = 0;
                for (Video video : partitionRankSet) {
                    JSONObject tempJson;
                    tempJson = new JSONObject();
                    tempJson.element(RANK, ++i);
                    tempJson.element(DATA, video.toRankString());
                    partitionRankJson.element(tempJson.toString());
                }
            }
            partitionJson.element(RANK, partitionRankJson);

            responseJson.put(str, partitionJson);
        }
    }

    //TODO:我也不知道这个是干啥的
    public void videoListInfo() {

    }

    public void videoPage() {
        if (isSignatureTrue()) {
            int id = requestJson.getInt(ID);
            Video video;
            ArrayList<Barrage> barrageLIst;
            ArrayList<Comment> commentList;
            if (VideoInfoUtil.isVideoExist(id)) {
                VideoDao.addView(id);
                video = VideoDao.getVideoInfo(id);
                barrageLIst = VideoDao.getBarrageList(id);
                commentList = VideoDao.getCommentList(id);
                responseJson.put(RESULT, SUCCESS);
                responseJson.put(VIDEO, video.toString());
                responseJson.put(BARRAGE, JsonUtil.getArrayString(barrageLIst));
                responseJson.put(COMMENT, JsonUtil.getArrayString(commentList));
            } else {
                responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
            }
        } else {
            errorString();
        }
    }


    public void uploadCode() {
        String name = requestJson.getString(NAME);
        String nickname = requestJson.getString(NICKNAME);
        String type = requestJson.getString(TYPE);
        String description = requestJson.getString(DESCRIPTION);
        String time = requestJson.getString(TIMESTAMP);
        String length = requestJson.getString(LENGTH);
        int authorId;

        if (isSignatureTrue()) {
            if ((authorId = UserDao.getUserid(nickname, NICKNAME)) != -1) {
                int avId = VideoDao.addVideo(name, authorId, type, description, time, length);
                String upToken;
                Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
                upToken = auth.uploadToken(BUCKET);
                responseJson.put(RESULT, SUCCESS);
                responseJson.put(AV_ID, avId);
                responseJson.put(UP_TOKEN, upToken);
            } else {
                responseJson.put(RESULT, DO_NOT_FIND_USER);
            }
        } else {
            errorString();
        }
    }

    public void UploadSuccess() {
        //{avId:<avId>,timestamp,signature
        if (isSignatureTrue()) {
            int avId = requestJson.getInt(AV_ID);
            if (VideoDao.getVideoId(AV_ID, String.valueOf(avId)) != -1) {
                UserDao.uploadSuccess(avId);
                responseJson.put(RESULT, SUCCESS);
            } else {
                responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
            }
        } else {
            errorString();
        }
    }

    public void barrageSocket(String barrage) {
        responseJson = responseJson.element(BARRAGE, barrage);
    }

    public void authentication() {
        if (isSignatureTrue()) {
            String avId = requestJson.getString(AV_ID);
            int id;
            if ((id = VideoDao.getVideoId(AV_ID, avId)) != -1) {
                responseJson.put(ID, id);
            } else {
                responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
            }
            errorString();
        }
    }

    public void SendCoin() {
        if (isSignatureTrue()) {
            if (token.isToken()) {
                if (token.isNotTokenOverTime()) {
                    int sendCoin = requestJson.getInt(SEND_COIN);
                    int videoId = requestJson.getInt(VIDEO_ID);
                    String nickname = token.getNickname();
                    if (LoginUtil.hasUser(NICKNAME, nickname)) {
                        int userId = UserDao.getUserid(NICKNAME, nickname);
                        if (VideoDao.getVideoId(ID, String.valueOf(videoId)) != -1) {
                            if (UserDao.getCoin(userId) <= sendCoin) {
                                UserDao.reduceCoin(sendCoin, userId);
                                VideoDao.addCoin(sendCoin, videoId);
                                responseJson.put(RESULT, SUCCESS);
                            } else {
                                responseJson.put(RESULT, NOT_ENOUGH_COIN);
                            }
                        } else {
                            responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
                        }
                    } else {
                        responseJson.put(RESULT, DO_NOT_FIND_USER);
                    }
                } else {
                    responseJson.put(RESULT, TOKEN_OVERTIME);
                }
            } else {
                responseJson.put(RESULT, TOKEN_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void addCollection() {
        if (isSignatureTrue()) {
            if (token.isToken()) {
                if (token.isNotTokenOverTime()) {
                    int videoId = requestJson.getInt(VIDEO_ID);
                    String nickname = token.getNickname();
                    if (LoginUtil.hasUser(NICKNAME, nickname)) {
                        int userId = UserDao.getUserid(NICKNAME, nickname);
                        if (VideoDao.getVideoId(ID, String.valueOf(videoId)) != -1) {
                            UserDao.addCollection(userId, videoId);
                            responseJson.put(RESULT, SUCCESS);
                        } else {
                            responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
                        }
                    } else {
                        responseJson.put(RESULT, DO_NOT_FIND_USER);
                    }
                } else {
                    responseJson.put(RESULT, TOKEN_OVERTIME);
                }
            } else {
                responseJson.put(RESULT, TOKEN_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void sendBarrage() {
        if (isSignatureTrue()) {
            if (token.isToken()) {
                if (token.isNotTokenOverTime()) {
                    int videoId = requestJson.getInt(VIDEO_ID);
                    String content = requestJson.getString(CONTENT);
                    String appearTime = requestJson.getString(APPEAR_TIME);
                    String sendTime = requestJson.getString(TIMESTAMP);
                    String color = requestJson.getString(COLOR);
                    int fontsize = requestJson.getInt(FONTSIZE);
                    int position = requestJson.getInt(POSITION);
                    String nickname = token.getNickname();
                    if (LoginUtil.hasUser(NICKNAME, nickname)) {
                        int userId = UserDao.getUserid(NICKNAME, nickname);
                        if (VideoDao.getVideoId(ID, String.valueOf(videoId)) != -1) {
                            VideoDao.addBarrage(videoId, userId, content, appearTime, sendTime, color, fontsize, position);
                            responseJson.put(RESULT, SUCCESS);
                        } else {
                            responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
                        }
                    } else {
                        responseJson.put(RESULT, DO_NOT_FIND_USER);
                    }
                } else {
                    responseJson.put(RESULT, TOKEN_OVERTIME);
                }
            } else {
                responseJson.put(RESULT, TOKEN_ERROR);
            }
        } else {
            errorString();
        }
    }

    public void sendComment() {
        if (isSignatureTrue()) {
            if (token.isToken()) {
                if (token.isNotTokenOverTime()) {
                    int videoId = requestJson.getInt(VIDEO_ID);
                    int pid = requestJson.getInt(COMMENT_PID);
                    String content = requestJson.getString(CONTENT);
                    String device = requestJson.getString(DEVICE);
                    String time = requestJson.getString(TIMESTAMP);
                    String nickname = token.getNickname();
                    if (LoginUtil.hasUser(NICKNAME, nickname)) {
                        int userId = UserDao.getUserid(NICKNAME, nickname);
                        if (VideoDao.getVideoId(ID, String.valueOf(videoId)) != -1) {
                            if (VideoDao.pidExist(pid)) {
                                VideoDao.addComment(videoId, pid, userId, content, time, device);
                                responseJson.put(RESULT, SUCCESS);
                            } else {
                                responseJson.put(RESULT, DO_NOT_FIND_COMMENT);
                            }
                        } else {
                            responseJson.put(RESULT, DO_NOT_FIND_VIDEO);
                        }
                    } else {
                        responseJson.put(RESULT, DO_NOT_FIND_USER);
                    }
                } else {
                    responseJson.put(RESULT, TOKEN_OVERTIME);
                }
            } else {
                responseJson.put(RESULT, TOKEN_ERROR);
            }
        } else {
            errorString();
        }
    }
}