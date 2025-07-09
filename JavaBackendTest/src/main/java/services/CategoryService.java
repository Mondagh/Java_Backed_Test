package services;

import model.Category;

public interface CategoryService {

	public void putCategory(Category category);

	public void deleteCategory(Long categoryId);
}
