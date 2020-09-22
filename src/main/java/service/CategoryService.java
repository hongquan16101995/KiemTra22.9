package service;

import model.Category;
import model.User;

public interface CategoryService {
    Iterable<Category> findAll();

    Category findById(Long id);
}
