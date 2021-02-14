package kr.or.connect.reservation.dto;

import java.util.List;

public class ReservationUserCommentWithImage {
	private int id;
	private int productId;
	private int reservationInfoId;
	private int score;
	private int userId;
	private String comment;
	private List<ReservationUserCommentImage> reservationUserCommentImages;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<ReservationUserCommentImage> getReservationUserCommentImages() {
		return reservationUserCommentImages;
	}
	public void setReservationUserCommentImages(List<ReservationUserCommentImage> reservationUserCommentImages) {
		this.reservationUserCommentImages = reservationUserCommentImages;
	}
	@Override
	public String toString() {
		return "ReservationUserCommentWithImage [id=" + id + ", productId=" + productId + ", reservationInfoId="
				+ reservationInfoId + ", score=" + score + ", userId=" + userId + ", comment=" + comment
				+ ", reservationUserCommentImages=" + reservationUserCommentImages + "]";
	}
}
