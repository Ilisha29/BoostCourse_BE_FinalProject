package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.service.CategoryService;

@RestController
@RequestMapping(path="/api/categories")
public class CategoryApiController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Map<String, Object> list(){
		Map<String, Object> map = new HashMap<>();
		System.out.println("RUn");
		map.put("size", categoryService.list().size());
		map.put("items", categoryService.list());
		return map;
	}
}
