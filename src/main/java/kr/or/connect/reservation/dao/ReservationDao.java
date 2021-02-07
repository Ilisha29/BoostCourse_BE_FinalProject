package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_ALL;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_BY_CATEGORY_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper
			.newInstance(ProductWithDisplayInfoAndCategory.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductWithDisplayInfoAndCategory> select(int categoryId, int start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		if (categoryId != 0) {
			params.put("category_id", categoryId);
			return jdbc.query(SELECT_BY_CATEGORY_ID, params, rowMapper);
		} else {
			return jdbc.query(SELECT_ALL, params, rowMapper);
		}
	}
}
