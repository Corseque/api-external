package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.api.category.api.CategoryGateway;
import ru.api.category.dto.CategoryDto;
import ru.apiexternal.security.jwt.JwtToken;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@SessionScope
public class CategoryRestController {

    private final CategoryGateway categoryGateway;
    private final JwtToken jwtToken;

    @GetMapping("/all")
    public List<CategoryDto> getManufacturerList() {
        return categoryGateway.getCategoryList(jwtToken.getToken());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "categoryId") Long id) {
        return categoryGateway.getCategory(jwtToken.getToken(), id);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.addCategory(jwtToken.getToken(), categoryDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "categoryId") Long id,
                                                @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.updateCategory(jwtToken.getToken(), id, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteById(@PathVariable(name = "categoryId") Long id) {
        categoryGateway.deleteById(jwtToken.getToken(), id);
    }


}
