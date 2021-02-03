package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.CategoryDaoSqls.SELECT_ALL;
import static kr.or.connect.reservation.dao.ProductDaoSqls.SELECT_BY_CATEGORY_ID;

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
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.Product;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_BY_PRODUCT_ID;

@Repository
public class DisplayInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	
	public DisplayInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<DisplayInfo> selectByProductId(int productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product_id",productId);
		return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
	}
}
