package pro.tanay.whattoeat.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import pro.tanay.whattoeat.model.MenuItem;

@Repository
public class MenuItemRepository {

    private final ConcurrentMap<Long, MenuItem> menuItems = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public MenuItem save(MenuItem menuItem) {
        if (menuItem.getId() == null) {
            menuItem.setId(idGenerator.incrementAndGet());
        }
        menuItems.put(menuItem.getId(), menuItem);
        return menuItem;
    }

    public List<MenuItem> findAll() {
        return new ArrayList<>(menuItems.values());
    }

    public Optional<MenuItem> findById(Long id) {
        return Optional.ofNullable(menuItems.get(id));
    }

    public void deleteById(Long id) {
        menuItems.remove(id);
    }
}
