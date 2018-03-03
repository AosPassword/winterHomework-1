package org.redrock.gayligayli.service.videoUpload.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class uploadCodeCommand extends Command{

    public uploadCodeCommand(Receiver receiver){
        this.receiver=receiver;
    }

    @Override
    public void exectue() {
        receiver.uploadCode();
    }
}
