package kr.or.connect.reservation.dao;

public class UserDaoSqls {
	public static final String SELECT_ALL_BY_EMAIL = "SELECT id, name, password, email, phone, create_date, modify_date FROM user WHERE email = :email";
}
