package kr.or.connect.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.UserRole;

@Repository
public class UserRoleDao {
	private NamedParameterJdbcTemplate jdbc;
	// BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
	// roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
	// 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
	// 생성하는 방법은 아래의 rowMapper2를 참고한다.
	private RowMapper<UserRole> rowMapper = BeanPropertyRowMapper.newInstance(UserRole.class);

	public UserRoleDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<UserRole> getRolesByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		return jdbc.query(UserRoleDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}

	public void addUserRole(int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("user_id", userId);
		map.put("role_name", "ROLE_USER");
		jdbc.update(UserRoleDaoSqls.INSERT_USER_ROLE, map);
	}
}
