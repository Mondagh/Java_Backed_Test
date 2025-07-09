package services;

import java.util.Optional;

import Repositorys.CategoryRepository;
import model.Category;

public class CategoryServiceImp implements CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryServiceImp(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void putCategory(Category category) {
		categoryRepository.save(category);

	}

	@Override
	public void deleteCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);

		if (category.isPresent()) {
			categoryRepository.delete(category.get());
		}

	}

}
