package controller;

import java.util.ArrayList;
import model.ScreenDTO;

public class ScreenController {
	private int nextId;
	private ArrayList<ScreenDTO> sList;
	
	public ScreenController() {
		nextId=1;
		sList=new ArrayList<>();
	}
	public ArrayList<ScreenDTO> selectAll(){
		ArrayList<ScreenDTO> temp=new ArrayList<>();
		
		for(ScreenDTO s:sList) {
			temp.add(s);
		}
		return temp;
	}
	public ScreenDTO selectOne(int id) {
		ScreenDTO s=new ScreenDTO(id);
		if(sList.contains(s)) {
			return new ScreenDTO(sList.get(sList.indexOf(s)));
		} else {
			return null;
		}
	}
	public void insert(ScreenDTO s) {
		s.setId(nextId++);
		sList.add(s);
	}
	public void delete(int id) {
		sList.remove(new ScreenDTO(id));
	}
	public void update(ScreenDTO s) {
		sList.set(sList.indexOf(s), s);
	}
	public String screenTypeString(int screenType) {
		if(screenType==1) {
			return "2D";
		} else if(screenType==2) {
			return "3D";
		} else if(screenType==3) {
			return "4DX";
		} else if(screenType==4) {
			return "IMAX";
		} else {
			return null;
		}
	}
}
