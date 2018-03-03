package org.redrock.gayligayli.service.videoInfo.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class VideoPageCommand extends Command {

    public VideoPageCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.videoPage();
    }
}
