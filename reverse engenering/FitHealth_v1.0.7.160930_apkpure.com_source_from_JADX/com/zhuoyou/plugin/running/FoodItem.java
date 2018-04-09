package com.zhuoyou.plugin.running;

public class FoodItem {
    private int calories;
    private String food;

    public void setName(String food) {
        this.food = food;
    }

    public String getName() {
        return this.food;
    }

    public void setCal(int cal) {
        this.calories = cal;
    }

    public int getCal() {
        return this.calories;
    }
}
