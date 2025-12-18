package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Product;
import tienda_back.domain.repository.ProductRepository;
import tienda_back.domain.service.ProductService;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe"));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Long id = product.getId();
        if (id == null || !productRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto con el id: " + id + " no existe");
        }
        return productRepository.update(product);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto con el id: " + id + " no existe");
        }
        productRepository.deleteById(id);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
