package controller;

import model.MovieDTO;
import java.util.ArrayList;

public class MovieController {
	private int nextId;
	private ArrayList<MovieDTO> mList;
	
	public MovieController() {
		nextId=1;
		mList=new ArrayList<>();
	}
	public ArrayList<MovieDTO> selectAll() {
		ArrayList<MovieDTO> temp=new ArrayList<>();
		
		for(MovieDTO m:mList) {
			temp.add(m);
		}
		return temp;
	}
	public MovieDTO selectOne(int id) {
		MovieDTO m=new MovieDTO(id);
		if(mList.contains(m)) {
			return new MovieDTO(mList.get(mList.indexOf(m)));
		} else {
			return null;
		}
	}
	public void insert(MovieDTO m) {
		m.setId(nextId++);
		mList.add(m);
	}
	public void delete(int id) {
		mList.remove(new MovieDTO(id));
	}
	public void update(MovieDTO m) {
		mList.set(mList.indexOf(m), m);
	}
}