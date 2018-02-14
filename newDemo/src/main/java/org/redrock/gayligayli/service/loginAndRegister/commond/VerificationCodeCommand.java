package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.video.Command;

public class VerificationCodeCommand extends Command {
    private Receiver receiver;

    public VerificationCodeCommand(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public String exectue() {
        return receiver.VerificationCode();
    }
}
