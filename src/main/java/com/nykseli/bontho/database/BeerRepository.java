package com.nykseli.bontho.database;

import java.util.List;

import com.nykseli.bontho.entity.Beer;

import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer> {
    List<Beer> findByUserId(Integer userId);
}
