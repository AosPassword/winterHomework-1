package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.video.Command;

public class RefreshTokenCommand extends Command {
    private Receiver receiver;

    public RefreshTokenCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String exectue() {
        return receiver.RefreshToken();
    }
}
