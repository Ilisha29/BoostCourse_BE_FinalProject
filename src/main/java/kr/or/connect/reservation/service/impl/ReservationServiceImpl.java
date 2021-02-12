package kr.or.connect.reservation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationRegistration;
import kr.or.connect.reservation.dto.ReservationUserComment;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	private static final int LIMIT = 5;
	ReservationDao reservationDao;

	public ReservationServiceImpl(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getCategories() {
		List<Category> list = reservationDao.selectCategory();
		for (Category category : list) {
			category.setCount(reservationDao.countCategoryItems(category.getId()));
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start) {
		List<ProductWithDisplayInfoAndCategory> list = reservationDao.selectDisplayInfos(categoryId, start);
		for (ProductWithDisplayInfoAndCategory product : list) {
			System.out.println(product);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PromotionWithCategoryAndProductAndProductImage> getPromotions() {
		return reservationDao.getPromotionWithCategoryAndProductAndProductImage();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductWithDisplayInfoAndCategory getProduct(int displayId) {
		return reservationDao.selectDisplayInfo(displayId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductImageWithFileInfo> getProductImages(int displayId) {
		int productId = reservationDao.getProductIdByDisplayID(displayId);
		return reservationDao.getProductImageWithFileInfos(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImages(int displayId) {
		return reservationDao.getDisplayInfoImages(displayId);
	}

	@Override
	@Transactional(readOnly = true)
	public int getProductAvgScore(int displayId) {
		int productId = reservationDao.getProductIdByDisplayID(displayId);
		return (int) reservationDao.getProductAvgScore(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductPrice> getProductPrices(int displayId) {
		int productId = reservationDao.getProductIdByDisplayID(displayId);
		return reservationDao.getProductPrices(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservationUserComment> getComments(int productId) {
		return reservationDao.getComments(productId);
	}

	@Override
	public List<ReservationUserComment> getCommentsApplyStart(List<ReservationUserComment> reservationUserComments,
			int start) {
		int count = 0;
		List<ReservationUserComment> appliedStartList = new ArrayList<>();
		for (int i = start; i < reservationUserComments.size(); i++) {
			if (count == LIMIT)
				break;
			appliedStartList.add(reservationUserComments.get(i));
			count++;
		}
		return appliedStartList;
	}

	@Override
	@Transactional
	public Map<String, Object> postReservation(ReservationRegistration reservationRegistration) {
		Map<String, Object> map = new HashMap<>();
		reservationDao.postReservationInfo(reservationRegistration);
		int id = reservationDao.getReservationInfoId(reservationRegistration);
		System.out.println("IDIDIDIDDIIDIIDIDIDID : "+id);
		reservationDao.postReservationInfoPrice(id, reservationRegistration);
		ReservationInfo reservationInfo = reservationDao.getReservationInfo(id);
		List<ReservationInfoPrice> reservationInfoPrice = reservationDao.getReservatioinInfoPrice(id);
		map.put("id", id);
		map.put("productId", reservationInfo.getProductId());
		map.put("cancelFlag", reservationInfo.getCancelFlag());
		map.put("displayInfoId", reservationInfo.getDisplayInfoId());
		map.put("userId", reservationInfo.getUserId());
		map.put("reservationDate", reservationInfo.getReservationDate());
		map.put("createDate", reservationInfo.getCreateDate());
		map.put("modifyDate", reservationInfo.getModifyDate());
		map.put("prices", reservationInfoPrice);
		return map;
	}
}
