package models.domain;

public enum Category {
    APPETIZER("Appetizer"),
    MAIN_COURSE("Main Course"),
    DESSERT("Dessert"),
    SALAD("Salad"),
    SOUP("Soup"),
    SIDE_DISH("Side Dish"),
    BEVERAGE("Beverage");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
