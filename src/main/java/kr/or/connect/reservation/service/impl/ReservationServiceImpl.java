package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	ReservationDao reservationDao;

	public ReservationServiceImpl(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	@Override
	@Transactional
	public List<Category> getCategories() {
		List<Category> list = reservationDao.selectCategory();
		for (Category category : list) {
			category.setCount(reservationDao.countCategoryItems(category.getId()));
		}
		return list;
	}

	@Override
	@Transactional
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start) {
		List<ProductWithDisplayInfoAndCategory> list = reservationDao.selectDisplayInfo(categoryId, start);
		for (ProductWithDisplayInfoAndCategory product : list) {
			System.out.println(product);
		}
		return list;
	}

	@Override
	@Transactional
	public List<PromotionWithCategoryAndProductAndProductImage> getPromotions() {
		return reservationDao.getPromotionWithCategoryAndProductAndProductImage();
	}
	
}
