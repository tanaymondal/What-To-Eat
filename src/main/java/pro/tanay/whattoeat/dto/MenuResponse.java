package pro.tanay.whattoeat.dto;

import java.time.LocalDate;
import java.util.List;
import pro.tanay.whattoeat.model.MealType;
import pro.tanay.whattoeat.model.MenuItem;

public class MenuResponse {

    private Long id;
    private String restaurantName;
    private String restaurantLocation;
    private LocalDate date;
    private MealType mealType;
    private List<MenuItem> items;

    public MenuResponse() {
    }

    public MenuResponse(Long id, String restaurantName, String restaurantLocation, LocalDate date, MealType mealType,
            List<MenuItem> items) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.date = date;
        this.mealType = mealType;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
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

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}
