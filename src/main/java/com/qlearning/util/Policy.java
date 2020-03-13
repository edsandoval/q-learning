package com.qlearning.util;

import com.qlearning.qtable.QTable;

import java.util.List;
import java.util.Random;

public class Policy {

    public static Action apply(Integer currentState,
                              List<Action> possibleActions,
                              QTable _qTable) {
        Action selectedAction = null;

        Double max = 0.0;
        for(Action _act : possibleActions) {
            Double _num = _qTable.getQ(currentState, _act.getValue());
            if(_num > max) {
                selectedAction = _act;
                max = _num;
            }
        }

        if(selectedAction == null) {
            int indexAction = random(0, possibleActions.size() - 1);
            selectedAction = possibleActions.get(indexAction);
        }

        return selectedAction;
    }

    private static int random(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
