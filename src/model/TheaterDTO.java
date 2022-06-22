package model;

//극장클래스 극장hashcode,극장이름,극장전화번호
public class TheaterDTO {
	private int id;
	private String name;
	private String phoneNumber;
	private String branch;
	
	public TheaterDTO() {}
	public TheaterDTO(int id) {
		this.id=id;
	}
	public TheaterDTO(TheaterDTO t) {
		this.id=t.id;
		this.name=t.name;
		this.phoneNumber=t.phoneNumber;
		this.branch=t.branch;
	}
	public boolean equals(Object o) {
		if(o instanceof TheaterDTO) {
			return this.id==((TheaterDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getBranch() {
		return branch;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}
