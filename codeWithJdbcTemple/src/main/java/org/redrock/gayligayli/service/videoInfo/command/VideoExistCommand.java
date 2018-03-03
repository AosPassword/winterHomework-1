package org.redrock.gayligayli.service.videoInfo.command;

import org.redrock.gayligayli.controller.websocket.BarrageWebSocket;
import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class VideoExistCommand extends Command {
    private Receiver receiver;

    public VideoExistCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.authentication();
    }
}
