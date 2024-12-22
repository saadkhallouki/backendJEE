package com.example.JEE.controllers;

import com.example.JEE.config.JwtService;
import com.example.JEE.entities.Restaurant;
import com.example.JEE.entities.User;
import com.example.JEE.repositories.UserRepository;
import com.example.JEE.services.RestaurantService;
import com.example.JEE.services.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private StorageService storageService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping
    public Restaurant createRestaurant(@RequestParam("Name") String name,@RequestParam("Localisation")
                                        String localisation, @RequestParam("file") MultipartFile file
            , HttpServletRequest httpServletRequest)
            throws IOException {
        String token=httpServletRequest.getHeader("Authorization").substring(7);
        String email=jwtService.extractEmail(token);
        User user = userRepository.findUtilisateurByEmail(email).get();
        Restaurant restaurant=new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(localisation);
        restaurant.setImgUrl(storageService.uploadImageToFileSystem(file));
        user.setRestaurant(restaurant);
        return restaurantService.createRestaurant(restaurant);
    }


    @GetMapping()
    public Restaurant getRestaurantById( HttpServletRequest httpServletRequest) {
        String token=httpServletRequest.getHeader("Authorization").substring(7);
        String email=jwtService.extractEmail(token);
        User user = userRepository.findUtilisateurByEmail(email).get();


        return user.getRestaurant();
    }

    //@GetMapping
    //public List<Restaurant> getAllRestaurants() {
      //  return restaurantService.getAllRestaurants();
    //}

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(
            @PathVariable int id,
            @RequestParam("Name") String name,
            @RequestParam("Localisation") String localisation,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Restaurant restaurant = restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setName(name);
        restaurant.setLocation(localisation);

        if (file != null && !file.isEmpty()) {
            restaurant.setImgUrl(storageService.uploadImageToFileSystem(file));
        }

        return restaurantService.createRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable int id) {
        restaurantService.deleteRestaurant(id);
    }
}
