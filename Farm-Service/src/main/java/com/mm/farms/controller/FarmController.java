package com.mm.farms.controller;

import com.mm.farms.dto.FarmDto;
import com.mm.farms.entity.Farm;
import com.mm.farms.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farm")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @PostMapping("/register_farm")
    public ResponseEntity<Farm> registerFarm(@RequestBody FarmDto dto){
        return null;
    }

    @GetMapping("/get")
    public ResponseEntity<Farm> getFarmData(@PathVariable("id") Long id){
        return ResponseEntity.ok(farmService.getById(id));

    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Farm>> getAllFarmData(@PathVariable Long id){
        return ResponseEntity.ok(farmService.getAllFarmsData());
    }

}
