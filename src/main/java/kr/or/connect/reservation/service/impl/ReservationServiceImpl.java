package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	ReservationDao reservationDao;

	public ReservationServiceImpl(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	@Override
	public List<ProductWithDisplayInfoAndCategory> getProducts(int categoryId, int start) {
		List<ProductWithDisplayInfoAndCategory> list = reservationDao.select(categoryId, start);
		for (ProductWithDisplayInfoAndCategory product : list) {
			System.out.println(product);
		}
		return list;
	}
	
}
