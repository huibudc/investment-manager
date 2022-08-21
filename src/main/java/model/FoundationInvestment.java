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
