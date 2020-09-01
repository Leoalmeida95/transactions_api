package guiabolso.challenge.api.configuration;

import java.util.Calendar;

public abstract class ConstantsConfig {
    public static final int MAX_TRANSACTIONS_TO_GENERATE = 5;
    public static final int MIN_TRANSACTIONS_TO_GENERATE = 1;
    public static final int MAX_TRANSACTIONS_VALUE = 9999999;
    public static final int MIN_TRANSACTIONS_VALUE = -9999999;
    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static final int MIN_USER_ID_VALID = 1000;
    public static final int MAX_USER_ID_VALID = 1000000000;
    public static final int MIN_YEAR_TRANSACTION_VALID = 1960;
    public static final String FORMAT_DATA = "yyyy-MM-dd";
}
