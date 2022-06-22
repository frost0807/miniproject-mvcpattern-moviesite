package model;

//영화정보클래스 영화hashcode,영화제목,영화줄거리,영화등급
public class MovieDTO {
	private int id;
	private int filmrate;
	private String title;
	private String synopsis;
	
	public MovieDTO(){}
	public MovieDTO(int id){
		this.id=id;
	}
	public MovieDTO(MovieDTO m){
		this.id=m.id;
		this.filmrate=m.filmrate;
		this.title=m.title;
		this.synopsis=m.synopsis;
	}
	public boolean equals(Object o) {
		if(o instanceof MovieDTO) {
			return this.id==((MovieDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getFilmrate() {
		return filmrate;
	}
	public String getTitle() {
		return title;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFilmrate(int filmrate) {
		this.filmrate = filmrate;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
}
