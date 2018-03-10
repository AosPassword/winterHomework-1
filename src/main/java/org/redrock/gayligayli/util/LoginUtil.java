package org.redrock.gayligayli.util;

import org.redrock.gayligayli.Dao.UserDao;
import org.redrock.gayligayli.util.SecretUtil;

import java.util.Date;

public class LoginUtil {


    public static boolean isPass(String usernameType, String username, String password) {

        if (UserDao.getUserid(usernameType, username) != -1) { ;
            String passwordSecret = UserDao.getUserPass(username, usernameType);
            password = SecretUtil.encoderHs256(password);
            if (password.equals(passwordSecret)) {
                System.out.println("passwordYes");
                return true;
            }
        }
        return false;

    }

    public static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }


    //TODO:防sql注入 性能不够好
    public static boolean hasSomeCharacter(String username) {
        if(!username.contains("-")){
            if(!username.contains("\'")){
                if(!username.contains("\"")){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasUser(String usernameType, String username) {
        return UserDao.getUserid(usernameType, username) != -1;
    }



}
