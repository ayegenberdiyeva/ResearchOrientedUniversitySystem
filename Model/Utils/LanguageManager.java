package Utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);

    public static void setLanguage(String language) {
        currentLocale = new Locale(language);
        bundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String getMessage(String key, Object... args) {
        String message = bundle.getString(key);
        return String.format(message, args);
    }
}
