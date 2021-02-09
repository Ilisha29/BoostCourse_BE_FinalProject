package kr.or.connect.reservation.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

	public UserDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public User getMemberByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		return jdbc.queryForObject(UserDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}

	public void addUser(User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", user.getName());
		map.put("password", user.getPassword());
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("create_date", user.getCreateDate());
		map.put("modify_date", user.getModifyDate());
		jdbc.update(UserDaoSqls.INSERT_USER, map);
	}
	
}
