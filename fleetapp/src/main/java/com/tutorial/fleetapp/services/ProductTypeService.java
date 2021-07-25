package com.tutorial.fleetapp.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.fleetapp.models.ProductType;
import com.tutorial.fleetapp.repositories.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    // Return list of producttype
    public List<ProductType> getProductType() {
        return productTypeRepository.findAll();
    }

    // SAve new producttype
    public void save(ProductType productType) {
        productTypeRepository.save(productType);
    }

    // get by id
    public Optional<ProductType> findById(int id) {
        return productTypeRepository.findById(id);
    }

    public ProductType findByCategory(String category) {
		return productTypeRepository.findByCategory(category);
    }
    
    public void delete(Integer id) {
        productTypeRepository.deleteById(id);
    }
}
