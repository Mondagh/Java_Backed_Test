package model;

import jakarta.persistence.Entity;

@Entity
public class Category {

	private Long id;
	private String name;
	private Category parentCategory;
	
}
