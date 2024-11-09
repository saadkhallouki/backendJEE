package com.example.JEE.controllers;

import com.example.JEE.entities.Table;
import com.example.JEE.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @PostMapping
    public Table createTable(@RequestBody Table table) {
        return tableService.createTable(table);
    }

    @GetMapping("/{id}")
    public Optional<Table> getTableById(@PathVariable int id) {
        return tableService.getTableById(id);
    }

    @GetMapping
    public List<Table> getAllTables() {
        return tableService.getAllTables();
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable int id) {
        tableService.deleteTable(id);
    }
}

