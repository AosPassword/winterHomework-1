package org.redrock.gayligayli.controller.websocket;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.videoInfo.command.BarrageSocketCommand;
import org.redrock.gayligayli.service.videoInfo.command.VideoExistCommand;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.ID;
import static org.redrock.gayligayli.util.FinalStringUtil.RESULT;

//@ServerEndpoint(value = "/barrageWebsocket/{jsonStr}")
public class BarrageWebSocket {

    @OnOpen
    public void onOpen(@PathParam("jsonStr") String jsonStr, Session session) throws IOException, InterruptedException {
        Receiver receiver = new Receiver(jsonStr);
        Command userExistCommand = new VideoExistCommand(receiver);
        userExistCommand.exectue();
        JSONObject jsonObject = JSONObject.fromObject(userExistCommand.getResponseJson());
        if (jsonObject.getString(RESULT) != null) {
            session.getBasicRemote().sendText(jsonObject.toString());
            session.close();
        } else {
            int id = jsonObject.getInt(ID);
            BarrageSocketCommand websockCommand = new BarrageSocketCommand(receiver, id);
            while (websockCommand.next()) {
                session.getBasicRemote().sendText(websockCommand.getResponseJson());
            }
            session.close();
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("连接关闭");
    }

    @OnMessage
    public void onMessage(Session session) throws IOException {
        session.getBasicRemote().sendText("test");
    }

    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        session.close();
        error.printStackTrace();
    }
}
