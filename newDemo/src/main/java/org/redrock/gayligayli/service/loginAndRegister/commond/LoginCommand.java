package org.redrock.gayligayli.service.loginAndRegister.commond;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.*;
import org.redrock.gayligayli.service.video.Command;

import java.sql.ResultSet;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class LoginCommand extends Command {
    private Receiver receiver;

    public LoginCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String exectue() {
        return receiver.login();
    }
}
