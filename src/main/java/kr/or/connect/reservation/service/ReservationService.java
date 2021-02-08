package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
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
}
