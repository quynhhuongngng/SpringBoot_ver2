package com.tutorial.fleetapp;

import com.tutorial.fleetapp.services.ProductService;
import com.tutorial.fleetapp.services.ProductTypeService;
import com.tutorial.fleetapp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.models.User;

import org.springframework.ui.Model;

@Controller
public class ApplicationController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {"/","/index","/home"})
	public String goHome(Model model, Principal principal) {
//		//Hiển thị dropdown menuheader quyền admin
//		User profile = userService.findByUsername(principal.getName());
//		model.addAttribute("profile", profile);
		
		//Hiển thị list category trên menu header
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		//Hiển thị ds sp tương ứng vs loại sp trên menu header
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);
		
		return "user/index";
	}
	
	@GetMapping("/admin/index")
	public String goAdmin() {
		return "admin/index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/actions/login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "/actions/register";
	}
}
