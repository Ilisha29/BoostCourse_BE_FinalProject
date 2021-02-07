package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;

public interface ReservationService {
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start);
}
