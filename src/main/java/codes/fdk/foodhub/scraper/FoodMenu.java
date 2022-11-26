package codes.fdk.foodhub.scraper;

import java.util.Collection;

import static codes.fdk.foodhub.scraper.FoodMenu.SuppemagbrotFoodMenu;

public sealed interface FoodMenu permits SuppemagbrotFoodMenu {

    Collection<Dish> dishes();

    record SuppemagbrotFoodMenu(Collection<Dish> dishes) implements FoodMenu {}

    record Dish(String name) {}

}
