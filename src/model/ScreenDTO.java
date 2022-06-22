package model;

//극장별 상영관정보클래스 상영관hashcode,극장hashcode,좌석배치유형,좌석별예매여부,잔여좌석수 등
public class ScreenDTO {
	private int id;
	private int theaterId;
	private int screenType;
	private int seatTotal;
	
	public ScreenDTO() {}
	public ScreenDTO(int id) {
		this.id=id;
	}
	public ScreenDTO(ScreenDTO s) {
		this.id=s.id;
		this.theaterId=s.theaterId;
		this.screenType=s.screenType;
		this.seatTotal=s.seatTotal;
	}
	public boolean equals(Object o) {
		if(o instanceof ScreenDTO) {
			return this.id==((ScreenDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getTheaterId() {
		return theaterId;
	}
	public int getScreenType() {
		return screenType;
	}
	public int getSeatTotal() {
		return seatTotal;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}
	public void setScreenType(int screenType) {
		this.screenType = screenType;
	}
	public void setSeatTotal(int seatTotal) {
		this.seatTotal = seatTotal;
	}
	
}
