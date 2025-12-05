package tienda_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.mapper.ProductMapper;
import tienda_back.domain.model.Product;
import tienda_back.domain.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductMapper.getInstance().productToProductDto(product))
                .toList();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getById(id);
        ProductDto productDto = ProductMapper.getInstance().productToProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = ProductMapper.getInstance().productDtoToProduct(productDto);
        Product createdProduct = productService.create(product);
        ProductDto createdProductDto = ProductMapper.getInstance().productToProductDto(createdProduct);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        if (!id.equals(productDto.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        Product product = ProductMapper.getInstance().productDtoToProduct(productDto);
        Product updatedProduct = productService.update(product);
        ProductDto updatedProductDto = ProductMapper.getInstance().productToProductDto(updatedProduct);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
