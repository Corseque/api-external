package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.api.product.api.ProductGateway;
import ru.api.product.dto.ProductDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductGateway productGateway;

    @GetMapping("/all")
    public List<ProductDto> getProductsList() {
        return productGateway.getProductsList();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "productId") Long id) {
        return productGateway.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductDto productDto) {
        return productGateway.addProduct(productDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "productId") Long id,
                                           @Validated @RequestBody ProductDto productDto) {
        return productGateway.updateProduct(id, productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteById(@PathVariable(name = "productId") Long id) {
        productGateway.deleteById(id);
    }

}