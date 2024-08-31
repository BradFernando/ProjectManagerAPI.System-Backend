package com.system.pma.controller;

import com.system.pma.dto.AreaDto;
import com.system.pma.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public List<AreaDto> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/{id}")
    public AreaDto getAreaById(@PathVariable Long id) {
        return areaService.getAreaById(id);
    }

    @PostMapping
    public AreaDto createArea(@RequestBody AreaDto areaDto) {
        return areaService.saveArea(areaDto);
    }

    @PutMapping("/{id}")
    public AreaDto updateArea(@PathVariable Long id, @RequestBody AreaDto areaDto) {
        return areaService.updateArea(id, areaDto);
    }

    @DeleteMapping("/{id}")
    public void deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
    }
}