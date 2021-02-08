package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.COUNT_CATEGORY_ITEM;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_ALL_CATEGORY;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_ALL_DISPLAY_INFO;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_ALL_PROMOTION;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_DISPLAY_AVG_SCORE;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_DISPLAY_INFO_BY_CATEGORY_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_DISPLAY_INFO_BY_DISPLAY_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_DISPLAY_INFO_IMAGES;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCT_ID_BY_DISPLAY_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCT_IMAGES_WHTH_FILE_INFO;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCT_PRICE_BY_PRODUCT_ID;

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
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	
	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Category> selectCategory(){
		RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
		return jdbc.query(SELECT_ALL_CATEGORY, Collections.emptyMap(), rowMapper);
	}
	
	public Integer countCategoryItems(int categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		return jdbc.queryForObject(COUNT_CATEGORY_ITEM, params, Integer.class);
	}

	public List<ProductWithDisplayInfoAndCategory> selectDisplayInfos(int categoryId, int start) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		if (categoryId != 0) {
			params.put("category_id", categoryId);
			return jdbc.query(SELECT_DISPLAY_INFO_BY_CATEGORY_ID, params, rowMapper);
		} 
		return jdbc.query(SELECT_ALL_DISPLAY_INFO, params, rowMapper);
	}

	public List<PromotionWithCategoryAndProductAndProductImage> getPromotionWithCategoryAndProductAndProductImage() {
		RowMapper<PromotionWithCategoryAndProductAndProductImage> rowMapper = BeanPropertyRowMapper.newInstance(PromotionWithCategoryAndProductAndProductImage.class);
		return jdbc.query(SELECT_ALL_PROMOTION, new HashMap<String, Object>(), rowMapper);
	}
	
	public ProductWithDisplayInfoAndCategory selectDisplayInfo(int display_id) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", display_id);
		return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_ID, params, rowMapper);
	}
	
	public int getProductIdByDisplayID(int displayId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", displayId);
		return jdbc.queryForObject(SELECT_PRODUCT_ID_BY_DISPLAY_ID, params, Integer.class);
	}
	
	public List<ProductImageWithFileInfo> getProductImageWithFileInfos(int productId){
		RowMapper<ProductImageWithFileInfo> rowMapper = BeanPropertyRowMapper.newInstance(ProductImageWithFileInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.query(SELECT_PRODUCT_IMAGES_WHTH_FILE_INFO, params, rowMapper);
	}
	
	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImages(int displayId){
		RowMapper<DisplayInfoImageWithFileInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImageWithFileInfo.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("display_id", displayId);
		return jdbc.query(SELECT_DISPLAY_INFO_IMAGES, params, rowMapper);
	}
	
	public float getProductAvgScore(int productId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.queryForObject(SELECT_DISPLAY_AVG_SCORE, params, Float.class);
	}
	
	public List<ProductPrice> getProductPrices(int productId){
		RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
		Map<String, Integer> params = new HashMap<>();
		params.put("product_id", productId);
		return jdbc.query(SELECT_PRODUCT_PRICE_BY_PRODUCT_ID, params, rowMapper);
	}
}
