package com.tutorial.fleetapp.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.fleetapp.repositories.ProductRepository;
import com.tutorial.fleetapp.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Return list of states
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    // SAve new state
    public void save(Product product) {
        productRepository.save(product);
    }

    // get by id
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
