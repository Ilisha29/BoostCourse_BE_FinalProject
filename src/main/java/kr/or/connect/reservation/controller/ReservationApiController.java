package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.*;
import kr.or.connect.reservation.service.CategoryService;

@RestController
@RequestMapping(path = "/api/categories")
public class ReservationApiController {

	private CategoryService categoryService;

	public ReservationApiController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@ApiOperation(value ="카테고리 확인")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK Good Boy"),
			@ApiResponse(code = 500, message = "Exception!!~~!!")
	})

	@GetMapping
	public Map<String, Object> categoryList() {
		Map<String, Object> map = new HashMap<>();
		map.put("size", categoryService.list().size());
		map.put("items", categoryService.list());
		return map;
	}
}
