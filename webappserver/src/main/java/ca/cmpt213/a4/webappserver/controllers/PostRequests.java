package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.DrinkInfo;
import ca.cmpt213.a4.webappserver.model.FoodInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PostRequests {

    @PostMapping("/addFood")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addFood(@RequestBody FoodInfo consumable) {
        ConsumableManager.addItem(consumable);
        return ConsumableManager.allItems();
    }

    @PostMapping("/addDrink")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addDrink(@RequestBody DrinkInfo consumable) {
        ConsumableManager.addItem(consumable);
        return ConsumableManager.allItems();
    }

    @PostMapping("/removeFood")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> removeFood(@RequestBody FoodInfo consumable) {
        ConsumableManager.removeItem(consumable);
        return ConsumableManager.allItems();
    }

    @PostMapping("/removeDrink")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> removeDrink(@RequestBody DrinkInfo consumable) {
        ConsumableManager.removeItem(consumable);
        return ConsumableManager.allItems();
    }

}
