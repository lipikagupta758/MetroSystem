package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Card;
import entity.Station;
import entity.Transaction;
import exceptions.LowAmountException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionDaoImpl implements TransactionDao {
	
	@Override
	public void insertTransactionRecord(int cardId, int startStation, int stationId, double fare) {
		PreparedStatement preparedStatement = null;
        Transaction transaction = new Transaction();

        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Transaction (card_id, swipeIn_station, swipeOut_station, fare) VALUES(?, ?, ?, ?)"
            );

            preparedStatement.setInt(1, cardId);
            preparedStatement.setInt(2, startStation);
            preparedStatement.setInt(3, stationId);
            preparedStatement.setDouble(4, fare);
            preparedStatement.executeUpdate();

        } 
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return ;		
	}
	
	@Override
	public ArrayList<Transaction> getUserTransactionRecord(int cardId) {
		PreparedStatement preparedStatement = null;
		ArrayList<Transaction> transactionList= new ArrayList<>();

        try {
            Connection connection = helperconnection.getConnection();
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Transaction WHERE card_id=?"
            );
            
            preparedStatement.setInt(1, cardId);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()) {
                int tid = resultSet.getInt("transaction_id");
                int cid = resultSet.getInt("card_id");
                double fare = resultSet.getDouble("fare");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
                
                Transaction transaction = new Transaction();
                transaction.setTransId(tid);
                transaction.setFare(fare);
                transaction.setTransactionTimeStamp(transactionDate);
                transactionList.add(transaction);

            } 
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return transactionList;		
	}
}
