package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDao categoryDao;
	private final ProductDao productDao;
	private final DisplayInfoDao displayInfoDao;

	public CategoryServiceImpl(CategoryDao categoryDao,ProductDao productDao ,DisplayInfoDao displayInfoDao) {
		this.categoryDao = categoryDao;
		this.productDao = productDao;
		this.displayInfoDao = displayInfoDao;
	}

	@Override
	@Transactional
	public List<Category> list() {
		List<Category> list = categoryDao.selectAll();
		for (Category category : list) {
			List<Product> products = productDao.selectByCategoryId(category.getId());
			int count = 0;
			for (Product product : products) {
				count += displayInfoDao.selectByProductId(product.getId()).size();
			}
			category.setCount(count);
		}
		return list;
	}
}
