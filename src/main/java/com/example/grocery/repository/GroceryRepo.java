package com.example.grocery.repository;

import com.example.grocery.model.Grocery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GroceryRepo extends CrudRepository<Grocery , Integer> {

    @Query("select g from Grocery g where g.name = ?1")
    public Grocery findByName(String name);

    @Transactional
    @Modifying
    @Query("update Grocery g set g.price= ?1 where g.name = ?2")
    public int updateByName(int price , String name);

    @Transactional
    @Modifying
    @Query("update Grocery g set g.quantity= ?1 where g.id = ?2")
    public int updateQuantityById(int quantity , int id);
}
