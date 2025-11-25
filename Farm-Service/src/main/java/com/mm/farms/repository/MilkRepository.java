package com.mm.farms.repository;


import com.mm.farms.entity.Milk;
import com.mm.farms.entity.Farm;
import com.mm.farms.enums.DayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MilkRepository extends JpaRepository<Milk, Long> {

    List<Milk> findByFarm(Farm farm);

    List<Milk> findByDate(LocalDate date);

    Optional<Milk> findByFarmAndDateAndDayType(Farm farm, LocalDate date, DayType dayType);

    List<Milk> findByFarmAndDateBetween(Farm farm, LocalDate start, LocalDate end);
}

