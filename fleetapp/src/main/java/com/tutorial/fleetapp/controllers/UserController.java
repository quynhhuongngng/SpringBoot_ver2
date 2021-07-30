package com.tutorial.fleetapp.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tutorial.fleetapp.models.Comment;
import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.models.User;
import com.tutorial.fleetapp.services.UserService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	ServletContext app;

	@Autowired
	HttpSession session;

	/* ACCOUNT */
	// Thay đổi mật khẩu user
	@PostMapping("/acount/edit")
	public String editProfile(Model model, RedirectAttributes redir, Principal principal, 
			@RequestParam("firstname") String fname, @RequestParam("lastname") String lname,
			@RequestParam("photo_upload") MultipartFile file)throws IllegalStateException, IOException{
		User profile = userService.findByUsername(principal.getName());
		
		profile.setFirstname(fname);
		profile.setLastname(lname);
		
		
		if(!file.isEmpty()) {
			String realPath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/photos";
			File f = new File(realPath, file.getOriginalFilename());
			file.transferTo(f);
			profile.setPhoto(f.getName());
		}
		
//		if(!file.isEmpty()) {
//			profile.setPhoto(file.getOriginalFilename());
//			String path = app.getRealPath("/static/img/photos"+profile.getPhoto());
//			file.transferTo(new File(path));
//		}
		
		userService.save(profile);
		redir.addFlashAttribute("message", "Xác nhận đã thay đổi thông tin!");
		return "redirect:/users/profile"; 
	}
	
	
	
	@PostMapping("/account/change")
	public String changePassword(Model model, RedirectAttributes redir, @RequestParam("id") Integer id,
			@RequestParam("password") String pw, @RequestParam("newpassword") String pw1,
			@RequestParam("confirmPassword") String pw2) {

		PasswordEncoder passencoder = new BCryptPasswordEncoder();

		Optional<User> user1 = userService.findById(id);
		User user = user1.get();

		if (!pw1.equals(pw2)) {
			redir.addFlashAttribute("message", "Xác nhận mật khẩu không trùng khớp!");
		} else {
			if (user == null) {
				redir.addFlashAttribute("message", "Sai tài khoản!!!");
			} else if (!passencoder.matches(pw, user.getPassword())) {
				redir.addFlashAttribute("message", "Mật khẩu hiện tại không đúng!");
			} else {
				user.setPassword(pw1);
				userService.save(user);
				redir.addFlashAttribute("message", "Thay đổi mật khẩu thành công!");
			}
		}

		return "redirect:/users/profile";
	}

	// Hiển thị thông tin user
	@RequestMapping("/users/profile")
	public String getProfileHeader(Model model, Principal principal) {
		// Get the User in a Controller
		User profile = userService.findByUsername(principal.getName());

		model.addAttribute("profile", profile);

		return "/user/body/account/Profile";
	}

//	@RequestMapping("/users/profile")
//	public String getProfileHeader(Model model) {
//		//Get the User in a Controller
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//		    String currentUserName = authentication.getName();
//		    return currentUserName;
//		}
//		
//
//		return "/user/body/account/Profile";
//	}

	// ADMIN

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
