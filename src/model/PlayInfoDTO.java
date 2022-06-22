package model;

public class PlayInfoDTO {
	private int id;
	private int movieId;
	private int theaterId;
	private int screenId;
	private int seatLeft;
	//ex) 22/06/22 09:30-11:30
	private String startTime;
	
	public PlayInfoDTO() {}
	public PlayInfoDTO(int id) {
		this.id=id;
	}
	public PlayInfoDTO(PlayInfoDTO p) {
		this.id=p.id;
		this.movieId=p.movieId;
		this.theaterId=p.theaterId;
		this.startTime=p.startTime;
		this.screenId=p.screenId;
		this.seatLeft=p.seatLeft;
	}
	public boolean equals(Object o) {
		if(o instanceof PlayInfoDTO) {
			return this.id==((PlayInfoDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getMovieId() {
		return movieId;
	}
	public int getTheaterId() {
		return theaterId;
	}
	public int getScreenId() {
		return screenId;
	}
	public int getSeatLeft() {
		return seatLeft;
	}
	public String getstartTime() {
		return startTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}
	public void setScreenId(int screenId) {
		this.screenId=screenId;
	}
	public void setSeatLeft(int seatLeft) {
		this.seatLeft=seatLeft;
	}
	public void setstartTime(String startTime) {
		this.startTime = startTime;
	}
}
