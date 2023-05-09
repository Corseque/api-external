package ru.apiexternal.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import ru.api.manufacturer.api.ManufacturerGateway;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.apiexternal.security.jwt.JwtToken;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturer")
@SessionScope
public class ManufacturerRestController {

    private final ManufacturerGateway manufacturerGateway;
    private final JwtToken jwtToken;

    @GetMapping("/all")
    public List<ManufacturerDto> getManufacturerList() {
        return manufacturerGateway.getManufacturerList(jwtToken.getToken());
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "manufacturerId") Long id) {
        return manufacturerGateway.getManufacturer(jwtToken.getToken(), id);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.addManufacturer(jwtToken.getToken(), manufacturerDto);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "manufacturerId") Long id,
                                                @Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.updateManufacturer(jwtToken.getToken(), id, manufacturerDto);
    }

    @DeleteMapping("/{manufacturerId}")
    public void deleteById(@PathVariable(name = "manufacturerId") Long id) {
        manufacturerGateway.deleteById(jwtToken.getToken(), id);
    }
}

