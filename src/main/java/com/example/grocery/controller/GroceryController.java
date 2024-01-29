package com.example.grocery.controller;


import com.example.grocery.model.Grocery;
import com.example.grocery.repository.GroceryRepo;
import com.example.grocery.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class GroceryController {

  //  public Logger logger = new Logger(GroceryController.class);

    @Autowired
    GroceryRepo groceryRepo;


    @PostMapping("/addGrocery")
    public Response addGrocery(@RequestBody Grocery grocery,@RequestHeader String role){
        if (role.equalsIgnoreCase("admin")){
            if (groceryRepo.findByName(grocery.getName()) == null){
                groceryRepo.save(grocery);
                return new Response(201,"Grocery added successfully!!");
            }else {
                return new Response(200,"Grocery Not added!!" , "Grocery already present with same name");
            }
        }else {
            groceryRepo.deleteById(grocery.getId());
            return  new Response(200,"Deleted successfully!!");
        }


    }

    @GetMapping("/fetchALlGrocery")
    public List<Grocery> fetchAllGrocery(){
        List<Grocery> groceryList = new ArrayList<>();
        Iterator<Grocery> itr = groceryRepo.findAll().iterator();
        while (itr.hasNext()){
            groceryList.add(itr.next());
        }
        return groceryList.stream().filter(grocery-> grocery.getQuantity()>0).collect(Collectors.toList());
    }

    @PostMapping("/fetchGrocery")
    public Grocery fetchGrocery(@RequestParam String name){

        Grocery grocery = groceryRepo.findByName(name);
        return grocery;
    }

    @DeleteMapping("/delete/{name}")
    public Response deleteGrocery(@PathVariable String name,@RequestHeader String role){
        if (role.equalsIgnoreCase("admin")){
            Grocery grocery = groceryRepo.findByName(name);
            if (grocery == null){
                return  new Response(404,"Not deleted","Grocery details not found for given name");
            }else {
                groceryRepo.deleteById(grocery.getId());
                return  new Response(200,"Deleted successfully!!");
            }
        }else {
            return  new Response(403,"Unauthorized","Only admin can access this functionality");
        }

    }

    @PostMapping("/update")
    public Response updateGrocery(@RequestBody Grocery grocery , @RequestHeader String role){
        if (role.equalsIgnoreCase("admin")){
            Grocery result = groceryRepo.findByName(grocery.getName());
            if (grocery == null){
                return  new Response(404,"Not updated","Grocery details not found for given name");
            }else {
                groceryRepo.updateByName(grocery.getPrice(),grocery.getName());
                return  new Response(200, grocery.toString());
            }
        }else {
            return  new Response(403,"Unauthorized","Only admin can access this functionality");
        }

    }


    @PostMapping("/order")
    public Response orderGrocery(@RequestBody List<Grocery> groceryList){
        boolean flag = false;
        List<Grocery> existingGroceryList = new ArrayList<>();

        List<Grocery> itr = (List<Grocery>) groceryRepo.findAll();
        existingGroceryList=  itr.stream().filter(grocery -> grocery.getQuantity()>0).collect(Collectors.toList());

        for (Grocery grocery : groceryList){
            for (Grocery grocery1 : existingGroceryList){
                if (grocery.getName().equalsIgnoreCase(grocery1.getName())
                        && grocery.getQuantity() <= grocery1.getQuantity()){
                    groceryRepo.updateQuantityById(grocery1.getQuantity()-grocery.getQuantity() , grocery1.getId());
                }else {
                    return new Response(200 , " Order not placed.", "Some Item out of stock!!!");
                };
            }
        }

        return new Response(200 , " Order placed successfully!!!!");
    }
}
