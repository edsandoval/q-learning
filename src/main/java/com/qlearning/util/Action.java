package com.qlearning.util;

public enum Action {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private final Integer value;

    Action(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Action getAction(Integer value) {
        Action action = Action.RIGHT;
        if(value == 0) {
            action = Action.UP;
        } else if(value == 1) {
            action = Action.DOWN;
        } else if(value == 2) {
            action = Action.LEFT;
        }
        return action;
    }

}
