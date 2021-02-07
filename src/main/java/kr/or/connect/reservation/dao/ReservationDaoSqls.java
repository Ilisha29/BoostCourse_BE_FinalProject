package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	public static final String SELECT_DISPLAY_INFO_BY_CATEGORY_ID = 
			"SELECT p.id id, p.category_id categoryId, d.id displayInfoId, c.name name, "
			+ "p.description description, p.content content, p.event event, "
			+ "d.opening_hours openingHours, d.place_name placeName, d.place_lot placeLot, "
			+ "d.place_street placeStreet, d.tel tel, d.homepage homepage, d.email email, "
			+ "d.create_date createDate, d.modify_date modifyDate, pi.file_id fileId "
			+ "FROM product p, category c, display_info d, product_image pi "
			+ "WHERE p.category_id = c.id AND p.id = d.product_id AND p.id = pi.product_id AND pi.type='ma' "
			+ "AND c.id = :category_id "
			+ "LIMIT :start, 1000;";
	
	public static final String SELECT_ALL_DISPLAY_INFO = 
			"SELECT p.id id, p.category_id categoryId, d.id displayInfoId, c.name name, "
			+ "p.description description, p.content content, p.event event, "
			+ "d.opening_hours openingHours, d.place_name placeName, d.place_lot placeLot, "
			+ "d.place_street placeStreet, d.tel tel, d.homepage homepage, d.email email, "
			+ "d.create_date createDate, d.modify_date modifyDate, pi.file_id fileId "
			+ "FROM product p, category c, display_info d, product_image pi "
			+ "WHERE p.category_id = c.id AND p.id = d.product_id AND p.id = pi.product_id AND pi.type='ma' "
			+ "LIMIT :start, 1000;";
	
	public static final String SELECT_ALL_PROMOTION =
			"SELECT prom.id id, prom.product_id productId, c.id categoryId, "
			+ "c.name categoryName, prod.description description, pi.file_id fileId "
			+ "FROM promotion prom, product prod, category c, product_image pi "
			+ "WHERE prod.category_id = c.id AND prod.id = prom.product_id "
			+ "AND prod.id = pi.product_id AND pi.type = 'ma';";
}
