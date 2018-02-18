package org.redrock.gayligayli.service.loginAndRegister.util;

import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.util.SecretUtil;

import java.util.Date;

public class LoginUtil {


    public static boolean isPass(String usernameType, String username, String password) {

        if (UserDao.getUserid(username, usernameType) != -1) {
            String passwordSecret = UserDao.getUserPass(username, usernameType);
            password = SecretUtil.encoderHs256(password);
            if (password.equals(passwordSecret)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }


    //TODO:防sql注入
    public static boolean hasSomeCharacter(String username) {
        return false;
    }

    public static boolean hasUser(String usernameType, String username) {
        return UserDao.getUserid(username, usernameType) != -1;
    }


}
