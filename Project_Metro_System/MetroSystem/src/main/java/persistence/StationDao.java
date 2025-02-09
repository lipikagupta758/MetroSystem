package persistence;

import java.util.ArrayList;
import entity.Station;
import exceptions.MetroStationDoNotExistException;

public interface StationDao {
    ArrayList<Station> getStationList();

    void checkStationExist(int stationId) throws MetroStationDoNotExistException;

    Station getStationById(int stationId) throws MetroStationDoNotExistException;
}
