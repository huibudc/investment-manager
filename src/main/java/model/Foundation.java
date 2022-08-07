package model;

public class Foundation {
    private String code;
    private String name;
    private String date;

    private String type;
    private String estimatedValue;
    private String estimatedGain;
    private String actualValue;
    private String actualGain;
    private String accumulativeValue;
    private String gainWithinWeek;
    private String gainWithinMonth;
    private String gainWithinThreeMonth;
    private String gainWithinSixMonth;
    private String rankWithinWeek;
    private String rankWithinMonth;
    private String rankWithinThreeMonth;
    private String rankWithinSixMonth;
    private Boolean isRankTop20WithinWeek;
    private Boolean isRankTop20WithinMonth;
    private Boolean isRankTop20ThreeMonth;
    private Boolean isRankTop20SixMonth;
    private Boolean shouldWarn;
    private final Long timeStamp = System.currentTimeMillis();

    public Long getTimeStamp() {
        return timeStamp;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(String estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getEstimatedGain() {
        return estimatedGain;
    }

    public void setEstimatedGain(String estimatedGain) {
        this.estimatedGain = estimatedGain;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getActualGain() {
        return actualGain;
    }

    public void setActualGain(String actualGain) {
        this.actualGain = actualGain;
    }

    public String getAccumulativeValue() {
        return accumulativeValue;
    }

    public void setAccumulativeValue(String accumulativeValue) {
        this.accumulativeValue = accumulativeValue;
    }

    public String getGainWithinWeek() {
        return gainWithinWeek;
    }

    public void setGainWithinWeek(String gainWithinWeek) {
        this.gainWithinWeek = gainWithinWeek;
    }

    public String getGainWithinMonth() {
        return gainWithinMonth;
    }

    public void setGainWithinMonth(String gainWithinMonth) {
        this.gainWithinMonth = gainWithinMonth;
    }

    public String getGainWithinThreeMonth() {
        return gainWithinThreeMonth;
    }

    public void setGainWithinThreeMonth(String gainWithinThreeMonth) {
        this.gainWithinThreeMonth = gainWithinThreeMonth;
    }

    public String getGainWithinSixMonth() {
        return gainWithinSixMonth;
    }

    public void setGainWithinSixMonth(String gainWithinSixMonth) {
        this.gainWithinSixMonth = gainWithinSixMonth;
    }

    public String getRankWithinWeek() {
        return rankWithinWeek;
    }

    public void setRankWithinWeek(String rankWithinWeek) {
        this.rankWithinWeek = rankWithinWeek;
    }

    public String getRankWithinMonth() {
        return rankWithinMonth;
    }

    public void setRankWithinMonth(String rankWithinMonth) {
        this.rankWithinMonth = rankWithinMonth;
    }

    public String getRankWithinThreeMonth() {
        return rankWithinThreeMonth;
    }

    public void setRankWithinThreeMonth(String rankWithinThreeMonth) {
        this.rankWithinThreeMonth = rankWithinThreeMonth;
    }

    public String getRankWithinSixMonth() {
        return rankWithinSixMonth;
    }

    public void setRankWithinSixMonth(String rankWithinSixMonth) {
        this.rankWithinSixMonth = rankWithinSixMonth;
    }

    public Boolean getShouldWarn() {
        return shouldWarn;
    }

    public void setShouldWarn(Boolean shouldWarn) {
        this.shouldWarn = shouldWarn;
    }
}
