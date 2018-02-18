package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.Command;

public class RegisterCommand extends Command {

    public RegisterCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.Register();
    }

}
