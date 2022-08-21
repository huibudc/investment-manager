package model;

public class FoundationInvestment {
    private String date;
    private String code;
    private String name;
    private Double initAmount;
    private Double initProfit;
    private Double dailyInvestAmount;
    private Double commission;
    private Double totalAmount;
    private Double totalProfit;
    private boolean isEnabled;

    private String actualGain;

    private String profitRate;

    private Boolean isRankTop20WithinWeek;
    private Boolean isRankTop20WithinMonth;
    private Boolean isRankTop20ThreeMonth;
    private Boolean isRankTop20SixMonth;
    private Boolean shouldWarn;

    public Boolean getRankTop20WithinWeek() {
        return isRankTop20WithinWeek;
    }

    public void setRankTop20WithinWeek(Boolean rankTop20WithinWeek) {
        isRankTop20WithinWeek = rankTop20WithinWeek;
    }

    public Boolean getRankTop20WithinMonth() {
        return isRankTop20WithinMonth;
    }

    public void setRankTop20WithinMonth(Boolean rankTop20WithinMonth) {
        isRankTop20WithinMonth = rankTop20WithinMonth;
    }

    public Boolean getRankTop20ThreeMonth() {
        return isRankTop20ThreeMonth;
    }

    public void setRankTop20ThreeMonth(Boolean rankTop20ThreeMonth) {
        isRankTop20ThreeMonth = rankTop20ThreeMonth;
    }

    public Boolean getRankTop20SixMonth() {
        return isRankTop20SixMonth;
    }

    public void setRankTop20SixMonth(Boolean rankTop20SixMonth) {
        isRankTop20SixMonth = rankTop20SixMonth;
    }

    public Boolean getShouldWarn() {
        return shouldWarn;
    }

    public void setShouldWarn(Boolean shouldWarn) {
        this.shouldWarn = shouldWarn;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public String getActualGain() {
        return actualGain;
    }

    public void setActualGain(String actualGain) {
        this.actualGain = actualGain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount = initAmount;
    }

    public Double getInitProfit() {
        return initProfit;
    }

    public void setInitProfit(Double initProfit) {
        this.initProfit = initProfit;
    }

    public Double getDailyInvestAmount() {
        return dailyInvestAmount;
    }

    public void setDailyInvestAmount(Double dailyInvestAmount) {
        this.dailyInvestAmount = dailyInvestAmount;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
