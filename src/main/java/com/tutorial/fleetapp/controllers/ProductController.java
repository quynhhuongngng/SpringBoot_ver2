package com.tutorial.fleetapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.services.ProductService;
import com.tutorial.fleetapp.services.ProductTypeService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;

	@GetMapping("/list")
	public String getProductView(Model model) {
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);

		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "user/body/product/list";
	}
	
	
	
	
	
	
	// Get All Countrys
	@GetMapping("/products")
	public String getProduct(Model model) {
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);

		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "admin/body/product";
	}
	

	@RequestMapping("products/findById")
	@ResponseBody
	public Optional<Product> findById(Integer id) {
		return productService.findById(id);
	}

	@GetMapping("/productadd")
	public String addProduct(Model model) {
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);

		return "admin/body/productadd";
	}

	// Add Country
	@PostMapping(value = "products/addNew")
	public String addNew(Product product) {
		productService.save(product);
		return "redirect:/products";
	}

	@RequestMapping(value = "products/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(Product product, @RequestParam("photo") String photo, @RequestParam("image") String image) {
		if (image.isEmpty()) {
			product.setImage(photo);
			productService.save(product);
			return "redirect:/products";
		}
		productService.save(product);
		return "redirect:/products";
	}

	@RequestMapping(value = "products/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String xoa(Integer id) {
		productService.delete(id);
		return "redirect:/products";
	}

}
