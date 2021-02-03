package kr.or.connect.reservation;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;

public class ReservationApiApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		CategoryDao categoryDao = ac.getBean(CategoryDao.class);
		ProductDao productDao = ac.getBean(ProductDao.class);
		DisplayInfoDao displayInfoDao = ac.getBean(DisplayInfoDao.class);
		List<Category> list = categoryDao.selectAll();
		for(Category category : list) {
			List<Product> products = productDao.selectByCategoryId(category.getId());
			int count = 0;
			for(Product product : products) {
				count += displayInfoDao.selectByProductId(product.getId()).size();
			}
			category.setCount(count);
			System.out.println(category);
		}
	}	
}
