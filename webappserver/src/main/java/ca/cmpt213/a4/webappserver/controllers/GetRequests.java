package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class GetRequests {

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String PingBack() {
        return "System is up!";
    }

    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> listAllItems() {
        return ConsumableManager.allItems();
    }

    @GetMapping("/listExpired")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> listExpiredItems() {
        return ConsumableManager.expiredItems();
    }

    @GetMapping("/listNonExpired")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> listNonExpiredItems() {
        return ConsumableManager.nonExpiredItems();
    }

    @GetMapping("/listExpiringIn7Days")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> listInSevenDays() {
        return ConsumableManager.inSevenDays();
    }

    @GetMapping("/exit")
    @ResponseStatus(HttpStatus.OK)
    public void saveItems() {
        ConsumableManager.saveConsumables();
    }

}
