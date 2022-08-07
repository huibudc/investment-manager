package scheduler;

import database.FoundationDao;
import lombok.extern.slf4j.Slf4j;
import model.Foundation;
import model.InvestFoundation;
import model.MarketFoundation;
import service.CrawlerService;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.Utils.GSON;

@Slf4j
public class Job {
    private final CrawlerService crawlerService;
    private final FoundationDao foundationDao;

    public Job(CrawlerService crawlerService, FoundationDao foundationDao) {
        this.crawlerService = crawlerService;
        this.foundationDao = foundationDao;
    }

    public int investedFoundationData() throws Exception {
        List<InvestFoundation> investFoundations = foundationDao.investFoundations();
        if (investFoundations == null || investFoundations.isEmpty()) return 0;
        log.info("start to getting data for investFoundations={}", GSON.toJson(investFoundations));
        List<Foundation> foundations = new ArrayList<>();
        for (InvestFoundation investFoundation : investFoundations) {
            log.info("start to getting data for code={}, name={}", investFoundation.getCode(), investFoundation.getName());
            Foundation foundationInfo = crawlerService.getFoundationInfo(investFoundation.getCode(), investFoundation.getName());
            if (foundationInfo != null) {
                foundations.add(foundationInfo);
            }
        }
        return foundationDao.addFoundationsInfo(foundations);
    }

    public void marketFoundationData() throws Exception {
        List<MarketFoundation> foundations = foundationDao.allMarketFoundations();
        if (foundations == null || foundations.isEmpty()) return;
        log.info("start to getting data for foundations={}", GSON.toJson(foundations));
        for (MarketFoundation marketFoundation : foundations) {
            try {
                long millis = new Random().nextLong(1000);
                log.info("sleep " + millis + " ms");
                Thread.sleep(millis);
                Foundation info = crawlerService.getFoundationInfo(marketFoundation.getCode(), marketFoundation.getName());
                foundationDao.addFoundationsInfo(List.of(info));
            } catch (Exception e) {
                log.info("failed to add info of {} into db due to ", GSON.toJson(marketFoundation), e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Job(new CrawlerService(), new FoundationDao()).investedFoundationData();
    }
}
