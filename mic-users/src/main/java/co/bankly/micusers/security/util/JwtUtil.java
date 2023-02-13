package co.bankly.micusers.security.util;

public class JwtUtil {
    public static final String SECRET = "myKeyBankly.CO";
    public static final String AUTH_HEADER = "Authorization";
    public static final long EXPIRED_JETON = 86400000; // one day
    public static final long EXPIRED_JETON_REFRESH = 86400000;// 1 day
    public static final String BEARER = "Bearer ";
    public static final String REFRESH_JETON = "/api/v1/users/refresh";
    public static final String AUTHORITIES = "authorities";
}
