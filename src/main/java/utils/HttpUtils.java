package utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Optional;

@Slf4j
public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient();

    public static Optional<String> get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            log.info("get data from url={}, result={}", url, string);
            return Optional.of(string);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed to get data from url={}, reason={}", url, e.getMessage());
            return Optional.empty();
        }
    }
}
