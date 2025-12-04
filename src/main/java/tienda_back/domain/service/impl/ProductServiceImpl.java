package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Product;
import tienda_back.domain.respository.ProductRepository;
import tienda_back.domain.service.ProductService;

public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe"));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe"));
        productRepository.deleteById(id);
    }


    
}
