package com.tutorial.fleetapp.repositories;

import com.tutorial.fleetapp.models.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    ProductType findByCategory(String category);
}
