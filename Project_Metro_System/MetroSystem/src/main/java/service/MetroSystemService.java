package service;

import java.util.ArrayList;
import entity.Station;
import entity.Transaction;
import exceptions.*;
import entity.Card;

public interface MetroSystemService {
	ArrayList<Station> getStationList();
	void swipeIn(int cardId, int stationId) throws InsufficientBalanceException;
	void checkCardExist(int cardId) throws WrongCardNoException;
	void checkStationExist(int stationId) throws MetroStationDoNotExistException;
	int issueCard(double amount) throws LowAmountException;
	Card getCardDetailsById(int id) throws WrongCardNoException;
	void addCardBalance(int cardId, double amount);
    double swipeOut(int cardId, int stationId) throws NotSwipedInException, InsufficientBalanceException, WrongCardNoException, MetroStationDoNotExistException;
	ArrayList<Transaction> getTransactionRecord(int cardId);
}
