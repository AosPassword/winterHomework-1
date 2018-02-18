package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.Command;

public class RefreshTokenCommand extends Command {
    private String jwt;

    public RefreshTokenCommand(Receiver receiver, String jwt) {
        this.receiver = receiver;
        this.jwt = jwt;
    }

    @Override
    public void exectue() {
        receiver.RefreshToken(jwt);
    }

}
