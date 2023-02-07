package pl.envelo.moovelo;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final Integer ACCESS_TOKEN_DURATION_TIME = 10 * 60 * 1000; // 10 min
    public static final Integer RESET_TOKEN_DURATION_TIME = 10 * 60 * 1000; // 10 min
    public static final String SECRET_KEY_FOR_JWT_ALGORITHM = "moovelo";
    public static final String RESPONSE_CONTENT_TYPE = "application/json";
    public static final Integer VISITOR_CONFIRM_URL_DURATION_TIME = 24 * 60 * 60 * 1000; // 24 hours
}
