package kr.or.connect.reservation.dto;

import java.util.List;

public class ReservationRegistration {
	private List<ReservationInfoPrice> prices;
	private int productId;
	private int displayInfoId;
	private String reservationYearMonthDay;
	private int userId;
	public List<ReservationInfoPrice> getPrices() {
		return prices;
	}
	public void setPrices(List<ReservationInfoPrice> prices) {
		this.prices = prices;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public String getReservationYearMonthDay() {
		return reservationYearMonthDay;
	}
	public void setReservationYearMonthDay(String reservationYearMonthDay) {
		this.reservationYearMonthDay = reservationYearMonthDay;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ReservationRegistration [prices=" + prices + ", productId=" + productId + ", displayInfoId="
				+ displayInfoId + ", reservationYearMonthDay=" + reservationYearMonthDay + ", userId=" + userId + "]";
	}
}
