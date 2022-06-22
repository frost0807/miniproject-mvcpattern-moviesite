package model;

//회원클래스 회원hashcode,아이디,비밀번호,닉네임,등급,(관람한 영화 리스트)
public class UserDTO {
	private int id;
	private int grade;
	private String username;
	private String password;
	private String nickname;
	
	public UserDTO(){}
	public UserDTO(int id){
		this.id=id;
	}
	public UserDTO(UserDTO u){
		this.id=u.id;
		this.grade=u.grade;
		this.username=u.username;
		this.password=u.password;
		this.nickname=u.nickname;
	}
	public boolean equals(Object o) {
		if(o instanceof UserDTO) {
			return this.id==((UserDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getGrade() {
		return grade;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
