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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.services.ProductTypeService;

@Controller
public class ProductTypeController {

	@Autowired
	private ProductTypeService productTypeService;

	// Get All Countrys
	@GetMapping("/producttypes")
	public String getProductType(Model model) {
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return "admin/body/producttype";
	}

	// Add Country
	@PostMapping(value = "producttypes/addNew")
	public String addNew(ProductType productType, @RequestParam("category") String category, RedirectAttributes redir) {
		if (productTypeService.findByCategory(category) != null) {
			redir.addFlashAttribute("message", "Không được đặt trùng tên thể loại!!!");
			return "redirect:/producttypes";
		}
		productTypeService.save(productType);
		return "redirect:/producttypes";
	}

	@RequestMapping("producttypes/findById")

	@ResponseBody
	public Optional<ProductType> findById(Integer id, Model model) {
		List<ProductType> productTypeList = productTypeService.getProductType();
		model.addAttribute("producttypes", productTypeList);
		return productTypeService.findById(id);
	}

	@RequestMapping(value = "producttypes/update", method = { RequestMethod.PUT, RequestMethod.GET })
	public String update(ProductType productType, @RequestParam("category") String category,  @RequestParam("type") String type, RedirectAttributes redir) {
		if (productTypeService.findByCategory(category) != null) {
			redir.addFlashAttribute("message", "Không được đặt trùng tên thể loại!!!");
			return "redirect:/producttypes";
		}
		//Ở đây cần thêm code
		productTypeService.save(productType);
		return "redirect:/producttypes";
	}

	@RequestMapping(value = "producttypes/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String delete(Integer id) {
		productTypeService.delete(id);
		return "redirect:/producttypes";
	}

}
