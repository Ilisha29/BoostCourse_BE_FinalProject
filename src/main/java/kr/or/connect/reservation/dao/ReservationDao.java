package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	
	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductWithDisplayInfoAndCategory> selectDisplayInfo(int categoryId, int start) {
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
}
