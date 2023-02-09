package co.bankly.micusers.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UtilString {
    public static String passwordEncoder(String pwd){
        return new BCryptPasswordEncoder().encode(pwd);
    }
    public static boolean isEmpty(String str){
        return str == null || str.equals("");
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
