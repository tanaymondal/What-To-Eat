package pro.tanay.whattoeat.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import pro.tanay.whattoeat.model.MealType;
import pro.tanay.whattoeat.model.Menu;

@Repository
public class MenuRepository {

    private final ConcurrentMap<Long, Menu> menus = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(idGenerator.incrementAndGet());
        }
        menus.put(menu.getId(), menu);
        return menu;
    }

    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(menus.get(id));
    }

    public List<Menu> findByRestaurantId(Long restaurantId) {
        return menus.values().stream()
                .filter(menu -> menu.getRestaurantId().equals(restaurantId))
                .toList();
    }

    public List<Menu> findByDate(LocalDate date) {
        return menus.values().stream()
                .filter(menu -> menu.getDate().equals(date))
                .toList();
    }

    public List<Menu> findByRestaurantIdAndDate(Long restaurantId, LocalDate date) {
        return menus.values().stream()
                .filter(menu -> menu.getRestaurantId().equals(restaurantId) && menu.getDate().equals(date))
                .toList();
    }

    public Optional<Menu> findByRestaurantIdAndDateAndMealType(Long restaurantId, LocalDate date, MealType mealType) {
        return menus.values().stream()
                .filter(menu -> menu.getRestaurantId().equals(restaurantId)
                        && menu.getDate().equals(date)
                        && menu.getMealType() == mealType)
                .findFirst();
    }

    public void deleteById(Long id) {
        menus.remove(id);
    }
}
