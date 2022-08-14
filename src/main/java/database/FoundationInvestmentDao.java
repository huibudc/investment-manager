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
            ResultSet resultSet = statement.executeQuery("select * from foundation_investment order by date desc");
            foundationInvestmentFromResultSet(foundationInvestments, resultSet);
            return foundationInvestments;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundationInvestments;
        }
    }

    public boolean add(FoundationInvestment foundationInvestment) {
//        String sql = """
//                    INSERT INTO foundation_investment
//                (`date`, code, name, init_amount, init_profit, daily_invest_amount, commission, total_amount, total_profit, is_enabled)
//                 VALUES (?,
//                           ?,
//                           ?,
//                           ?,
//                           ?,
//                           ?,
//                           ?,
//                           ?,
//                           ?,
//                           ?)
//                """;
//        try (Connection connection = DbUtils.connection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, foundationInvestment.getDate());
//            statement.setString(2, foundationInvestment.getCode());
//            statement.setString(3, foundationInvestment.getName());
//            statement.setFloat(4, foundationInvestment.getInitAmount().floatValue());
//            statement.setFloat(5, foundationInvestment.getInitProfit().floatValue());
//            statement.setFloat(6, foundationInvestment.getDailyInvestAmount().floatValue());
//            statement.setFloat(7, foundationInvestment.getCommission().floatValue());
//            statement.setFloat(8, foundationInvestment.getTotalAmount().floatValue());
//            statement.setFloat(9, foundationInvestment.getTotalProfit().floatValue());
//            statement.setInt(10, foundationInvestment.isEnabled() ? 1 : 0);
//            return statement.execute(sql);

        String sql = """
                    INSERT INTO foundation_investment
                (`date`, code, name, init_amount, init_profit, daily_invest_amount, commission, total_amount, total_profit, is_enabled)
                 VALUES (%s,
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
                    foundationInvestment.isEnabled() ? 1 : 0
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
            foundationInvestments.add(foundationInvestment);
        }
    }
}
