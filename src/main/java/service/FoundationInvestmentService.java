package service;

import database.FoundationDao;
import database.FoundationInvestmentDao;
import lombok.extern.slf4j.Slf4j;
import model.Foundation;
import model.FoundationInvestment;
import scheduler.Job;
import utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static utils.DateUtils.today;
import static utils.Utils.GSON;


@Slf4j
public class FoundationInvestmentService {
    private final Job job;
    private final FoundationInvestmentDao foundationInvestmentDao;
    private final FoundationDao foundationDao;

    public FoundationInvestmentService(Job job, FoundationInvestmentDao foundationInvestmentDao, FoundationDao foundationDao) {
        this.job = job;
        this.foundationInvestmentDao = foundationInvestmentDao;
        this.foundationDao = foundationDao;
    }


    public List<FoundationInvestment> foundationInvestments() throws Exception {
        List<FoundationInvestment> foundationInvestments = foundationInvestmentDao.latestFoundationInvestments();
        if (DateUtils.isWeekendOrPublicHoliday()) {
            return foundationInvestmentDao.foundationInvestments();
        }
        if (!foundationDao.isLatestData()) {
            job.investedFoundationData();
        }
        List<Foundation> foundations = foundationDao.latestFoundations();

        for (FoundationInvestment foundationInvestment : foundationInvestments) {
            Optional<Foundation> foundation = foundations.stream().filter(it -> it.getCode().equalsIgnoreCase(foundationInvestment.getCode())).findFirst();
            if (foundation.isPresent()) {
                Foundation item = foundation.get();
                if (today().equalsIgnoreCase(item.getDate()) && !foundationInvestment.getDate().equalsIgnoreCase(item.getDate())) {
                    Double dailyInvestAmount = foundationInvestment.getDailyInvestAmount();
                    Double totalAmount = foundationInvestment.getTotalAmount();
                    Double totalProfit = foundationInvestment.getTotalProfit();
                    Double commission = foundationInvestment.getCommission();
                    Double actualGain = Double.parseDouble(item.getActualGain().split("%")[0].trim()) / 100;
                    Double todayBuyInAmount = dailyInvestAmount * (1 - commission);
                    Double todayTotalProfit = totalProfit + (totalAmount * actualGain);
                    double todayTotalAmount = totalAmount + (totalAmount * actualGain) + todayBuyInAmount;
                    foundationInvestment.setDate(today());
                    foundationInvestment.setTotalAmount(todayTotalAmount);
                    foundationInvestment.setTotalProfit(todayTotalProfit);
                    foundationInvestmentDao.add(foundationInvestment);
                }
            }
        }
        return foundationInvestmentDao.foundationInvestments();
    }

    public static void main(String[] args) throws ParseException {
        // init data since 2022-08-12
        FoundationInvestmentDao foundationInvestmentDao = new FoundationInvestmentDao();
        FoundationDao foundationDao = new FoundationDao();
        List<FoundationInvestment> foundationInvestmentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<FoundationInvestment> foundationInvestments = foundationInvestmentDao.latestFoundationInvestments();
            List<Foundation> foundations = foundationDao.foundations();
            List<FoundationInvestment> foundationInvestmentList_nextDay = foundationInvestments.stream().map(it -> GSON.fromJson(GSON.toJson(it), FoundationInvestment.class)).toList();
            String nextDay = DateUtils.nextDay(foundationInvestmentList_nextDay.get(0).getDate());
            for (int j = 0; j < 5; j++) {
                String finalNextDay = nextDay;
                boolean b = foundations.stream().anyMatch(it -> it.getDate().equalsIgnoreCase(finalNextDay));
                if (!b) {
                    nextDay = DateUtils.nextDay(nextDay);
                    continue;
                }
                String finalNextDay1 = nextDay;
                String finalNextDay2 = nextDay;
                foundationInvestmentList_nextDay.forEach(foundationInvestment -> {
                    Optional<Foundation> foundation = foundations.stream().filter(item ->
                            item.getDate().equalsIgnoreCase(finalNextDay1) &&
                                    foundationInvestment.getCode().equalsIgnoreCase(item.getCode())).findFirst();
                    if (foundation.isPresent()) {
                        Foundation item = foundation.get();
                        Double dailyInvestAmount = foundationInvestment.getDailyInvestAmount();
                        Double totalAmount = foundationInvestment.getTotalAmount();
                        Double totalProfit = foundationInvestment.getTotalProfit();
                        Double commission = foundationInvestment.getCommission();
                        Double actualGain = Double.parseDouble(item.getActualGain().split("%")[0].trim()) / 100;
                        Double todayBuyInAmount = dailyInvestAmount * (1 - commission);
                        Double todayTotalProfit = totalProfit + (totalAmount * actualGain);
                        double todayTotalAmount = totalAmount + (totalAmount * actualGain) + todayBuyInAmount;
                        foundationInvestment.setDate(finalNextDay2);
                        foundationInvestment.setTotalAmount(todayTotalAmount);
                        foundationInvestment.setTotalProfit(todayTotalProfit);
                        foundationInvestmentList.add(foundationInvestment);
                        foundationInvestmentDao.add(foundationInvestment);
                    }
                });
            }
        }
        System.out.println(GSON.toJson(foundationInvestmentList));
    }

}
