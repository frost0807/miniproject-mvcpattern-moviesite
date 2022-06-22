package controller;

import java.util.ArrayList;

import model.PlayInfoDTO;

public class PlayInfoController {
	private int nextId;
	private ArrayList<PlayInfoDTO> pList;
	
	public PlayInfoController() {
		nextId=1;
		pList=new ArrayList<>();
	}
	public ArrayList<PlayInfoDTO> selectAll(){
		ArrayList<PlayInfoDTO> temp=new ArrayList<>();
		
		for(PlayInfoDTO p:pList) {
			temp.add(p);
		}
		return temp;
	}
	public PlayInfoDTO selectOne(int id) {
		PlayInfoDTO p=new PlayInfoDTO(id);
		if(pList.contains(p)) {
			return new PlayInfoDTO(pList.get(pList.indexOf(p)));
		} else {
			return null;
		}
	}
	public ArrayList<PlayInfoDTO> selectAllByScreenId(int screenId) {
		ArrayList<PlayInfoDTO> temp=new ArrayList<>();
		
		for(PlayInfoDTO p:pList) {
			if(p.getScreenId()==screenId) {
				temp.add(p);
			}
		}
		return temp;
	}
	public void insert(PlayInfoDTO p) {
		p.setId(nextId++);
		pList.add(p);
	}
	public void delete(int id) {
		pList.remove(new PlayInfoDTO(id));
	}
	public void update(PlayInfoDTO p) {
		pList.set(pList.indexOf(p), p);
	}
}
