package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Category> selectCategory() {
		RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
		return jdbc.query(SELECT_ALL_CATEGORY, Collections.emptyMap(), rowMapper);
	}

	public Integer countCategoryItems(int categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		return jdbc.queryForObject(COUNT_CATEGORY_ITEM, params, Integer.class);
	}

	public List<ProductWithDisplayInfoAndCategory> selectDisplayInfos(int categoryId, int start) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper
				.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		if (categoryId != 0) {
			params.put("category_id", categoryId);
			return jdbc.query(SELECT_DISPLAY_INFO_BY_CATEGORY_ID, params, rowMapper);
		}
		return jdbc.query(SELECT_ALL_DISPLAY_INFO, params, rowMapper);
	}

	public List<PromotionWithCategoryAndProductAndProductImage> getPromotionWithCategoryAndProductAndProductImage() {
		RowMapper<PromotionWithCategoryAndProductAndProductImage> rowMapper = BeanPropertyRowMapper
				.newInstance(PromotionWithCategoryAndProductAndProductImage.class);
		return jdbc.query(SELECT_ALL_PROMOTION, new HashMap<String, Object>(), rowMapper);
	}

	public ProductWithDisplayInfoAndCategory selectDisplayInfo(int display_id) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper
				.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", display_id);
		return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_ID, params, rowMapper);
	}

	public int getProductIdByDisplayID(int displayId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", displayId);
		return jdbc.queryForObject(SELECT_PRODUCT_ID_BY_DISPLAY_ID, params, Integer.class);
	}

	public List<ProductImageWithFileInfo> getProductImageWithFileInfos(int productId) {
		RowMapper<ProductImageWithFileInfo> rowMapper = BeanPropertyRowMapper
				.newInstance(ProductImageWithFileInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.query(SELECT_PRODUCT_IMAGES_WHTH_FILE_INFO, params, rowMapper);
	}

	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImages(int displayId) {
		RowMapper<DisplayInfoImageWithFileInfo> rowMapper = BeanPropertyRowMapper
				.newInstance(DisplayInfoImageWithFileInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", displayId);
		return jdbc.query(SELECT_DISPLAY_INFO_IMAGES, params, rowMapper);
	}

	public float getProductAvgScore(int productId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.queryForObject(SELECT_DISPLAY_AVG_SCORE, params, Float.class);
	}

	public List<ProductPrice> getProductPrices(int productId) {
		RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.query(SELECT_PRODUCT_PRICE_BY_PRODUCT_ID, params, rowMapper);
	}

	public List<ReservationUserComment> getComments(int productId) {
		RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.query(SELECT_RESERVATION_USER_COMMENTS_BY_PRODUCT_ID, params, rowMapper);
	}

	public void postReservationInfo(ReservationRegistration reservationRegistration) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", reservationRegistration.getProductId());
		params.put("display_info_id", reservationRegistration.getDisplayInfoId());
		params.put("user_id", reservationRegistration.getUserId());
		params.put("reservation_date", reservationRegistration.getReservationYearMonthDay());
		jdbc.update(INSERT_RESERVATION_INFO, params);
	}

	public int getReservationInfoId(ReservationRegistration reservationRegistration) {
		Map<String, Object> params = new HashMap<>();
		params.put("user_id", reservationRegistration.getUserId());
		params.put("reservation_date", reservationRegistration.getReservationYearMonthDay());
		return jdbc.queryForObject(SELECT_RESERVATION_INFO_ID_BY_USER_ID_WITH_RESERVATION_DATE, params, Integer.class);
	}

	public void postReservationInfoPrice(int id, ReservationRegistration reservationRegistration) {
		Map<String, Object> params = new HashMap<>();
		params.put("reservation_info_id", id);
		for (ReservationInfoPrice price : reservationRegistration.getPrices())
		{
			params.put("product_price_id", price.getProductPriceId());
			params.put("count", price.getCount());
			jdbc.update(INSERT_RESERVATION_INFO_PRICE, params);
		}
	}

	public ReservationInfo getReservationInfo(int id) {
		RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_ID, params, rowMapper);
	}

	public List<ReservationInfoPrice> getReservatioinInfoPrice(int id) {
		RowMapper<ReservationInfoPrice> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfoPrice.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("reservation_info_id", id);
		return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_ID, params, rowMapper);
	}

	public List<ReservationInfo> getReservationInfoList(int userId) {
		RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("user_id", userId);
		return jdbc.query(SELECT_RESERVATION_INFO_LIST_BY_USER_ID, params, rowMapper);
	}

	public Product getProductByProductId(int productId) {
		RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("id", productId);
		return jdbc.queryForObject(SELECT_PRODUCT_BY_PRODUCT_ID, params, rowMapper);
	}

	public int getSumPrice(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservation_info_id", reservationInfoId);
		return jdbc.queryForObject(SELECT_SUM_PRICE_BY_RESERVATION_ID, params,Integer.class);
	}

	public int getReservationInfosCancelFlag(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservation_info_id", reservationInfoId);
		return jdbc.queryForObject(SELECT_CANCEL_FLAG_BY_RESERVATION_INFO_ID, params,Integer.class);
	}

	public void putReservationInfos(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservation_info_id", reservationInfoId);
		jdbc.update(UPDATE_CANCEL_FLAG_BY_RESERVATION_INFO_ID, params);
	}
}
