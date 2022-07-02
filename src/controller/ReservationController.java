package controller;

import java.util.ArrayList;
import model.ReservationDTO;

public class ReservationController {
	private int nextId;
	private ArrayList<ReservationDTO> rvList;

	public ReservationController() {
		nextId=1;
		rvList=new ArrayList<>();
	}
	
	public ArrayList<ReservationDTO> selectAll(){
		ArrayList<ReservationDTO> temp=new ArrayList<>();
		
		for(ReservationDTO r:rvList) {
			temp.add(r);
		}
		return temp;
	}
	public ReservationDTO selectOne(int id) {
		ReservationDTO r=new ReservationDTO(id);
		if(rvList.contains(r)) {
			return new ReservationDTO(rvList.get(rvList.indexOf(r)));
		} else {
			return null;
		}
	}
	public void insert(ReservationDTO r) {
		r.setId(nextId++);
		rvList.add(r);
	}
	public void delete(int id) {
		rvList.remove(new ReservationDTO(id));
	}
	public void update(ReservationDTO r) {
		rvList.set(rvList.indexOf(r), r);
	}
}
