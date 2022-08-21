package service;

import database.FoundationDao;
import model.Foundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static utils.Utils.GSON_PRETTY;


public class FoundationEnricher {
    private final static Logger log = LoggerFactory.getLogger(CrawlerService.class);
    private final FoundationDao foundationDao;

    public FoundationEnricher(FoundationDao foundationDao) {
        this.foundationDao = foundationDao;
    }

    public List<Foundation> rankingFoundations(List<Foundation> foundations) {
        for (Foundation foundation : foundations) {
            try {
                foundation.setRankTop20WithinWeek(isTop20(foundation.getRankWithinWeek()));
                foundation.setRankWithinWeek(foundation.getRankWithinWeek() + " " + rankingInPercentage(foundation.getRankWithinWeek()));
                foundation.setRankTop20WithinMonth(isTop20(foundation.getRankWithinMonth()));
                foundation.setRankWithinMonth(foundation.getRankWithinMonth() + " " + rankingInPercentage(foundation.getRankWithinMonth()));
                foundation.setRankTop20ThreeMonth(isTop20(foundation.getRankWithinThreeMonth()));
                foundation.setRankWithinThreeMonth(foundation.getRankWithinThreeMonth() + " " + rankingInPercentage(foundation.getRankWithinThreeMonth()));
                foundation.setRankTop20SixMonth(isTop20(foundation.getRankWithinSixMonth()));
                foundation.setRankWithinSixMonth(foundation.getRankWithinSixMonth() + " " + rankingInPercentage(foundation.getRankWithinSixMonth()));
                foundation.setShouldWarn(!foundation.getRankTop20WithinWeek() && !foundation.getRankTop20SixMonth() && !foundation.getRankTop20ThreeMonth());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Failed to handle all foundations from cache {}", GSON_PRETTY.toJson(foundation));
            }
        }
        foundationDao.updateRanking(foundations);
        return foundations;
    }

    private static Boolean isTop20(String ranking) {
        return getRankInDouble(ranking) < 0.2;
    }

    private static String rankingInPercentage(String ranking) {
        return String.format("%.2f", getRankInDouble(ranking) * 100) + "%";
    }

    private static double getRankInDouble(String ranking) {
        String[] rankingArr = ranking.split("\\|");
        return (Double.parseDouble(rankingArr[0]) / Double.parseDouble(rankingArr[1]));
    }
}
