package com.vladimir.deliveryfood.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public  class CreateSort {

    private CreateSort() {
    }

    public static Sort getSort(String directionSort, String nameField) {
        Direction sortDirection = getSortDirection(directionSort);
        Order order = new Order(sortDirection, nameField);
        return Sort.by(order);
    }

    private static Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Direction.ASC;
        } else if (direction.equals("desc")) {
            return Direction.DESC;
        }
        return Direction.ASC;
    }

}
