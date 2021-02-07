package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;

public interface ReservationService {
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start);
	public List<PromotionWithCategoryAndProductAndProductImage> getPromotions();
}
