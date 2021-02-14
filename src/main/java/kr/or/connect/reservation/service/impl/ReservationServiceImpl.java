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
import kr.or.connect.reservation.dto.Product;
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
	private static final int CANCELED = 1;
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
		return reservationDao.selectDisplayInfos(categoryId, start);
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

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getReservationInfos(int userId) {
		Map<String, Object> map = new HashMap<>();
		List<ReservationInfo> reservationInfoList = reservationDao.getReservationInfoList(userId);
		map.put("size", reservationInfoList.size());
		List<Map<String, Object>> items = new ArrayList<>();
		for (ReservationInfo reservationInfo : reservationInfoList) {
			Map<String, Object> item = new HashMap<>();
			item.put("id", reservationInfo.getId());
			item.put("productId", reservationInfo.getProductId());
			item.put("displayInfoId", reservationInfo.getDisplayInfoId());
			item.put("cancelFlag", reservationInfo.getCancelFlag());
			Product product = reservationDao.getProductByProductId(reservationInfo.getProductId());
			item.put("productDescription", product.getDescription());
			item.put("productContent", product.getContent());
			item.put("userId", userId);
			item.put("sumPrice", reservationDao.getSumPrice(reservationInfo.getId()));
			item.put("reservationDate", reservationInfo.getReservationDate());
			item.put("createDate", reservationInfo.getCreateDate());
			item.put("modifyDate", reservationInfo.getModifyDate());
			items.add(item);
		}
		map.put("items", items);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> putReservationInfos(int reservationInfoId) {
		Map<String, Object> map = new HashMap<>();
		int cancelFlag = reservationDao.getReservationInfosCancelFlag(reservationInfoId);
		if (cancelFlag == 1) {
			map.put("result", "fail - already canceled");
			return map;
		}
		reservationDao.putReservationInfos(reservationInfoId);
		cancelFlag = reservationDao.getReservationInfosCancelFlag(reservationInfoId);
		if (cancelFlag == CANCELED) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationInfo getReservationInfoByUserIdWithReservationInfoId(int userId, int reservationInfoId) {
		return reservationDao.getReservationInfoByUserIdWithReservationInfoId(userId, reservationInfoId);
	}

	@Override
	@Transactional
	public boolean postComment(ReservationInfo reservationInfo, int score, String comment, int fileId,
			String create_date) {
		reservationDao.postReservationUserComment(reservationInfo, score, comment, create_date);
		int reservationUserCommentId = reservationDao.getReservationUserComment(reservationInfo.getId(), create_date);
		if (reservationUserCommentId == 0) {
			return false;
		}
		reservationDao.postReservationUserCommentImage(reservationInfo.getId(), reservationUserCommentId, fileId);
		int reservationUserCommentImageId = reservationDao.getReservationUserCommentImageId(reservationInfo.getId(),
				reservationUserCommentId, fileId);
		if (reservationUserCommentImageId == 0) {
			return false;
		}
		return true;
	}
}
