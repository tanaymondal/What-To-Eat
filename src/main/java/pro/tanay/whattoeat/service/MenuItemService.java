package pro.tanay.whattoeat.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pro.tanay.whattoeat.exception.InvalidRequestException;
import pro.tanay.whattoeat.exception.ResourceNotFoundException;
import pro.tanay.whattoeat.model.MenuItem;
import pro.tanay.whattoeat.repository.MenuItemRepository;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        validateMenuItem(menuItem);
        menuItem.setId(null);
        menuItem.setName(menuItem.getName().trim());
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existingMenuItem = getMenuItemById(id);
        validateMenuItem(menuItem);
        existingMenuItem.setName(menuItem.getName().trim());
        existingMenuItem.setDescription(menuItem.getDescription());
        existingMenuItem.setPrice(menuItem.getPrice());
        existingMenuItem.setVegetarian(menuItem.isVegetarian());
        existingMenuItem.setCategory(menuItem.getCategory());
        return menuItemRepository.save(existingMenuItem);
    }

    public void deleteMenuItem(Long id) {
        getMenuItemById(id);
        menuItemRepository.deleteById(id);
    }

    private void validateMenuItem(MenuItem menuItem) {
        if (menuItem == null) {
            throw new InvalidRequestException("Menu item request body is required");
        }
        if (menuItem.getName() == null || menuItem.getName().trim().isEmpty()) {
            throw new InvalidRequestException("Menu item name must not be null or blank");
        }
        if (menuItem.getPrice() <= 0) {
            throw new InvalidRequestException("Menu item price must be greater than 0");
        }
        if (menuItem.getCategory() == null) {
            throw new InvalidRequestException("Menu item category must not be null");
        }
    }
}
