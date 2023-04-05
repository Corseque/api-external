package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.api.category.api.CategoryGateway;
import ru.api.category.dto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final CategoryGateway categoryGateway;

    @GetMapping("/all")
    public List<CategoryDto> getManufacturerList() {
        return categoryGateway.getManufacturerList();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "categoryId") Long id) {
        return categoryGateway.getManufacturer(id);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.addManufacturer(categoryDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "categoryId") Long id,
                                                @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.updateManufacturer(id, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteById(@PathVariable(name = "categoryId") Long id) {
        categoryGateway.deleteById(id);
    }


}
