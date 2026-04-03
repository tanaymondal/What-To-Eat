package pro.tanay.whattoeat.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import pro.tanay.whattoeat.model.Restaurant;

@Repository
public class RestaurantRepository {

    private final ConcurrentMap<Long, Restaurant> restaurants = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            restaurant.setId(idGenerator.incrementAndGet());
        }
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public List<Restaurant> findAll() {
        return new ArrayList<>(restaurants.values());
    }

    public Optional<Restaurant> findById(Long id) {
        return Optional.ofNullable(restaurants.get(id));
    }

    public void deleteById(Long id) {
        restaurants.remove(id);
    }
}
