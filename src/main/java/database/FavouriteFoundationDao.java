package database;

import lombok.extern.slf4j.Slf4j;
import model.FavouriteFoundation;
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

import static utils.Utils.convertToYYYY_MM_DD;


@Slf4j
public class FavouriteFoundationDao {
        public List<FavouriteFoundation> favouriteFoundations() {
        List<FavouriteFoundation> foundations = new ArrayList<>();
        try (Connection connection = DbUtils.connection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from invest_foundation");
            while (resultSet.next()) {
                FavouriteFoundation foundation = new FavouriteFoundation(resultSet.getString("code"), resultSet.getString("name"));
                foundations.add(foundation);
            }
            return foundations;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return foundations;
        }
    }

}
