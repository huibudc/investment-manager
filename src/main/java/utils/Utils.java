package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_yy_MM_dd = new SimpleDateFormat("yy-MM-dd");
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public final static Gson GSON_PRETTY = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public final static Gson GSON = new GsonBuilder()
            .create();

    public static String convertToYYYY_MM_DD(String yy_mm_dd) throws ParseException {
        if (yy_mm_dd.equalsIgnoreCase("--")) {
            return "--";
        }
        return SIMPLE_DATE_FORMAT_yyyy_MM_dd.format(SIMPLE_DATE_FORMAT_yy_MM_dd.parse(yy_mm_dd));
    }

    public static String getDateYYYY_MM_DD() {
        return SIMPLE_DATE_FORMAT_yyyy_MM_dd.format(new Date());
    }
}
