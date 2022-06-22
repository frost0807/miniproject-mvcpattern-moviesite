package viewer;

import java.util.Scanner;
import model.ReviewDTO;
import model.UserDTO;
import util.ScUtil;
import controller.ReviewController;

public class ReviewViewer {
	private Scanner sc;
	private ReviewController reviewController;
	private UserViewer userViewer;
	private UserDTO logInInfo;
	
	public ReviewViewer(Scanner sc) {
		reviewController=new ReviewController();
		this.sc=sc;
	}
	public void setLogIn(UserDTO logInInfo) {
		this.logInInfo=logInInfo;
	}
	public void setUserViewer(UserViewer userViewer) {
		this.userViewer=userViewer;
	}
	public void addReview(int movieId) {
		ReviewDTO temp=new ReviewDTO();
		int tempScore=ScUtil.nextInt(sc, "평점으로 등록할 점수를 입력해주세요(1~5)");
		
		temp.setMovieId(movieId);
		temp.setScore(tempScore);
		temp.setUserId(logInInfo.getId());
		
		if(logInInfo.getGrade()==2) {
			String tempComment=ScUtil.nextLine(sc, "평론으로 등록할 내용을 입력해주세요");
			temp.setComment(tempComment);
		}
		if(reviewController.selectOneByUserIdAndMovieId(logInInfo.getId(),movieId)==null) {
			reviewController.insert(temp);
			System.out.println("등록이 완료되었습니다.");
		} else {
			System.out.println("이미 등록하셨습니다.");
		}
	}
	//영화hashcode를 받아서 해당영화에 해당하는 전체평점,전문가평점,일반관람객평점
	public void printAverageScoreOfAll(int movieId) {
		System.out.print(reviewController.getAverageScoreOfAll(movieId));
	}
	public void printAverageScoreOfPublic(int movieId) {
		System.out.print(reviewController.getAverageScoreOfPublic(movieId));
	}
	public void printAverageScoreOfCritic(int movieId) {
		System.out.print(reviewController.getAverageScoreOfCritic(movieId));
	}
	public void printReview(int movieId) {
		for(ReviewDTO r:reviewController.selectAll()) {
			if(r.getMovieId()==movieId&&r.getComment()!=null) {
				System.out.println("-------------------------------------------------");
				System.out.print("리뷰코드:"+r.getId());
				System.out.print(" 리뷰작성자:");
				userViewer.printNickNameFromId(r.getUserId());
				System.out.println(" 평점:"+r.getScore());
				System.out.println("리뷰내용:"+r.getComment());
				System.out.println("-------------------------------------------------");
			}
		}
	}
	public void deleteReview(int movieId) {
		ReviewDTO temp=reviewController.selectOneByUserIdAndMovieId(logInInfo.getId(), movieId);
		if(temp!=null) {
			String yOrN=ScUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
			if(yOrN.equalsIgnoreCase("y")) {
				reviewController.delete(temp.getId());
				System.out.println("삭제가 완료되었습니다.");
			}
		} else {
			System.out.println("작성한 리뷰가 없습니다.");
		}
	}
	public void updateReview(int movieId) {
		ReviewDTO temp=reviewController.selectOneByUserIdAndMovieId(logInInfo.getId(), movieId);
		if(temp!=null) {
			ReviewDTO r=reviewController.selectOne(temp.getId());
			
			r.setScore(ScUtil.nextInt(sc, "평점으로 등록할 점수를 입력해주세요(1~5)"));
			
			if(logInInfo.getGrade()==2) {
				r.setComment(ScUtil.nextLine(sc, "평론으로 등록할 내용을 입력해주세요"));
			}
			reviewController.update(r);
			System.out.println("수정이 완료되었습니다.");
		} else {
			System.out.println("작성한 리뷰가 없습니다.");
		}
	}
}
