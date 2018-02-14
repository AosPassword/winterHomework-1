package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.video.Command;

public class RegisterCommand extends Command {
    private Receiver receiver;

    public RegisterCommand(Receiver receiver){
        this.receiver=receiver;
    }
    @Override
    public String exectue() {
        return receiver.Register();
    }
}
