package org.website.thienan.ricewaterthienan.security.bcrypt;


import java.security.MessageDigest;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

@Component
public class HashCodeMD5 {

    public String hashMD5(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }
    public boolean compareMD5(String password1, String password2) throws Exception {
        String hashedPassword1 = hashMD5(password1);
        String hashedPassword2 = hashMD5(password2);
        return hashedPassword1.equals(hashedPassword2);
    }

}
