package controller;

import java.util.ArrayList;
import model.UserDTO;

public class UserController {
	private ArrayList<UserDTO> uList;
	private int nextId;
	
	public UserController() {
		uList=new ArrayList<>();
		nextId=1;
	}
	public UserDTO selectOne(int id) {
		UserDTO temp=new UserDTO(id);
		if(uList.contains(temp)) {
			return new UserDTO(uList.get(uList.indexOf(temp)));
		} else {
			return null;
		}
	}
	public ArrayList<Integer> getPublicIdList(){
		ArrayList<Integer> temp=new ArrayList<>();
		for(UserDTO u:uList) {
			if(u.getGrade()==1) {
				temp.add(u.getId());
			}
		}
		return temp;
	}
	public ArrayList<Integer> getCriticIdList(){
		ArrayList<Integer> temp=new ArrayList<>();
		for(UserDTO u:uList) {
			if(u.getGrade()==2) {
				temp.add(u.getId());
			}
		}
		return temp;
	}
	public void insert(UserDTO u) {
		u.setId(nextId++);
		uList.add(u);
	}
	public void delete(int userId) {
		uList.remove(new UserDTO(userId));
	}
	public void update(UserDTO u) {
		uList.set(uList.indexOf(u), u);
	}
	public boolean checkValidId(String tempUserName) {
		for(UserDTO u:uList) {
			if(u.getUsername().equalsIgnoreCase(tempUserName)) {
				return true;
			}
		}
		return false;
	}
	public UserDTO auth(String tempUserName, String tempPassword) {
		for(UserDTO u:uList) {
			if(u.getUsername().equalsIgnoreCase(tempUserName)
					&&u.getPassword().equals(tempPassword)) {
				return new UserDTO(u);
			}
		}
		return null;
	}
}
