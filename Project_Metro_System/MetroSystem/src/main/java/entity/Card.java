package entity;

import java.util.Date;

public class Card {
    private int cardId;
    private double balance;
    private Date issueDate;
    private Station swipeInStation;
    private Station swipeOutStation;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Station getSwipeInStation() {
        return swipeInStation;
    }

    public void setSwipeInStation(Station swipeInStation) {
        this.swipeInStation = swipeInStation;
    }

    public Station getSwipeOutStation() {
        return swipeOutStation;
    }

    public void setSwipeOutStation(Station swipeOutStation) {
        this.swipeOutStation = swipeOutStation;
    }

    public int getSwipeInStationId() {
        return (swipeInStation != null) ? swipeInStation.getStationId() : -1; 
    }
}