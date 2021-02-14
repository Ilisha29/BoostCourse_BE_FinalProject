package kr.or.connect.reservation.dao;

public class FileDaoSqls {
	public static final String INSERT_FILE_INFO =
			"INSERT INTO file_info (file_name, save_file_name, content_type, delete_flag, create_date, modify_date) "
			+ "VALUES(:file_name, :save_file_name, :content_type, :delete_flag, :create_date, :modify_date);";
	
	public static final String SELECT_FILE_INFO_ID_BY_FILENAME_WITH_CREATE_DATE=
			"SELECT id FROM file_info "
			+ "WHERE file_name = :file_name AND create_date = :create_date;";
}
