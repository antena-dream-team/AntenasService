package br.gov.sp.fatec.utils.commons;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class Md5 {
    public static String md5(String password) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            hexString.append("{MD5}");
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", 0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            // Unexpected - do nothing
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
            // Unexpected - do nothing
        }
        return password;
    }
}
