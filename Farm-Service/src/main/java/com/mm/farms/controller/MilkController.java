package com.mm.farms.controller;

import com.mm.farms.entity.Milk;
import com.mm.farms.enums.DayType;
import com.mm.farms.service.MilkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/milk")
@RequiredArgsConstructor
public class MilkController {

    private final MilkService milkService;

    @PostMapping("/record")
    public Milk recordMilk(@RequestParam Long farmId,
                           @RequestParam Double producedMilk) {
            LocalTime time=LocalTime.now();
            DayType dayType;
            if(time.isBefore(LocalTime.NOON)){
                dayType=DayType.MORNING;
            }
            dayType=DayType.EVENING;
        return milkService.recordMilk(farmId, dayType, producedMilk);
    }

    @GetMapping("/farm/{farmId}")
    public List<Milk> getMilkByFarm(@PathVariable Long farmId) {
        return milkService.getMilkByFarm(farmId);
    }

    @GetMapping("/date/{date}")
    public List<Milk> getMilkByDate(@PathVariable String date) {
        return milkService.getMilkByDate(LocalDate.parse(date));
    }

    @GetMapping("/total/{date}")
    public Double getTotalMilk(@PathVariable String date) {
        return milkService.getTotalMilkByDate(LocalDate.parse(date));
    }
}
