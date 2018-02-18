package org.redrock.gayligayli.service.videoInfo.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class VideoListInfoCommand extends Command {

    public VideoListInfoCommand(Receiver receiver){
        this.receiver=receiver;
    }

    @Override
    public void exectue() {
        receiver.videoListInfo();
    }
}
