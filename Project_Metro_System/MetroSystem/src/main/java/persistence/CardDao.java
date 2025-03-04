package persistence;

import entity.Card;
import exceptions.*;

public interface CardDao {
    int issueCard(double amount) throws LowAmountException;
    void addCardBalance(int cardId, double amount);
    Card getCardDetailsById(int cardId) throws WrongCardNoException;
    void swipeOut(int cardId, int stationId, double fare) throws NotSwipedInException, InsufficientBalanceException, WrongCardNoException;
    void swipeIn(int cardId, int stationId) throws InsufficientBalanceException;
    void checkCardExist(int cardId) throws WrongCardNoException;
}
