package model;

//평점클래스 평점hashcode,작성회원hashcode,영화hashcode,평점,평론
//전문평론가 등급만 평론+평점 작성가능하고 일반회원은 평점만 등록가능
public class ReviewDTO {
	private int id;
	private int userId;
	private int movieId;
	private int score;
	private String comment;
	
	public ReviewDTO(){}
	public ReviewDTO(int id) {
		this.id=id;
	}
	public ReviewDTO(ReviewDTO r) {
		this.id=r.id;
		this.userId=r.userId;
		this.movieId=r.movieId;
		this.score=r.score;
		this.comment=r.comment;
	}
	public boolean equals(Object o) {
		if(o instanceof ReviewDTO) {
			return this.id==((ReviewDTO)o).id;
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
	public int getMovieId() {
		return movieId;
	}
	public int getScore() {
		return score;
	}
	public String getComment() {
		return comment;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
