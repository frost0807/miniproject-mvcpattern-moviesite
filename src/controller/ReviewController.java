package controller;

import model.MovieDTO;
import model.ReviewDTO;
import java.util.ArrayList;

public class ReviewController {
	private int nextId;
	private ArrayList<ReviewDTO> rList;
	
	public ReviewController(){
		nextId=1;
		rList=new ArrayList<>();
	}
	public ArrayList<ReviewDTO> selectAll(){
		ArrayList<ReviewDTO> temp=new ArrayList<>();
		
		for(ReviewDTO r:rList) {
			temp.add(r);
		}
		return temp;
	}
	public ReviewDTO selectOne(int id) {
		ReviewDTO r=new ReviewDTO(id);
		if(rList.contains(r)) {
			return new ReviewDTO(rList.get(rList.indexOf(r)));
		} else {
			return null;
		}
	}
	public ReviewDTO selectOneByUserIdAndMovieId(int userId, int movieId) {
		for(ReviewDTO r:rList) {
			if(r.getUserId()==userId&&r.getMovieId()==movieId) {
				return new ReviewDTO(r);
			}
		}
		return null;
	}
	public double getAverageScoreOfAll(int movieId) {
		double result=0.0;
		int count=0;
		
		for(ReviewDTO r:rList) {
			if(r.getMovieId()==movieId) {
				result+=r.getScore();
				count++;
			}
		}
		if(count>0) {
			return result/count;
		} else {
			return 0.0;
		}
	}
	public double getAverageScoreOfPublic(int movieId) {
		double result=0.0;
		int count=0;
		
		for(ReviewDTO r:rList) {
			if(r.getMovieId()==movieId&&r.getComment()==null) {
				result+=r.getScore();
				count++;
			}
		}
		if(count>0) {
			return result/count;
		} else {
			return 0.0;
		}
	}
	public double getAverageScoreOfCritic(int movieId) {
		double result=0.0;
		int count=0;
		
		for(ReviewDTO r:rList) {
			if(r.getMovieId()==movieId&&r.getComment()!=null) {
				result+=r.getScore();
				count++;
			}
		}
		if(count>0) {
			return result/count;
		} else {
			return 0.0;
		}
	}
	public void insert(ReviewDTO r) {
		r.setId(nextId++);
		rList.add(r);
	}
	public void delete(int id) {
		rList.remove(new ReviewDTO(id));
	}
	public void update(ReviewDTO r) {
		rList.set(rList.indexOf(r), r);
	}
}
