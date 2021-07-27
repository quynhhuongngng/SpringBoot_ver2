package com.tutorial.fleetapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorial.fleetapp.models.Comment;
import com.tutorial.fleetapp.models.User;

import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.services.CommentService;
import com.tutorial.fleetapp.services.ProductService;
import com.tutorial.fleetapp.services.ProductTypeService;
import com.tutorial.fleetapp.services.UserService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductTypeService productTypeService;

	@GetMapping("/productlist")
	public String getProductView(Model model) {
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);

		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "user/body/product/Product_list";
	}

	
	@RequestMapping("product/detail")
	public String getProductDetail(Model model,@PathParam("id") Integer id) {

		Optional<Product> product1 = productService.findById(id);
		//Trả về object sản phẩm
		Product product = product1.get();
		model.addAttribute("product",product);

		// Trả về mảng sản phẩm
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);

		// Trả về mảng người dùng
		List<User> userlist = userService.getUsers();
		model.addAttribute("users", userlist);

		// Trả về mảng comment theo id sản phẩm
		List<Comment> commentList = product.getComments();
		model.addAttribute("comments", commentList);

		// Trả về mảng loại sản phẩm
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);

		return "user/body/product/Product_Detail";
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
