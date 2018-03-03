package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.*;
import org.redrock.gayligayli.service.Command;

public class LoginCommand extends Command {

    public LoginCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.login();
    }

}
