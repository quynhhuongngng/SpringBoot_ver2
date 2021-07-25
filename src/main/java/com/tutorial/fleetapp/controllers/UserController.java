package com.tutorial.fleetapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tutorial.fleetapp.models.User;
import com.tutorial.fleetapp.services.UserService;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> userlist = userService.getUsers();
		model.addAttribute("users", userlist);
		return "/admin/body/User";
	}

	@RequestMapping("users/findById")

	@ResponseBody
	public Optional<User> findById(int id) {
		return userService.findById(id);
	}

	// Modified method to Add a new user User
	@PostMapping(value = "/register/addNew")
	public String addNew(User user, RedirectAttributes redir, @RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword, @RequestParam("username") String username) {

		if (userService.findByUsername(username) != null) {
			redir.addFlashAttribute("message", "Tên đăng nhập đã tồn tại!!!");
			return "redirect:/register";
		}
		if (!password.equals(confirmPassword)) {
			redir.addFlashAttribute("message", "Mật khẩu không trùng khớp!!!");
			return "redirect:/register";
		}
		user.setPhoto("user.jpg");
		userService.save(user);
		// RedirectView redirectView = new RedirectView("/login", true);
		redir.addFlashAttribute("message", "Tạo tài khoản thành công!!!");
		return "redirect:/login";
	}

	@PostMapping(value = "users/addNew")
	public String addNew(User user, RedirectAttributes redir, @RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword, @RequestParam("username") String username,
			@RequestParam("photo") String photo) {

		if (userService.findByUsername(username) != null) {
			redir.addFlashAttribute("message", "Tên đăng nhập đã tồn tại!!!");
			return "redirect:/users";
		}
		if (!password.equals(confirmPassword)) {
			redir.addFlashAttribute("message", "Mật khẩu không trùng khớp!!!");
			return "redirect:/users";
		}
		if (photo.isEmpty()) {
			user.setPhoto("user.jpg");
		}
		redir.addFlashAttribute("message", "Tạo tài khoản thành công!!!");
		userService.save(user);
		return "redirect:/users";

	}

	@RequestMapping(value = "/users/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(User user, @RequestParam("photo") String photo, @RequestParam("image") String image,
			RedirectAttributes redir) {

		if (photo.isEmpty()) {
			user.setPhoto(image);
			userService.save(user);
			return "redirect:/users";
		}
		userService.save(user);
		redir.addFlashAttribute("message", "Cập nhật thành công!!!");
		return "redirect:/users";
	}

	@RequestMapping(value = "/users/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		userService.delete(id);
		return "redirect:/users";
	}

}
