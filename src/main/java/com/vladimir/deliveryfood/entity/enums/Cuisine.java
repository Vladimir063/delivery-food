package com.vladimir.deliveryfood.entity.enums;

public enum Cuisine {
    SUSHI("Суши"),
    BURGER("Бургеры"),
    PIZZA("Пицца"),
    MEAT("Мясо"),
    SWEET("Сладкое"),
    SANDWICHES("Cэндвичи");

    private String translate;

    Cuisine(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }
}
