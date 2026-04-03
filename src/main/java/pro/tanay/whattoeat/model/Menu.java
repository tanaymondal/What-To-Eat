package pro.tanay.whattoeat.model;

import java.time.LocalDate;
import java.util.List;

public class Menu {

    private Long id;
    private Long restaurantId;
    private LocalDate date;
    private MealType mealType;
    private List<Long> menuItemIds;

    public Menu() {
    }

    public Menu(Long id, Long restaurantId, LocalDate date, MealType mealType, List<Long> menuItemIds) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
        this.mealType = mealType;
        this.menuItemIds = menuItemIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
