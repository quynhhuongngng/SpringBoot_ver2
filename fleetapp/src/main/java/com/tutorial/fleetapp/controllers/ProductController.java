package com.tutorial.fleetapp.controllers;

import java.security.Principal;
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
	
	
	/*ACCOUNT*/
	@GetMapping("/productlist")
	public String getProductView(Model model, Principal principal) {
		//Hiển thị dropdown menuheader nếu quyền admin
		User profile = userService.findByUsername(principal.getName());
		model.addAttribute("profile", profile);
		
		//Hiển thị ds sp tương ứng vs loại sp trên menu header
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);
		//Hiển thị list category trên menu header
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "user/body/product/Product_list";
	}

	@RequestMapping("product/detail")
	public String getProductDetail(Model model, @PathParam("id") Integer id, Principal principal) {
		//Hiển thị dropdown menuheader nếu quyền admin
		User profile = userService.findByUsername(principal.getName());
		model.addAttribute("profile", profile);
		Optional<Product> product1 = productService.findById(id);
		//Trả về object sản phẩm
		Product product = product1.get();
		model.addAttribute("product", product);
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
	
	//Chức năng: Tìm kiếm (gần đúng) trong menu header
	@PostMapping("/SearchKeywords")
	public String SearchKeywords(Model model,  @RequestParam("keywords") String keywords, Principal principal) {
		//Hiển thị dropdown menuheader nếu quyền admin
		User profile = userService.findByUsername(principal.getName());
		model.addAttribute("profile", profile);
		
		//Hiển thị dropdown Category trong menu header
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		//Hiển thị sản phẩm tương ứng mỗi loại sp Category
		List<Product> productList = productService.findByKeywords(keywords);
		model.addAttribute("products", productList);

		return "user/body/product/Product_list";
	}
	
	//Hiển thị danh sách dropdown Category
	@RequestMapping("/SearchProducttype")
	public String getProductType(Model model, @PathParam("id") Integer id, Principal principal) {
		//Hiển thị dropdown menuheader nếu quyền admin
		User profile = userService.findByUsername(principal.getName());
		model.addAttribute("profile", profile);
		//Hiển thị dropdown Category trong menu header
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		//Hiển thị sản phẩm tương ứng mỗi loại sp Category
		List<Product> productList = productService.findByProductType(id);
		model.addAttribute("products", productList);
		
		return "user/body/product/Product_list";
	}
	
	//Hiển thị All sp nổi bật
		@RequestMapping("/productspecial")
		public String getAllProductSpecial(Model model, Principal principal) {
			User profile = userService.findByUsername(principal.getName());
			model.addAttribute("profile", profile);
			List<Product> productList = productService.findBySpecial();
			model.addAttribute("products", productList);
			List<ProductType> productTypeList = productTypeService.getProductType();
			model.addAttribute("producttypes", productTypeList);
			return "user/body/product/Product_list";
		}
	
	/*ADMIN*/
	//Hiển thị toàn bộ thông tin sp
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
	
	//Chức năng: Thêm sản phẩm
	@GetMapping("/products/productadd")
	public String addProduct(Model model) {
		//Hiển thị drop down loại sp
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);

		return "admin/body/ProductAdd";
	}

	@PostMapping(value = "products/addNew")
	public String addNew(Product product) {
		productService.save(product);
		return "redirect:/products";
	}
	
	//Chức năng: Chỉnh sửa sản phẩm
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
	
	//Chức năng xóa sản phẩm
	@RequestMapping(value = "products/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String xoa(Integer id) {
		productService.delete(id);
		return "redirect:/products";
	}

}
