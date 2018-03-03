package org.redrock.gayligayli.service.videoUpload.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class UploadSuccessCommand extends Command {

    public UploadSuccessCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.UploadSuccess();
    }
}
