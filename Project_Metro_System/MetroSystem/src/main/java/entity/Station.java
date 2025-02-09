package entity;

public class Station {
	private int stationId;
	private String stationName;
	
	public Station(int id, String name) {
		this.stationId= id;
		this.stationName= name;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	@Override
	public String toString() {
		return "Station No: "+stationId+ ", Station Name: " + stationName;
	}
}
