package org.redrock.gayligayli.service.loginAndRegister.commond;

import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.service.Command;

public class VerificationCodeCommand extends Command {

    public VerificationCodeCommand(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void exectue() {
        receiver.VerificationCode();
    }

}
