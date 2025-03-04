package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.Card;
import entity.Station;
import exceptions.*;
import persistence.StationDao;
import persistence.StationDaoImpl;

public class CardDaoImpl implements CardDao {
    private StationDao stationDao = new StationDaoImpl();

    @Override
    public int issueCard(double amount) throws LowAmountException {
        PreparedStatement preparedStatement = null;
        Card card = new Card();
        int id = 0;
        Date issueDate = new Date(System.currentTimeMillis());

        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (amount < 100) {
                throw new LowAmountException("Low Balance! New Card is issued with minimum Rs.100");
            }

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO CARD (balance, issue_date, swipeIn_station, swipeOut_station) VALUES(?, CURRENT_DATE, NULL, NULL)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setDouble(1, amount);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                card.setCardId(id);
                card.setIssueDate(issueDate);
                card.setBalance(amount);
                card.setSwipeInStation(null);
                card.setSwipeOutStation(null);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void addCardBalance(int cardId, double amount) {
        PreparedStatement preparedStatement = null;
        try {
            Card card = getCardDetailsById(cardId);
            amount += card.getBalance();
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "UPDATE CARD SET balance=? WHERE card_id=?"
            );

            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, cardId);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException | WrongCardNoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card getCardDetailsById(int cardId) throws WrongCardNoException {
        PreparedStatement preparedStatement = null;
        Card card = new Card();
        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CARD WHERE card_id=?"
            );
            preparedStatement.setInt(1, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int cid = resultSet.getInt("card_id");
                double cbalance = resultSet.getDouble("balance");
                Date issueDate = resultSet.getDate("issue_date");

                Integer swipeInStationId = resultSet.getObject("swipeIn_station", Integer.class);
                Integer swipeOutStationId = resultSet.getObject("swipeOut_station", Integer.class);

                Station swipeInStation = null;
                Station swipeOutStation = null;

                try {
                    if (swipeInStationId != null) {
                        swipeInStation = stationDao.getStationById(swipeInStationId);
                    }
                    if (swipeOutStationId != null) {
                        swipeOutStation = stationDao.getStationById(swipeOutStationId);
                    }
                } 
                catch (MetroStationDoNotExistException e) {
                    e.printStackTrace();
                }

                card.setCardId(cid);
                card.setBalance(cbalance);
                card.setIssueDate(issueDate);
                card.setSwipeInStation(swipeInStation);
                card.setSwipeOutStation(swipeOutStation);
            } else {
                throw new WrongCardNoException();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new WrongCardNoException();
        }
        return card;
    }

    @Override
    public void swipeOut(int cardId, int stationId, double fare) throws NotSwipedInException, InsufficientBalanceException, WrongCardNoException {
        PreparedStatement preparedStatement = null;
        try {
            Card card = getCardDetailsById(cardId);
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (card.getSwipeInStation() == null) {
                throw new NotSwipedInException("Swipe-in record not found! Please swipe in before swiping out.");
            }

            int startStation = card.getSwipeInStation().getStationId();

            if (card.getBalance() < fare) {
                throw new InsufficientBalanceException("Insufficient balance to deduct fare.");
            }

            double newBalance = card.getBalance() - fare;
            card.setBalance(newBalance);

            preparedStatement = connection.prepareStatement(
                    "UPDATE CARD SET balance=?, swipeIn_station=NULL WHERE card_id=?"
            );

            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, cardId);
            preparedStatement.executeUpdate();

            return ;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new WrongCardNoException();
        }
    }

    @Override
    public void swipeIn(int cardId, int stationId) throws InsufficientBalanceException {
        PreparedStatement preparedStatement = null;
        try {
            Card card = getCardDetailsById(cardId);
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (card.getBalance() < 20) {
                throw new InsufficientBalanceException("Insufficient balance! Please recharge your card.");
            }

            preparedStatement = connection.prepareStatement(
                    "UPDATE CARD SET swipeIn_station=? WHERE card_id=?"
            );
            preparedStatement.setInt(1, stationId);
            preparedStatement.setInt(2, cardId);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException | WrongCardNoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkCardExist(int cardId) throws WrongCardNoException {
        try {
            getCardDetailsById(cardId);
        } catch (WrongCardNoException e) {
            throw new WrongCardNoException("Card does not exist!");
        }
    }
}
