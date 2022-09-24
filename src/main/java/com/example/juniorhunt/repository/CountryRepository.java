package com.example.juniorhunt.repository;

import com.example.juniorhunt.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findByCountry(String country);
}
