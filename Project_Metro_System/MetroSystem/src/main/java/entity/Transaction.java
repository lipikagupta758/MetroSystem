package entity;

import java.sql.Timestamp;

public class Transaction {
	private int transId;
	private Card card;
	private double fare;
	private Station swipeInStation;
	private Station swipeOutStation;
	private Timestamp transactionTimeStamp;
	
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
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
	public Timestamp getTransactionTimeStamp() {
		return transactionTimeStamp;
	}
	public void setTransactionTimeStamp(Timestamp transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}
	@Override
	public String toString() {
		return "Id: "+ transId+ ", Fare: "+ fare+ ", TimeStamp: "+ transactionTimeStamp;
	}
	
	
}
