package com.example.JEE.repositories;

import com.example.JEE.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer> {
}
