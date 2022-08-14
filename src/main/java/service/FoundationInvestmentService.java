package service;

import database.FoundationDao;
import database.FoundationInvestmentDao;
import lombok.extern.slf4j.Slf4j;
import model.Foundation;
import model.FoundationInvestment;
import scheduler.Job;
import utils.DateUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static utils.DateUtils.today;


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
            return foundationInvestments;
        }
        List<Foundation> foundations = foundationDao.latestFoundations();
        for (FoundationInvestment foundationInvestment : foundationInvestments) {
            Optional<Foundation> foundation = foundations.stream().filter(it -> it.getCode().equalsIgnoreCase(foundationInvestment.getCode())).findFirst();
            if (foundation.isPresent()) {
                Foundation item = foundation.get();
                Double dailyInvestAmount = foundationInvestment.getDailyInvestAmount();
                Double totalAmount = foundationInvestment.getTotalAmount();
                Double totalProfit = foundationInvestment.getTotalProfit();
                Double commission = foundationInvestment.getCommission();
                Double actualGain = Double.parseDouble(item.getActualGain().split("%")[0].trim()) / 100;
                Double todayBuyInAmount = dailyInvestAmount * (1-commission);
                double todayTotalAmount = totalAmount + (totalAmount * actualGain) + todayBuyInAmount;
                Double initProfit = foundationInvestment.getInitProfit();
                Double todayTotalProfit = initProfit + (totalProfit * actualGain);
                foundationInvestment.setDate(today());
                foundationInvestment.setTotalAmount(todayTotalAmount);
                foundationInvestment.setTotalProfit(todayTotalProfit);
                foundationInvestmentDao.add(foundationInvestment);
            }
        }

        return foundationInvestmentDao.foundationInvestments();
    }


}
