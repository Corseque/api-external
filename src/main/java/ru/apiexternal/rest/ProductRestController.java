package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.api.product.api.ProductGateway;
import ru.api.product.dto.ProductDto;
import ru.apiexternal.security.jwt.JwtToken;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@SessionScope
public class ProductRestController {

    private final ProductGateway productGateway;
    private final JwtToken jwtToken;

    @GetMapping("/all")
    public List<ProductDto> getProductsList() {
        return productGateway.getProductsList(jwtToken.getToken());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "productId") Long id) {
        return productGateway.getProduct(jwtToken.getToken(), id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create')")
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductDto productDto) {
        return productGateway.addProduct(jwtToken.getToken(), productDto);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('product.update')")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "productId") Long id,
                                           @Validated @RequestBody ProductDto productDto) {
        return productGateway.updateProduct(jwtToken.getToken(), id, productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteById(@PathVariable(name = "productId") Long id) {
        productGateway.deleteById(jwtToken.getToken(), id);
    }

}