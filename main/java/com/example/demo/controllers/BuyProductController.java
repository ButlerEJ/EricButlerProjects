package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BuyProductController {
    @Autowired
    private ProductRepository prodRepo;
    @PostMapping("/buyProduct")
    public String showBuyProdController(@RequestParam("productId") Long prodId) {
        Optional<Product> prodToBuy = prodRepo.findById(prodId);

        if (prodToBuy.isPresent()) { // If it exists
            Product temp = prodToBuy.get(); //Store it into a temp variable to examine
            if (temp.getInv() > 0) { // If we have units in inv
                temp.setInv(temp.getInv() - 1); // Sell and decrement
                prodRepo.save(temp); //Update the repository for inventory
                return "buyProduct";
            } else {
                return "outOfStock";
            }
        } else {
            return "doesntExist";
        }
    }
}
