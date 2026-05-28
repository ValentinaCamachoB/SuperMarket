package com.example.SuperMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SuperMarket.entity.Category;
 
@Repository
public interface Categoryrepository extends JpaRepository<Category, Long> {
}
