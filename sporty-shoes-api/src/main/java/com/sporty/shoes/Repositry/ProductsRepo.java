package com.sporty.shoes.Repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sporty.shoes.enteties.Products;
@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer>{

	List<Products> findByNameContaining(String name);
	List<Products> findAllByOrderByCategoryAsc();
}
