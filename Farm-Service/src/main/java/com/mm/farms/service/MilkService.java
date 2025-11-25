package com.mm.farms.service;

import com.mm.farms.entity.Milk;
import com.mm.farms.enums.DayType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MilkService {
    Milk recordMilk(Long farmId, DayType dayType, Double producedMilk);
    List<Milk> getMilkByFarm(Long farmId);
    List<Milk> getMilkByDate(LocalDate date);
    Double getTotalMilkByDate(LocalDate date);
}
