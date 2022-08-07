package cache;

import com.google.gson.reflect.TypeToken;
import model.Foundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Utils.GSON;
import static utils.Utils.GSON_PRETTY;


public class FileStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorage.class);
    public static final String FOUNDATION_STORAGE = "foundationStorage";
    public static final Type TYPE = new TypeToken<List<Foundation>>() {
    }.getType();

    public static void updateStorage(Foundation foundation) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FOUNDATION_STORAGE, true))) {
            String content = GSON.toJson(foundation);
            bufferedWriter.write(content);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            LOGGER.info("Update file storage with content {} successfully", content);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Update file storage failed");
        }
    }

    public static List<Foundation> loadStorageFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FOUNDATION_STORAGE))) {
            List<Foundation> foundations = bufferedReader.lines().distinct().map(line -> GSON.fromJson(line, Foundation.class)).collect(Collectors.toList());
            LOGGER.info("Load file storage with content {} successfully", "");
            return foundations;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Load file storage failed");
            return Collections.emptyList();
        }
    }


    public static void main(String[] args) throws IOException {
        testWriteFile();
        loadStorageFile();
    }


    private static void testWriteFile() {
        String simpleString = simpleString();
        List<Foundation> fromJson = GSON_PRETTY.fromJson(simpleString, TYPE);
        fromJson.forEach(FileStorage::updateStorage);
    }

    private static String simpleString() {
        return "[" +
                "  {" +
                "    \"code\": \"005911\"," +
                "    \"name\": \"广发双擎升级混合\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"2.5979\"," +
                "    \"estimatedGain\": \"+0.22%\"," +
                "    \"actualValue\": \"2.5921 (2020-02-12)\"," +
                "    \"actualGain\": \"1.81% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"2.5921\"," +
                "    \"gainWithinWeek\": \"7.69%\"," +
                "    \"gainWithinMonth\": \"13.69%\"," +
                "    \"gainWithinThreeMonth\": \"39.18%\"," +
                "    \"gainWithinSixMonth\": \"76.66%\"," +
                "    \"rankWithinWeek\": \"348 | 3186, top 10.92%\"," +
                "    \"rankWithinMonth\": \"123 | 3189, top 3.86%\"," +
                "    \"rankWithinThreeMonth\": \"44 | 3140, top 1.40%\"," +
                "    \"rankWithinSixMonth\": \"10 | 3042, top 0.33%\"," +
                "    \"isRankTop20WithinWeek\": true," +
                "    \"isRankTop20WithinMonth\": true," +
                "    \"isRankTop20ThreeMonth\": true," +
                "    \"isRankTop20SixMonth\": true," +
                "    \"shouldWarn\": false" +
                "  }," +
                "  {" +
                "    \"code\": \"161725\"," +
                "    \"name\": \"招商中证白酒指数基金\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"0.9277\"," +
                "    \"estimatedGain\": \"-0.78%\"," +
                "    \"actualValue\": \"0.9350 (2020-02-12)\"," +
                "    \"actualGain\": \"0.28% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"2.0611\"," +
                "    \"gainWithinWeek\": \"4.75%\"," +
                "    \"gainWithinMonth\": \"-9.55%\"," +
                "    \"gainWithinThreeMonth\": \"-5.37%\"," +
                "    \"gainWithinSixMonth\": \"2.25%\"," +
                "    \"rankWithinWeek\": \"5.26%\"," +
                "    \"rankWithinMonth\": \"-10.22%\"," +
                "    \"rankWithinThreeMonth\": \"-5.36%\"," +
                "    \"rankWithinSixMonth\": \"2.69%\"" +
                "  }," +
                "  {" +
                "    \"code\": \"004851\"," +
                "    \"name\": \"广发医疗保健股票\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"1.9447\"," +
                "    \"estimatedGain\": \"-1.27%\"," +
                "    \"actualValue\": \"1.9697 (2020-02-12)\"," +
                "    \"actualGain\": \"1.73% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"1.9697\"," +
                "    \"gainWithinWeek\": \"5.76%\"," +
                "    \"gainWithinMonth\": \"10.43%\"," +
                "    \"gainWithinThreeMonth\": \"11.85%\"," +
                "    \"gainWithinSixMonth\": \"37.86%\"," +
                "    \"rankWithinWeek\": \"512 | 1404\"," +
                "    \"rankWithinMonth\": \"92 | 1413\"," +
                "    \"rankWithinThreeMonth\": \"442 | 1333\"," +
                "    \"rankWithinSixMonth\": \"129 | 1259\"" +
                "  }," +
                "  {" +
                "    \"code\": \"110022\"," +
                "    \"name\": \"易方达消费行业股票\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"2.8997\"," +
                "    \"estimatedGain\": \"-0.52%\"," +
                "    \"actualValue\": \"2.9150 (2020-02-12)\"," +
                "    \"actualGain\": \"0.14% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"2.9150\"," +
                "    \"gainWithinWeek\": \"4.18%\"," +
                "    \"gainWithinMonth\": \"-7.05%\"," +
                "    \"gainWithinThreeMonth\": \"-0.92%\"," +
                "    \"gainWithinSixMonth\": \"6.54%\"," +
                "    \"rankWithinWeek\": \"887 | 1404\"," +
                "    \"rankWithinMonth\": \"1303 | 1413\"," +
                "    \"rankWithinThreeMonth\": \"1197 | 1333\"," +
                "    \"rankWithinSixMonth\": \"994 | 1259\"" +
                "  }," +
                "  {" +
                "    \"code\": \"519674\"," +
                "    \"name\": \"银河创新成长混合\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"5.3877\"," +
                "    \"estimatedGain\": \"+0.98%\"," +
                "    \"actualValue\": \"5.3354 (2020-02-12)\"," +
                "    \"actualGain\": \"2.14% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"5.3354\"," +
                "    \"gainWithinWeek\": \"7.97%\"," +
                "    \"gainWithinMonth\": \"23.10%\"," +
                "    \"gainWithinThreeMonth\": \"52.08%\"," +
                "    \"gainWithinSixMonth\": \"85.37%\"," +
                "    \"rankWithinWeek\": \"303 | 3186\"," +
                "    \"rankWithinMonth\": \"5 | 3189\"," +
                "    \"rankWithinThreeMonth\": \"7 | 3140\"," +
                "    \"rankWithinSixMonth\": \"5 | 3042\"" +
                "  }," +
                "  {" +
                "    \"code\": \"519056\"," +
                "    \"name\": \"海富通内需热点混合\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"2.0293\"," +
                "    \"estimatedGain\": \"-0.33%\"," +
                "    \"actualValue\": \"2.0360 (2020-02-12)\"," +
                "    \"actualGain\": \"1.29% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"2.0360\"," +
                "    \"gainWithinWeek\": \"4.79%\"," +
                "    \"gainWithinMonth\": \"-2.68%\"," +
                "    \"gainWithinThreeMonth\": \"8.13%\"," +
                "    \"gainWithinSixMonth\": \"24.60%\"," +
                "    \"rankWithinWeek\": \"1428 | 3186\"," +
                "    \"rankWithinMonth\": \"2864 | 3189\"," +
                "    \"rankWithinThreeMonth\": \"1513 | 3140\"," +
                "    \"rankWithinSixMonth\": \"863 | 3042\"" +
                "  }," +
                "  {" +
                "    \"code\": \"002121\"," +
                "    \"name\": \"广发沪港深新起点股票\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"1.4820\"," +
                "    \"estimatedGain\": \"+0.13%\"," +
                "    \"actualValue\": \"1.4800 (2020-02-12)\"," +
                "    \"actualGain\": \"1.58% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"1.5650\"," +
                "    \"gainWithinWeek\": \"4.23%\"," +
                "    \"gainWithinMonth\": \"-1.07%\"," +
                "    \"gainWithinThreeMonth\": \"9.31%\"," +
                "    \"gainWithinSixMonth\": \"20.33%\"," +
                "    \"rankWithinWeek\": \"872 | 1404\"," +
                "    \"rankWithinMonth\": \"655 | 1413\"," +
                "    \"rankWithinThreeMonth\": \"570 | 1333\"," +
                "    \"rankWithinSixMonth\": \"414 | 1259\"" +
                "  }," +
                "  {" +
                "    \"code\": \"005311\"," +
                "    \"name\": \"万家经济新动能混合A\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"1.9411\"," +
                "    \"estimatedGain\": \"+1.08%\"," +
                "    \"actualValue\": \"1.9203 (2020-02-12)\"," +
                "    \"actualGain\": \"4.59% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"1.9203\"," +
                "    \"gainWithinWeek\": \"11.79%\"," +
                "    \"gainWithinMonth\": \"30.42%\"," +
                "    \"gainWithinThreeMonth\": \"66.22%\"," +
                "    \"gainWithinSixMonth\": \"71.95%\"," +
                "    \"rankWithinWeek\": \"17 | 3186\"," +
                "    \"rankWithinMonth\": \"1 | 3189\"," +
                "    \"rankWithinThreeMonth\": \"2 | 3140\"," +
                "    \"rankWithinSixMonth\": \"18 | 3042\"" +
                "  }," +
                "  {" +
                "    \"code\": \"260108\"," +
                "    \"name\": \"景顺长城新兴成长混合\"," +
                "    \"date\": \"2020-02-13\"," +
                "    \"estimatedValue\": \"1.7241\"," +
                "    \"estimatedGain\": \"-0.46%\"," +
                "    \"actualValue\": \"1.7320 (2020-02-12)\"," +
                "    \"actualGain\": \"0.17% (2020-02-12)\"," +
                "    \"accumulativeValue\": \"3.3690\"," +
                "    \"gainWithinWeek\": \"5.16%\"," +
                "    \"gainWithinMonth\": \"-4.99%\"," +
                "    \"gainWithinThreeMonth\": \"-0.28%\"," +
                "    \"gainWithinSixMonth\": \"7.91%\"," +
                "    \"rankWithinWeek\": \"1225 | 3186\"," +
                "    \"rankWithinMonth\": \"3100 | 3189\"," +
                "    \"rankWithinThreeMonth\": \"3052 | 3140\"," +
                "    \"rankWithinSixMonth\": \"2068 | 3042\"" +
                "  }" +
                "]";
    }
}
