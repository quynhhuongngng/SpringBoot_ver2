package com.tutorial.fleetapp;

import com.tutorial.fleetapp.services.ProductService;
import com.tutorial.fleetapp.services.ProductTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.ProductType;
import org.springframework.ui.Model;

@Controller
public class ApplicationController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	
	@GetMapping("/")
	public String goHome(Model model ) {
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);

		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "user/index";
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
