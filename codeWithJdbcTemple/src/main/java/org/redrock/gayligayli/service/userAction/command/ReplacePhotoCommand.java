package org.redrock.gayligayli.service.userAction.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class ReplacePhotoCommand extends Command {

    public ReplacePhotoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.replacePhoto();
    }
}
