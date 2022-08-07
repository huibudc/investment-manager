package utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Slf4j
public class DateUtils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean isHoliday(String date) {
        String url = "http://timor.tech/api/holiday/year/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = client.newCall(request).execute();) {
            String result = response.body().string();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject holidays = jsonObject.getJSONObject("holiday");
            Set<String> keys = holidays.keySet();
            for (String key : keys) {
                boolean isHoliday = holidays.getJSONObject(key).getString("date").equalsIgnoreCase(date);
                if (isHoliday) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isWeekend(String date) throws ParseException {
        Calendar instance = Calendar.getInstance();
        instance.setTime(SIMPLE_DATE_FORMAT.parse(date));
        return instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

    }

    public static boolean isWeekendOrPublicHoliday() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String format = SIMPLE_DATE_FORMAT.format(date);
        return isWeekend(format) || isHoliday(format);
    }

    public static String today() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return SIMPLE_DATE_FORMAT.format(date);
    }

}
