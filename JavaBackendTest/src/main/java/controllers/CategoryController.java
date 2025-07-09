package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import model.Category;
import services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	
	@Autowired
	private CategoryService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public void putCategory( @Valid @RequestBody Category category){
		 service.putCategory(category);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void deteleCataegory(@PathVariable Long id) {
		service.deleteCategory(id);
	}
}
