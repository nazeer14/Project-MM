package com.mm.farms.service;

import com.mm.farms.entity.Farm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FarmService{

    Farm getById(Long id);

    List<Farm> getAllFarmsData();
}
