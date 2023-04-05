package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.api.manufacturer.api.ManufacturerGateway;
import ru.api.manufacturer.dto.ManufacturerDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturer")
public class ManufacturerRestController {

    private final ManufacturerGateway manufacturerGateway;

    @GetMapping("/all")
    public List<ManufacturerDto> getManufacturerList() {
        return manufacturerGateway.getManufacturerList();
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "manufacturerId") Long id) {
        return manufacturerGateway.getManufacturer(id);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.addManufacturer(manufacturerDto);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "manufacturerId") Long id,
                                                @Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.updateManufacturer(id, manufacturerDto);
    }

    @DeleteMapping("/{manufacturerId}")
    public void deleteById(@PathVariable(name = "manufacturerId") Long id) {
        manufacturerGateway.deleteById(id);
    }
}

