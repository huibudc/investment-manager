package database;

import lombok.extern.slf4j.Slf4j;
import model.Foundation;
import model.InvestFoundation;
import model.MarketFoundation;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.HttpUtils;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.Utils.GSON;
import static utils.Utils.convertToYYYY_MM_DD;


@Slf4j
public class FoundationDao {
    private final static String INSERT_FOUNDATIONS = """
            insert into foundation (
               code ,
                   date ,
                   name,
                   estimatedValue ,
                   estimatedGain,
                   actualValue,
                   actualGain ,
                   accumulativeValue,
                   gainWithinWeek,
                   gainWithinMonth,
                   gainWithinThreeMonth,
                   gainWithinSixMonth,
                   rankWithinWeek,
                   rankWithinMonth,
                   rankWithinThreeMonth,
                   rankWithinSixMonth,
                   type
            ) values (? ,? ,?,?,
                   ? ,                   ?,                   ?,                   ?,
                   ?,                   ?,
                   ?,                   ?,
                   ?,                   ?,
                   ?,                   ?,                   ?
            )
            """;


    public List<InvestFoundation> investFoundations() {
        List<InvestFoundation> investFoundations = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from invest_foundation");
            while (resultSet.next()) {
                investFoundations.add(new InvestFoundation(resultSet.getString("code"), resultSet.getString("name")));
            }
            return investFoundations;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return investFoundations;
        }
    }

    public InvestFoundation investFoundation(String code) {
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from invest_foundation where code=" + code);
            while (resultSet.next()) {
                return new InvestFoundation(resultSet.getString("code"), resultSet.getString("name"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    public List<Foundation> foundations() {
        List<Foundation> foundations = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from foundation");
            while (resultSet.next()) {
                Foundation foundation = getFoundation(resultSet);
                foundations.add(foundation);
            }
            return foundations;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundations;
        }
    }

    public List<Foundation> latestFoundations() {
        String sql = """
                select * from foundation a
                join (select code, max(date) date from foundation group by code) b on a.code=b.code and a.date=b.date
                """;
        List<Foundation> foundations = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Foundation foundation = getFoundation(resultSet);
                foundations.add(foundation);
            }
            return foundations;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundations;
        }
    }

    public Foundation foundation(String code) {
        Foundation foundation = null;
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from foundation where code=" + code);
            while (resultSet.next()) {
                foundation = getFoundation(resultSet);
            }
            return foundation;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundation;
        }
    }

    private Foundation getFoundation(ResultSet resultSet) throws SQLException, ParseException {
        String code = resultSet.getString("code");
        String date = resultSet.getString("date");
        String name = resultSet.getString("name");
        String estimatedValue = resultSet.getString("estimatedValue");
        String estimatedGain = resultSet.getString("estimatedGain");
        String actualValue = resultSet.getString("actualValue");
        String actualGain = resultSet.getString("actualGain");
        String accumulativeValue = resultSet.getString("accumulativeValue");
        String gainWithinWeek = resultSet.getString("gainWithinWeek");
        String gainWithinMonth = resultSet.getString("gainWithinMonth");
        String gainWithinThreeMonth = resultSet.getString("gainWithinThreeMonth");
        String gainWithinSixMonth = resultSet.getString("gainWithinSixMonth");
        String rankWithinWeek = resultSet.getString("rankWithinWeek");
        String rankWithinMonth = resultSet.getString("rankWithinMonth");
        String rankWithinThreeMonth = resultSet.getString("rankWithinThreeMonth");
        String rankWithinSixMonth = resultSet.getString("rankWithinSixMonth");
        String type = resultSet.getString("type");
        Foundation foundation = new Foundation();
        foundation.setCode(code);
        foundation.setDate(date);
        foundation.setName(name);
        foundation.setType(type);
        foundation.setDate(convertToYYYY_MM_DD(estimatedValue));
        foundation.setEstimatedValue(estimatedValue);
        foundation.setEstimatedGain(estimatedGain);
        foundation.setActualValue(actualValue);
        foundation.setActualGain(actualGain);
        foundation.setAccumulativeValue(accumulativeValue);
        foundation.setGainWithinWeek(gainWithinWeek);
        foundation.setGainWithinMonth(gainWithinMonth);
        foundation.setGainWithinThreeMonth(gainWithinThreeMonth);
        foundation.setGainWithinSixMonth(gainWithinSixMonth);
        foundation.setRankWithinWeek(rankWithinWeek);
        foundation.setRankWithinMonth(rankWithinMonth);
        foundation.setRankWithinThreeMonth(rankWithinThreeMonth);
        foundation.setRankWithinSixMonth(rankWithinSixMonth);
        return foundation;
    }

    public int addFoundationsInfo(List<Foundation> foundations) {
        if (foundations == null || foundations.isEmpty()) return 0;
        int count = 0;
        try (Connection connection = DbUtils.connection();
             PreparedStatement statement = connection.prepareStatement(INSERT_FOUNDATIONS);) {
            for (Foundation foundation : foundations) {
                statement.setString(1, foundation.getCode());
                statement.setString(2, foundation.getDate());
                statement.setString(3, foundation.getName());
                statement.setString(4, foundation.getEstimatedValue());
                statement.setString(5, foundation.getEstimatedGain());
                statement.setString(6, foundation.getActualValue());
                statement.setString(7, foundation.getActualGain());
                statement.setString(8, foundation.getAccumulativeValue());
                statement.setString(9, foundation.getGainWithinWeek());
                statement.setString(10, foundation.getGainWithinMonth());
                statement.setString(11, foundation.getGainWithinThreeMonth());
                statement.setString(12, foundation.getGainWithinSixMonth());
                statement.setString(13, foundation.getRankWithinWeek());
                statement.setString(14, foundation.getRankWithinMonth());
                statement.setString(15, foundation.getRankWithinThreeMonth());
                statement.setString(16, foundation.getRankWithinSixMonth());
                statement.setString(17, foundation.getType());
                boolean execute = statement.execute();
                if (execute) count++;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return 0;
        }
    }

    public void addAllFoundationsList() {
        Optional<String> result = HttpUtils.get("https://stockapi.com.cn/v1/fund/all");
        if (result.isEmpty()) {
            return;
        }
        JSONArray jsonArray = new JSONObject(result.get()).getJSONArray("data");
        if (jsonArray != null && !jsonArray.isEmpty()) {
            try(Connection connection = DbUtils.connection();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into foundation_all(code, type, name) value (?, ? ,?)")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    preparedStatement.setString(1, jsonObject.getString("code"));
                    preparedStatement.setString(2, jsonObject.getString("type"));
                    preparedStatement.setString(3, jsonObject.getString("name"));
                    preparedStatement.addBatch();
                }
                connection.prepareStatement("truncate table foundation_all");
                preparedStatement.executeBatch();
                log.info("got all foundations successfully");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("failed to get all foundations due to {}", e.getMessage());
            }
        }
    }

    public List<MarketFoundation> allMarketFoundations() {
        List<MarketFoundation> foundations = new ArrayList<>();
        try(Connection connection = DbUtils.connection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from foundation_all")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MarketFoundation marketFoundation = new MarketFoundation(
                        resultSet.getString("code"),
                        resultSet.getString("type"),
                        resultSet.getString("name")
                        );
                foundations.add(marketFoundation);
            }
            log.info("got all foundations from DB successfully");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed to get all foundations from DB due to {}", e.getMessage());
        }
        return foundations;
    }
}
