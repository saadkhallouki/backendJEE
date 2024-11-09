package com.example.JEE.services;

import com.example.JEE.entities.Table;
import com.example.JEE.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    public Optional<Table> getTableById(int id) {
        return tableRepository.findById(id);
    }

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public void deleteTable(int id) {
        tableRepository.deleteById(id);
    }
}
