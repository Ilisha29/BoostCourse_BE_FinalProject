package kr.or.connect.reservation.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationRegistration;
import kr.or.connect.reservation.dto.ReservationUserComment;

public interface ReservationService {
	public List<Category> getCategories();
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start);
	public List<PromotionWithCategoryAndProductAndProductImage> getPromotions();
	public ProductWithDisplayInfoAndCategory getProduct(int display_id);
	public List<ProductImageWithFileInfo> getProductImages(int display_id);
	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImages(int display_id);
	public int getProductAvgScore(int display_id);
	public List<ProductPrice> getProductPrices(int display_id);
	public List<ReservationUserComment> getComments(int categoryId);
	public List<ReservationUserComment> getCommentsApplyStart(List<ReservationUserComment> reservationUserComments, int start);
	public Map<String, Object> postReservation(ReservationRegistration reservationRegistration);
	public Map<String, Object> getReservationInfos(int userId);
	public Map<String, Object> putReservationInfos(int reservationInfoId);
	public ReservationInfo getReservationInfoByUserIdWithReservationInfoId(int userId, int reservationInfoId);
	public boolean postComment(ReservationInfo reservationInfo, int score, String comment, int fileId, String create_date);
}
