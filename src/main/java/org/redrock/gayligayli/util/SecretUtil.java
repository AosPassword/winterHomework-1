package org.redrock.gayligayli.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SecretUtil {
    private static final String SECRET = "mashiroc";


    public static boolean isSecret(String data, String signature) {
        return encoderHs256(data).equals(signature);
    }

    public static String encoderHs256(String message) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
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

}
