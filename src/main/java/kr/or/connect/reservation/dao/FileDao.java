package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.FileDaoSqls.INSERT_FILE_INFO;
import static kr.or.connect.reservation.dao.FileDaoSqls.SELECT_FILE_INFO_ID_BY_FILENAME_WITH_CREATE_DATE;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.FileInfo;

@Repository
public class FileDao {

	private NamedParameterJdbcTemplate jdbc;

	public FileDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public void postFile(FileInfo fileInfo, String create_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("file_name", fileInfo.getFileName());
		params.put("save_file_name", fileInfo.getSaveFileName());
		params.put("content_type", fileInfo.getContentType());
		params.put("delete_flag", fileInfo.getDeleteFlag());
		params.put("create_date", create_date);
		params.put("modify_date", create_date);
		jdbc.update(INSERT_FILE_INFO, params);
	}

	public int getFileInfoId(String originalFilename, String create_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("file_name", originalFilename);
		params.put("create_date", create_date);
		return jdbc.queryForObject(SELECT_FILE_INFO_ID_BY_FILENAME_WITH_CREATE_DATE, params, Integer.class);
	}

}
