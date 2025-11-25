package com.mm.farms.service.impl;

import com.mm.farms.entity.Milk;
import com.mm.farms.entity.Farm;
import com.mm.farms.enums.DayType;
import com.mm.farms.repository.MilkRepository;
import com.mm.farms.repository.FarmRepository;
import com.mm.farms.service.MilkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MilkServiceImpl implements MilkService {

    private final MilkRepository milkRepository;
    private final FarmRepository farmRepository;

    @Override
    public Milk recordMilk(Long farmId, DayType dayType, Double producedMilk) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new RuntimeException("Farm not found with ID: " + farmId));

        LocalDate today = LocalDate.now();

        milkRepository.findByFarmAndDateAndDayType(farm, today, dayType).ifPresent(m -> {
            throw new RuntimeException("Milk entry already exists for today (" + dayType + ")");
        });

        Milk milk = Milk.builder()
                .farm(farm)
                .dayType(dayType)
                .date(today)
                .producedMilk(producedMilk)
                .build();

        return milkRepository.save(milk);
    }

    @Override
    public List<Milk> getMilkByFarm(Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new RuntimeException("Farm not found"));
        return milkRepository.findByFarm(farm);
    }

    @Override
    public List<Milk> getMilkByDate(LocalDate date) {
        return milkRepository.findByDate(date);
    }

    @Override
    public Double getTotalMilkByDate(LocalDate date) {
        return milkRepository.findByDate(date)
                .stream()
                .mapToDouble(Milk::getProducedMilk)
                .sum();
    }
}
