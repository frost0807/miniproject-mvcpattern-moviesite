package model;
//예매 관련 정보를 저장하는 model
public class ReservationDTO {
	private int id;
	private int userId;
	private int playInfoId;
	
	public ReservationDTO(){}
	public ReservationDTO(int id){
		this.id=id;
	}
	public ReservationDTO(ReservationDTO r){
		this.id=r.id;
		this.userId=r.userId;
		this.playInfoId=r.playInfoId;
	}
	public boolean equals(Object o) {
		if(o instanceof ReservationDTO) {
			return this.id==((ReservationDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public int getPlayInfoId() {
		return playInfoId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setPlayInfoId(int playInfoId) {
		this.playInfoId = playInfoId;
	}
	
}
