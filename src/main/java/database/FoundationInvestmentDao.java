package database;

import lombok.extern.slf4j.Slf4j;
import model.FoundationInvestment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class FoundationInvestmentDao {
    public List<FoundationInvestment> foundationInvestments() {
        List<FoundationInvestment> foundationInvestments = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from foundation_investment order by code desc, date desc");
            foundationInvestmentFromResultSet(foundationInvestments, resultSet);
            return foundationInvestments;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundationInvestments;
        }
    }

    public boolean add(FoundationInvestment foundationInvestment) {
        String sql = """
                    INSERT INTO foundation_investment
                (`date`, code, name, init_amount, init_profit, daily_invest_amount, commission, total_amount, total_profit, is_enabled
                    ,isRankTop20WithinWeek, isRankTop20WithinMonth, isRankTop20ThreeMonth, isRankTop20SixMonth, shouldWarn
                )
                 VALUES (%s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s,
                           %s)
                """;
        try (Connection connection = DbUtils.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String formatted = sql.formatted("'" + foundationInvestment.getDate() + "'",
                    "'" + foundationInvestment.getCode() + "'",
                    "'" + foundationInvestment.getName() + "'",
                    foundationInvestment.getInitAmount(),
                    foundationInvestment.getInitProfit(),
                    foundationInvestment.getDailyInvestAmount(),
                    foundationInvestment.getCommission(),
                    foundationInvestment.getTotalAmount(),
                    foundationInvestment.getTotalProfit(),
                    foundationInvestment.isEnabled() ? 1 : 0,
                    foundationInvestment.getRankTop20WithinWeek(),
                    foundationInvestment.getRankTop20WithinMonth(),
                    foundationInvestment.getRankTop20ThreeMonth(),
                    foundationInvestment.getRankTop20SixMonth(),
                    foundationInvestment.getShouldWarn()
            );
            System.out.println("formatted=" + formatted);
            return statement.execute(formatted);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            boolean execute = statement.execute("delete from foundation_investment where date = '2022-08-08'");
            System.out.println(execute);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    public List<FoundationInvestment> latestFoundationInvestments() {
        String sql = """
                select *
                from
                foundation_investment a
                join (select code, max(date) date from foundation_investment group by code) b on a.code=b.code and a.date=b.date
                 """;
        List<FoundationInvestment> foundationInvestments = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            foundationInvestmentFromResultSet(foundationInvestments, resultSet);
            return foundationInvestments;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundationInvestments;
        }
    }

    private void foundationInvestmentFromResultSet(List<FoundationInvestment> foundationInvestments, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String date = resultSet.getString("date");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            Double initAmount = resultSet.getDouble("init_amount");
            Double initProfit = resultSet.getDouble("init_profit");
            Double commission = resultSet.getDouble("commission");
            Double dailyInvestAmount = resultSet.getDouble("daily_invest_amount");
            Double totalAmount = resultSet.getDouble("total_amount");
            Double totalProfit = resultSet.getDouble("total_profit");
            double profitRate = resultSet.getDouble("profit_rate");
            String actualGain = resultSet.getString("actualGain");
            boolean isEnabled = resultSet.getBoolean("is_enabled");
            FoundationInvestment foundationInvestment = new FoundationInvestment();
            foundationInvestment.setDate(date);
            foundationInvestment.setCode(code);
            foundationInvestment.setName(name);
            foundationInvestment.setInitAmount(initAmount);
            foundationInvestment.setInitProfit(initProfit);
            foundationInvestment.setDailyInvestAmount(dailyInvestAmount);
            foundationInvestment.setCommission(commission);
            foundationInvestment.setTotalAmount(totalAmount);
            foundationInvestment.setTotalProfit(totalProfit);
            foundationInvestment.setEnabled(isEnabled);
            foundationInvestment.setActualGain(actualGain);
            foundationInvestment.setProfitRate((profitRate * 100) + " %");
            foundationInvestments.add(foundationInvestment);
        }
    }
}
