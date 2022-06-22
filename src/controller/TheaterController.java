package controller;

import model.ReviewDTO;
import model.TheaterDTO;
import java.util.ArrayList;

public class TheaterController {
	private int nextId;
	private ArrayList<TheaterDTO> tList;
	
	public TheaterController() {
		nextId=1;
		tList=new ArrayList<>();
	}
	public ArrayList<TheaterDTO> selectAll(){
		ArrayList<TheaterDTO> temp=new ArrayList<>();
		
		for(TheaterDTO t:tList) {
			temp.add(t);
		}
		return temp;
	}
	public TheaterDTO selectOne(int id) {
		TheaterDTO t=new TheaterDTO(id);
		if(tList.contains(t)) {
			return new TheaterDTO(tList.get(tList.indexOf(t)));
		} else {
			return null;
		}
	}
	public void insert(TheaterDTO t) {
		t.setId(nextId++);
		tList.add(t);
	}
	public void delete(int id) {
		tList.remove(new TheaterDTO(id));
	}
	public void update(TheaterDTO t) {
		tList.set(tList.indexOf(t), t);
	}
}
