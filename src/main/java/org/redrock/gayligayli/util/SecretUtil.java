package org.redrock.gayligayli.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static org.redrock.gayligayli.util.FinalStringUtil.UTF8;

public class SecretUtil {
    private static final String SECRET = "mashiroc";
    private static final String INSTANCE="HmacSHA256";

    public static boolean isSecret(String data, String signature) {
        String base = null;
        try {
            System.out.println("data " +data);
            base = new String(Base64.getEncoder().encode(data.getBytes("UTF-8")),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("si "+base);
        return encoderHs256(base).equals(signature);
    }

    public static String encoderHs256(String message) {
        String hash = null;
        try {
            Mac sha256Hmac = Mac.getInstance(INSTANCE);
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), INSTANCE);
            sha256Hmac.init(secretKey);
            byte[] bytes = sha256Hmac.doFinal(message.getBytes(UTF8));
            StringBuilder hs = new StringBuilder();
            String stmp;
            for (int n = 0; bytes!=null && n < bytes.length; n++) {
                stmp = Integer.toHexString(bytes[n] & 0XFF);
                if (stmp.length() == 1)
                    hs.append('0');
                hs.append(stmp);
            }
            hash = hs.toString().toLowerCase();
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static void main(String[] args) {
        String str = "测试aaa";
        String a = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(new String(Base64.getDecoder().decode(a)));
    }
}
