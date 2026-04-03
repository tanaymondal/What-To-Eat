package pro.tanay.whattoeat.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pro.tanay.whattoeat.exception.InvalidRequestException;
import pro.tanay.whattoeat.exception.ResourceNotFoundException;
import pro.tanay.whattoeat.model.Restaurant;
import pro.tanay.whattoeat.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        validateRestaurant(restaurant);
        restaurant.setId(null);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = getRestaurantById(id);
        validateRestaurant(restaurant);
        existingRestaurant.setName(restaurant.getName().trim());
        existingRestaurant.setLocation(restaurant.getLocation().trim());
        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long id) {
        getRestaurantById(id);
        restaurantRepository.deleteById(id);
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new InvalidRequestException("Restaurant request body is required");
        }
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new InvalidRequestException("Restaurant name must not be null or blank");
        }
        if (restaurant.getLocation() == null || restaurant.getLocation().trim().isEmpty()) {
            throw new InvalidRequestException("Restaurant location must not be null or blank");
        }
    }
}
