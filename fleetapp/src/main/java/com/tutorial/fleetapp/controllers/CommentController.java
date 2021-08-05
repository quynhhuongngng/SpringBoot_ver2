package com.tutorial.fleetapp.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorial.fleetapp.services.CommentService;
import com.tutorial.fleetapp.services.ProductService;
import com.tutorial.fleetapp.services.UserService;
import com.tutorial.fleetapp.models.Comment;
import com.tutorial.fleetapp.models.Product;
import com.tutorial.fleetapp.models.User;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	
	@PostMapping("product/addCommentProduct/{id}")
		public String Comment(Comment comment, @PathVariable("id") Integer id)  {
//		User user = (User) session.getAttribute("user");
		// Optional<User> user1 = userService.findById(id);
		// User user = user1.get();
		
		Optional<Product> product1 = productService.findById(id);
		Product product = product1.get();
		// if (user == null) {
		// 	return "redirect:/users";
		// } 
		// else {
			// comment.setUser(user);
			comment.setProductId(id);
			// comment.setUserId(userId);
			comment.setCommentedDate(new Date());
			commentService.save(comment);
			return "redirect:/product/detail?id={id}";
		// }
	}

}
