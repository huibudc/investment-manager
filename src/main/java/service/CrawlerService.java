package service;

import model.Foundation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

import static utils.Utils.GSON;
import static utils.Utils.convertToYYYY_MM_DD;


public class CrawlerService {
    private final static Logger log = LoggerFactory.getLogger(CrawlerService.class);

    public static void main(String[] args) throws Exception {
        new CrawlerService().getFoundationInfo("161725", "易方达国防军工混合A");
    }

    public Foundation getFoundationInfo(String code, String name) throws Exception {
        String url = foundationUrl(code);
        log.info("Start to craw data from foundation code={} ,from url={}", code, url);
        try {
            if (isDisallowToFetchData(url)) {
                log.info("Not allow to craw data from url={}, please check the robots file from http://fund.eastmoney.com/robots.txt", url);
                throw new RuntimeException("Not allow to craw data, please check the robots file from http://fund.eastmoney.com/robots.txt");
            }
            Foundation foundation = new Foundation();
            Document doc = Jsoup.connect(url).get();
            String text = doc.select("#gz_gztime").text();
            System.out.println("text=" + text);
            String estimatedValueTime = text.substring(1, 9);
            String estimatedValue = doc.select("#gz_gsz").text();  // 估值
            String estimatedGain = doc.select("#gz_gszzl").text();  //  估值涨幅
            String actualUnitDateStr = doc.select(".fundInfoItem .dataItem02 dt p").text();
            String actualUnitDate = actualUnitDateStr.split(" ")[1];
            Elements actualUnit = doc.select(".fundInfoItem .dataItem02 .dataNums span");
            String actualValue = actualUnit.first().text() + " " + actualUnitDate;
            String actualGain = actualUnit.last().text() + " " + actualUnitDate;
            String accumulativeValue = doc.select(".fundInfoItem .dataItem03 .dataNums span").first().text();
            Elements increaseAmountStages = doc.select("#increaseAmount_stage table tbody tr");
            Element stageIncreaseInfo = increaseAmountStages.get(1);
            Element rankingWithinSameFoundation = increaseAmountStages.get("161725".equalsIgnoreCase(code) ? 5 : 4); // 招商中证白酒指数(LOF)A(161725)
            Elements foundtionInfos = doc.select(".infoOfFund table tr");
            String foundationType = foundtionInfos.get(0).select("td").get(0).select("a").text();

            foundation.setCode(code);
            foundation.setName(name);
            foundation.setType(foundationType);
            foundation.setDate(convertToYYYY_MM_DD(estimatedValueTime));
            foundation.setEstimatedValue(estimatedValue);
            foundation.setEstimatedGain(estimatedGain);
            foundation.setActualValue(actualValue);
            foundation.setActualGain(actualGain);
            foundation.setAccumulativeValue(accumulativeValue);
            foundation.setGainWithinWeek(stageIncreaseInfo.select("td").get(1).text());
            foundation.setGainWithinMonth(stageIncreaseInfo.select("td").get(2).text());
            foundation.setGainWithinThreeMonth(stageIncreaseInfo.select("td").get(3).text());
            foundation.setGainWithinSixMonth(stageIncreaseInfo.select("td").get(4).text());
            foundation.setRankWithinWeek(rankingWithinSameFoundation.select("td").get(1).text());
            foundation.setRankWithinMonth(rankingWithinSameFoundation.select("td").get(2).text());
            foundation.setRankWithinThreeMonth(rankingWithinSameFoundation.select("td").get(3).text());
            foundation.setRankWithinSixMonth(rankingWithinSameFoundation.select("td").get(4).text());
            return foundation;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Failed to craw data from foundation code={} ,from url={}", code, url);
            return null;
        }
    }

    public static Boolean isDisallowToFetchData(String targetUrl) {
        boolean isDisAllowToFetch = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("User-agent:") && !line.contains("User-agent: *")) {
                    log.info("Not allow to craw data");
                    throw new RuntimeException("Not allow to craw data, please check the robots file from http://fund.eastmoney.com/robots.txt");
                }
                if (line.startsWith("Disallow:")) {
                    String pattern = line.split(":")[1].trim();
                    boolean isDisallow = Pattern.compile(pattern).matcher(targetUrl).find();
                    isDisAllowToFetch = isDisallow;
                    log.info("Current url: {}, Disallow pattern is {}, isDisallow={}", targetUrl, pattern, isDisallow);
                    if (isDisAllowToFetch) {
                        break;
                    }
                }
            }
            return isDisAllowToFetch;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static InputStream inputStream() throws IOException {
        return new URL("http://fund.eastmoney.com/robots.txt").openStream();
    }

    public static String foundationUrl(String foundationCode) {
        return String.format("http://fund.eastmoney.com/%s.html", foundationCode);
    }
}
