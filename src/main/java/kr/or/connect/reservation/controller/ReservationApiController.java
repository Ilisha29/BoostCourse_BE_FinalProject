package kr.or.connect.reservation.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionWithCategoryAndProductAndProductImage;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationRegistration;
import kr.or.connect.reservation.dto.ReservationUserComment;
import kr.or.connect.reservation.service.FileService;
import kr.or.connect.reservation.service.ReservationService;
import kr.or.connect.reservation.service.UserDbService;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {

	private ReservationService reservationService;
	private UserDbService userDbService;
	private FileService fileService;

	public ReservationApiController(ReservationService reservationService, UserDbService userDbService,
			FileService fileService) {
		this.reservationService = reservationService;
		this.userDbService = userDbService;
		this.fileService = fileService;
	}

	@ApiOperation(value = "카테고리 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK Good Boy"),
			@ApiResponse(code = 500, message = "Exception!!~~!!") })

	@GetMapping(path = "/categories")
	public Map<String, Object> getCategories() {
		Map<String, Object> map = new HashMap<>();
		map.put("size", reservationService.getCategories().size());
		map.put("items", reservationService.getCategories());
		return map;
	}

	@ApiOperation(value = "상품 목록 구하기 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success GET DisplyaInfo"),
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
	@ApiResponses({ @ApiResponse(code = 200, message = "Success GET Promotions"),
			@ApiResponse(code = 500, message = "Exception!!~~!!") })

	@GetMapping(path = "/promotions")
	public Map<String, Object> getPromotions() {
		Map<String, Object> map = new HashMap<>();
		List<PromotionWithCategoryAndProductAndProductImage> promotions = reservationService.getPromotions();
		map.put("size", promotions.size());
		map.put("items", promotions);
		return map;
	}

	@ApiOperation(value = "상품정보 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success GET ProductInfo"),
			@ApiResponse(code = 500, message = "Product Info Exception!!~~!!") })

	@GetMapping(path = "/displayinfos/{displayId}")
	public Map<String, Object> getProductInfo(@PathVariable(name = "displayId") int displayId) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", reservationService.getProduct(displayId));
		map.put("productImages", reservationService.getProductImages(displayId));
		map.put("displayInfoImages", reservationService.getDisplayInfoImages(displayId));
		map.put("avgScore", reservationService.getProductAvgScore(displayId));
		map.put("productPrices", reservationService.getProductPrices(displayId));
		return map;
	}

	@ApiOperation(value = "댓글 목록 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success GET Replies"),
			@ApiResponse(code = 500, message = "Product Info Exception!!~~!!") })

	@GetMapping(path = "/comments")
	public Map<String, Object> getComments(
			@RequestParam(name = "productId", required = false, defaultValue = "0") int productId,
			@RequestParam(name = "start", required = false, defaultValue = "0") int start) {
		Map<String, Object> map = new HashMap<>();
		List<ReservationUserComment> reservationUserComments = reservationService.getComments(productId);
		map.put("totalCount", reservationUserComments.size());
		List<ReservationUserComment> applyStartReservationUserComments = reservationService
				.getCommentsApplyStart(reservationUserComments, start);
		map.put("commentCount", applyStartReservationUserComments.size());
		map.put("reservaionUserComments", applyStartReservationUserComments);
		return map;
	}

	@ApiOperation(value = "예약 등록하기")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success Create Reservation"),
			@ApiResponse(code = 500, message = "Reservation POST Exception!!~~!!") })

	@PostMapping(path = "/reservationInfos")
	public Map<String, Object> postReservation(@RequestBody ReservationRegistration request) {
		return reservationService.postReservation(request);
	}

	@ApiOperation(value = "예약 확인하기")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success Get Reservation"),
			@ApiResponse(code = 500, message = "Reservation Get Exception!!~~!!") })

	@GetMapping(path = "/reservationInfos")
	public Map<String, Object> getReservationInfos(Principal principal) {
		int userId = userDbService.getUser(principal.getName()).getId();
		return reservationService.getReservationInfos(userId);
	}

	@ApiOperation(value = "예약 취소하기")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success Put Reservation"),
			@ApiResponse(code = 500, message = "Reservation Get Exception!!~~!!") })

	@PutMapping(path = "/reservationInfos")
	public Map<String, Object> putReservationInfos(@RequestBody ReservationInfo reservationInfo) {
		return reservationService.putReservationInfos(reservationInfo.getId());
	}

	@ApiOperation(value = "댓글 등록하기")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success Put Comment"),
			@ApiResponse(code = 500, message = "Reservation Get Exception!!~~!!") })

	@PostMapping(path = "/comments")
	public Map<String, Object> postComment(@RequestParam("reservationInfoId") int reservationInfoId,
			@RequestParam("score") int score, @RequestParam("comment") String comment,
			@RequestParam("multipartFile") MultipartFile file, Principal principal) {
		Map<String, Object> map = new HashMap<>();
		ReservationInfo reservationInfo = reservationService.getReservationInfoByUserIdWithReservationInfoId(
				userDbService.getUser(principal.getName()).getId(), reservationInfoId);
		if (reservationInfo == null) {
			map.put("result", "fail by no reservationinfo");
			return map;
		}
		String create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		int fileId = fileService.postFile(file, create_date);
		if (fileId == 0) {
			map.put("result", "fail by fileupload");
			return map;
		}
		if (!reservationService.postComment(reservationInfo, score, comment, fileId, create_date)) {
			map.put("result", "fail by post comment");
			return map;
		}
		map.put("result", "success");
		map.put("productId", reservationInfo.getProductId());
		return map;
	}
}
