package com.mm.farms.service.impl;

import com.mm.farms.entity.Farm;
import com.mm.farms.repository.FarmRepository;
import com.mm.farms.service.FarmService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService{

    @Autowired
    private FarmRepository farmRepository;

    @Override
    public Farm getById(Long id){
        return farmRepository.findById(id).orElseThrow(()-> new NotFoundException("Farm Not Found"));
    }

    @Override
    public List<Farm> getAllFarmsData(){
        return farmRepository.findAll();
    }
}
