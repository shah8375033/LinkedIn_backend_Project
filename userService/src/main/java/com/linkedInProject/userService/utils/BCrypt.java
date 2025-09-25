package com.linkedInProject.userService.utils;
import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static org.mindrot.jbcrypt.BCrypt.gensalt;
public class BCrypt {

    public static String hash(String s){
        return hashpw(s, gensalt());
    }
    public static boolean match(String passwordText,String hashedPassword){
        return checkpw(passwordText,hashedPassword);
    }
}
