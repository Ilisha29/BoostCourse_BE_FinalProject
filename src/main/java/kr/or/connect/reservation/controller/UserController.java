package kr.or.connect.reservation.controller;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.reservation.dto.User;
import kr.or.connect.reservation.service.UserService;

@Controller
@RequestMapping(path = "/users")
public class UserController {
	// 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "users/loginform";
	}

	@RequestMapping("/loginerror")
	public String loginerror(@RequestParam("login_error") String loginError) {
		return "users/loginerror";
	}

	@GetMapping("/joinform")
	public String joinform() {
		return "users/joinform";
	}

	// 사용자가 입력한 name, email, password가 member에 저장된다.
	@PostMapping("/join")
	public String join(@ModelAttribute User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.addUser(user);
		userService.addUserRole(user.getEmail());
		return "redirect:/users/welcome";
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "users/welcome";
	}

	@GetMapping("/userinfo")
	public String userInfo(Principal principal, ModelMap modelMap) {
		String loginId = principal.getName();
		User user = userService.getUser(loginId);
		modelMap.addAttribute("user", user);

		return "users/userinfo";
	}
}