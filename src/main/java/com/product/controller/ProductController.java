package com.product.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.product.model.Product;
import com.product.repository.IProductRepository;

@Controller
public class ProductController {

	@Autowired
	private IProductRepository productRepository;

	@GetMapping("/")
	public String Index(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "index";
	}

	@GetMapping("/createproduct")
	public String createProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "createproduct";
	}

	@GetMapping("/updateproduct/{id}")
	public String updateProductForm(@PathVariable Long id, Model model) {
		Optional<Product> product = productRepository.findById(id);
		model.addAttribute("product", product);
		return "updateproduct";
	}

	@PostMapping("/createproduct")
	public String createProduct(@ModelAttribute("person") Product product, Model model) {
		productRepository.save(product);
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "index";
	}

	@PostMapping("/updateproduct")
	public String updateProduct(@ModelAttribute("person") Product product, Model model) {
		product.setLastUpdated(LocalDateTime.now());
		productRepository.save(product);
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "index";
	}

	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct(@PathVariable Long id, Model model) {
		productRepository.deleteById(id);
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "index";
	}
}
