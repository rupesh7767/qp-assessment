package com.example.grocery.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grocery {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id()
    private int id;
    private String name;
    private int price;
    private int quantity;
}
