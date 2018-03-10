package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.Command;

public class RefreshTokenCommand extends Command {

    public RefreshTokenCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.RefreshToken();
    }

}
