package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	
	public static final String SELECT_ALL_CATEGORY = "SELECT * FROM category";
	public static final String COUNT_CATEGORY_ITEM =
			"SELECT count(*) "
			+ "FROM product p, display_info d "
			+ "WHERE p.id = d.product_id AND p.category_id = :categoryId;";
	
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
	
	public static final String SELECT_DISPLAY_INFO_BY_DISPLAY_ID = 
			"SELECT p.id id, p.category_id categoryId, d.id displayInfoId, c.name name, "
			+ "p.description description, p.content content, p.event event, "
			+ "d.opening_hours openingHours, d.place_name placeName, d.place_lot placeLot, "
			+ "d.place_street placeStreet, d.tel tel, d.homepage homepage, d.email email, "
			+ "d.create_date createDate, d.modify_date modifyDate, pi.file_id fileId "
			+ "FROM product p, category c, display_info d, product_image pi "
			+ "WHERE p.category_id = c.id AND p.id = d.product_id AND p.id = pi.product_id AND pi.type='ma' "
			+ "AND d.id = :display_id ";
	
	public static final String SELECT_PRODUCT_IMAGES_WHTH_FILE_INFO=
			"SELECT pi.product_id productId, pi.id productImageId, pi.type type, pi.file_id fileInfoId, "
			+ "fi.file_name fileName, fi.save_file_name saveFileName, fi.content_type contentType, fi.delete_flag deleteFlag, "
			+ "fi.create_date createDate, fi.modify_date modifyDate "
			+ "FROM product_image pi, file_info fi "
			+ "WHERE pi.file_id = fi.id AND pi.type = 'ma' AND pi.product_id = :product_id;";
	public static final String SELECT_DISPLAY_INFO_IMAGES=
			"SELECT dii.id id, dii.display_info_id displayInfoId, dii.file_id fileId, "
			+ "fi.file_name fileName, fi.save_file_name saveFileName, "
			+ "fi.content_type contentType, fi.delete_flag deleteFlag, "
			+ "fi.create_date createDate, fi.modify_date modifyDate "
			+ "FROM display_info_image dii, file_info fi "
			+ "WHERE dii.file_id = fi.id AND dii.display_info_id = :display_id;";
	
	public static final String SELECT_DISPLAY_AVG_SCORE=
			"SELECT AVG(score) "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :product_id";
	
	public static final String SELECT_PRODUCT_ID_BY_DISPLAY_ID=
			"SELECT p.id "
			+ "FROM product p, display_info di "
			+ "WHERE p.id = di.product_id AND di.id = :display_id;";
	
	public static final String SELECT_PRODUCT_PRICE_BY_PRODUCT_ID=
			"SELECT pp.id id, pp.product_id productId, pp.price_type_name priceTypeName, "
			+ "pp.price price, pp.discount_rate discountRate, "
			+ "pp.create_date createDate, pp.modify_date modifyDate "
			+ "FROM product_price pp WHERE product_id = :product_id;";
	
	public static final String SELECT_RESERVATION_USER_COMMENTS_BY_PRODUCT_ID=
			"SELECT ruc.id id, ruc.product_id productId, ruc.reservation_info_id reservationInfoId, "
			+ "ruc.score score, ruc.comment comment, "
			+ "ruc.create_date createDate, ruc.modify_date modifyDate "
			+ "FROM reservation_user_comment ruc "
			+ "WHERE ruc.product_id = :product_id";
}
