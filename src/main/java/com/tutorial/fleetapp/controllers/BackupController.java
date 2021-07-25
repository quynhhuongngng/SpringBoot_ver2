package com.tutorial.fleetapp.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;

import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BackupController {

	@Autowired
	ProductService productService;

	// Get All Countrys
	@GetMapping("/backup")
	public String getBackup(RedirectAttributes model) {
		List<Product> productList = productService.getProduct();
		model.addAttribute("products", productList);
		return "admin/body/backup";
	}

	@PostMapping(value = "backup/Add")
	public String backup(RedirectAttributes redir, @RequestParam("nameBU") String name) {
		// Chỉnh đường dẫn tối ưu định vị vị trí file đầu ra
		try {
			String path = new File(".").getCanonicalPath();
			Stream<Path> walk = Files.walk(Paths.get(path + "\\src\\main\\resources\\static\\img"));
			List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

			String nhap = new String();
			String desFile = path + "\\src\\main\\resources\\static\\backup\\BU-" + name + ".zip";

			redir.addFlashAttribute("message",
					"Backup thành công tại " + path + "\\src\\main\\resources\\static\\backup");

			String[] listFiles = new String[result.size()];
			for (int i = 0; i < listFiles.length; i++) {
				listFiles[i] = result.get(i);
			}
			ZipUtility u = new ZipUtility();
			u.zip(listFiles, desFile);
			System.out.println(listFiles);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/backup";
	}
}
