package org.redrock.gayligayli.service.videoInfo.command;

import org.redrock.gayligayli.service.Command;
import org.redrock.gayligayli.service.Receiver;

public class HomePageCommand extends Command {

    public HomePageCommand(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.homePage();
    }

}
