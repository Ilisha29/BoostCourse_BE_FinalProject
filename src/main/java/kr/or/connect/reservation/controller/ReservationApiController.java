package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {

	private CategoryService categoryService;
	private ReservationService reservationService;

	public ReservationApiController(CategoryService categoryService, ReservationService reservationService) {
		this.categoryService = categoryService;
		this.reservationService = reservationService;
	}

	@ApiOperation(value = "카테고리 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK Good Boy"),
			@ApiResponse(code = 500, message = "Exception!!~~!!") })

	@GetMapping(path = "/categories")
	public Map<String, Object> getCategories() {
		Map<String, Object> map = new HashMap<>();
		map.put("size", categoryService.list().size());
		map.put("items", categoryService.list());
		return map;
	}

	@ApiOperation(value = "상품 목록 구하기 확인")
	@ApiResponses({ @ApiResponse(code = 200, message ="Success GET DisplyaInfo"),
			@ApiResponse(code = 500, message = "Exception!!~~!!") })
	
	@GetMapping(path = "/displayinfos")
	public Map<String, Object> getDisplayInfos(
			@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId,
			@RequestParam(name = "start", required = false, defaultValue = "0") int start) {
		Map<String, Object> map = new HashMap<>();
		List<ProductWithDisplayInfoAndCategory> list = reservationService.getProducts(categoryId, start);
		map.put("totalCount", list.size() + start);
		map.put("productCount", list.size());
		map.put("products", list);
		return map;
	}
	@ApiOperation(value = "프로모 확인")
	@ApiResponses({ @ApiResponse(code = 200, message ="Success GET Promotions"),
			@ApiResponse(code = 500, message = "Exception!!~~!!") })
	
	@GetMapping(path = "/promotions")
	public Map<String, Object> getPromotions(){
		Map<String, Object> map = new HashMap<>();
		List<PromotionWithCategoryAndProductAndProductImage> promotions = reservationService.getPromotions();
		map.put("size", promotions.size());
		map.put("items", promotions);
		return map;
	}
}
