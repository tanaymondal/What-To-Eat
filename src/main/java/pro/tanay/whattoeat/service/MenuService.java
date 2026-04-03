package pro.tanay.whattoeat.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import pro.tanay.whattoeat.dto.MenuResponse;
import pro.tanay.whattoeat.exception.DuplicateResourceException;
import pro.tanay.whattoeat.exception.InvalidRequestException;
import pro.tanay.whattoeat.exception.ResourceNotFoundException;
import pro.tanay.whattoeat.model.Menu;
import pro.tanay.whattoeat.model.MenuItem;
import pro.tanay.whattoeat.model.Restaurant;
import pro.tanay.whattoeat.repository.MenuRepository;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public MenuService(MenuRepository menuRepository, RestaurantService restaurantService,
            MenuItemService menuItemService) {
        this.menuRepository = menuRepository;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    public MenuResponse createMenu(Menu menu) {
        validateMenu(menu, null);
        menu.setId(null);
        Menu savedMenu = menuRepository.save(copyMenu(menu));
        return toResponse(savedMenu);
    }

    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuResponse getMenuById(Long id) {
        return toResponse(getMenuEntityById(id));
    }

    public List<MenuResponse> getMenusByRestaurant(Long restaurantId) {
        restaurantService.getRestaurantById(restaurantId);
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MenuResponse> getMenusByDate(LocalDate date) {
        if (date == null) {
            throw new InvalidRequestException("Menu date must not be null");
        }
        return menuRepository.findByDate(date).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MenuResponse> getMenusByRestaurantAndDate(Long restaurantId, LocalDate date) {
        restaurantService.getRestaurantById(restaurantId);
        if (date == null) {
            throw new InvalidRequestException("Menu date must not be null");
        }
        return menuRepository.findByRestaurantIdAndDate(restaurantId, date).stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuResponse updateMenu(Long id, Menu menu) {
        Menu existingMenu = getMenuEntityById(id);
        validateMenu(menu, id);
        existingMenu.setRestaurantId(menu.getRestaurantId());
        existingMenu.setDate(menu.getDate());
        existingMenu.setMealType(menu.getMealType());
        existingMenu.setMenuItemIds(List.copyOf(menu.getMenuItemIds()));
        Menu savedMenu = menuRepository.save(existingMenu);
        return toResponse(savedMenu);
    }

    public void deleteMenu(Long id) {
        getMenuEntityById(id);
        menuRepository.deleteById(id);
    }

    private Menu getMenuEntityById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
    }

    private void validateMenu(Menu menu, Long currentMenuId) {
        if (menu == null) {
            throw new InvalidRequestException("Menu request body is required");
        }
        if (menu.getRestaurantId() == null) {
            throw new InvalidRequestException("Menu restaurantId must not be null");
        }
        if (menu.getDate() == null) {
            throw new InvalidRequestException("Menu date must not be null");
        }
        if (menu.getMealType() == null) {
            throw new InvalidRequestException("Menu mealType must not be null");
        }
        if (menu.getMenuItemIds() == null || menu.getMenuItemIds().isEmpty()) {
            throw new InvalidRequestException("Menu item IDs must not be null or empty");
        }

        restaurantService.getRestaurantById(menu.getRestaurantId());
        for (Long menuItemId : menu.getMenuItemIds()) {
            if (menuItemId == null) {
                throw new InvalidRequestException("Menu item IDs must not contain null values");
            }
            menuItemService.getMenuItemById(menuItemId);
        }

        menuRepository.findByRestaurantIdAndDateAndMealType(
                        menu.getRestaurantId(), menu.getDate(), menu.getMealType())
                .filter(existingMenu -> !existingMenu.getId().equals(currentMenuId))
                .ifPresent(existingMenu -> {
                    throw new DuplicateResourceException(
                            "Menu already exists for restaurantId=" + menu.getRestaurantId()
                                    + ", date=" + menu.getDate()
                                    + ", mealType=" + menu.getMealType());
                });
    }

    private MenuResponse toResponse(Menu menu) {
        Restaurant restaurant = restaurantService.getRestaurantById(menu.getRestaurantId());
        List<MenuItem> items = menu.getMenuItemIds().stream()
                .map(menuItemService::getMenuItemById)
                .toList();
        return new MenuResponse(
                menu.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                menu.getDate(),
                menu.getMealType(),
                items);
    }

    private Menu copyMenu(Menu menu) {
        return new Menu(
                menu.getId(),
                menu.getRestaurantId(),
                menu.getDate(),
                menu.getMealType(),
                List.copyOf(menu.getMenuItemIds()));
    }
}
