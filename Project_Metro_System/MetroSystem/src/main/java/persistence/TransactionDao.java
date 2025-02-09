package persistence;

import java.util.ArrayList;

import entity.Transaction;

public interface TransactionDao {
	void insertTransactionRecord(int cardId, int startStation, int stationId, double fare);
	ArrayList<Transaction> getUserTransactionRecord(int cardId);
}
