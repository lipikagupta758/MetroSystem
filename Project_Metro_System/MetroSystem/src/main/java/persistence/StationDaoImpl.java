package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Station;
import exceptions.MetroStationDoNotExistException;

public class StationDaoImpl implements StationDao {

    @Override
    public ArrayList<Station> getStationList() {
        ArrayList<Station> stationList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement("SELECT * FROM Station");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("station_id");
                String name = resultSet.getString("station_name");

                Station station = new Station(id, name);
                stationList.add(station);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return stationList;
    }

    @Override
    public void checkStationExist(int stationId) throws MetroStationDoNotExistException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM STATION WHERE station_id=?"
            );
            preparedStatement.setInt(1, stationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new MetroStationDoNotExistException();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new MetroStationDoNotExistException();
        }
    }

    @Override
    public Station getStationById(int stationId) throws MetroStationDoNotExistException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM STATION WHERE station_id=?"
            );
            preparedStatement.setInt(1, stationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("station_id");
                String name = resultSet.getString("station_name");

                return new Station(id, name);
            } else {
                throw new MetroStationDoNotExistException();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new MetroStationDoNotExistException();
        }
    }
}
