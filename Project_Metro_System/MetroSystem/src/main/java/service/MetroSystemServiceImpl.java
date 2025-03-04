package service;

import java.util.ArrayList;
import entity.Card;
import entity.Station;
import entity.Transaction;
import exceptions.*;
import persistence.*;

public class MetroSystemServiceImpl implements MetroSystemService {
    CardDao card = new CardDaoImpl();
    StationDao station = new StationDaoImpl();
    TransactionDao transaction= new TransactionDaoImpl();
    
    @Override
    public ArrayList<Station> getStationList() {
        return station.getStationList();
    }

    @Override
    public int issueCard(double amount) throws LowAmountException {
        return card.issueCard(amount);
    }

    @Override
    public Card getCardDetailsById(int id) throws WrongCardNoException {
        return card.getCardDetailsById(id);
    }

    @Override
    public void checkCardExist(int cardId) throws WrongCardNoException {
        card.checkCardExist(cardId);
    }

    @Override
    public void checkStationExist(int stationId) throws MetroStationDoNotExistException {
        station.checkStationExist(stationId);
    }

    @Override
    public void swipeIn(int cardId, int stationId) throws InsufficientBalanceException {
        card.swipeIn(cardId, stationId);
    }

    @Override
    public void addCardBalance(int cardId, double amount) {
        card.addCardBalance(cardId, amount);
    }

    @Override
    public double swipeOut(int cardId, int stationId) throws NotSwipedInException, InsufficientBalanceException, WrongCardNoException, MetroStationDoNotExistException {
        Card userCard = getCardDetailsById(cardId);

        if (userCard.getSwipeInStation()==null) {
            throw new NotSwipedInException("Swipe-in record not found! Please swipe in before swiping out.");
        }

        int startStationId = userCard.getSwipeInStationId();
        
        int distance = Math.abs(stationId - startStationId);
        double fare = distance * 5; 

        if (userCard.getBalance()< fare) {
            throw new InsufficientBalanceException("Insufficient balance to deduct fare.");
        }
        
        transaction.insertTransactionRecord(cardId, startStationId, stationId, fare);
        
        card.swipeOut(cardId, stationId, fare);

        return fare;
    }
    
    @Override
    public ArrayList<Transaction> getTransactionRecord(int cardId){
    	return transaction.getUserTransactionRecord(cardId);
    }

}
