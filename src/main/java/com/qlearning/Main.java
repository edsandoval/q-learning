package com.qlearning;

import com.qlearning.qtable.QTable;
import com.qlearning.util.Action;
import com.qlearning.util.Policy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int STATE_COUNT  = 9;
    private static int ACTION_COUNT = 4;

    private static int INITIAL_STATE = 9;
    private static int END_STATE     = 1;

    private static int PERIOD_COUNT = 100;
    private static double DISCOUNT_FACTOR = 0.4;

    public static void main(String[] args) {

        Integer[][] baseQ = {
                // Up   Down   Left  Right
                {   0,    0,     0,     0},  // 1 state
                {   0,   -1,   100,   -10},  // 2 state
                {   0,   -1,    -1,     0},  // 3 state
                { 100,  -10,     0,    -1},  // 4 state
                {  -1,   -1,    -1,    -1},  // 5 state
                { -10,   -1,    -1,     0},  // 6 state
                {  -1,    0,     0,    -1},  // 7 state
                {  -1,    0,   -10,    -1},  // 8 state
                {  -1,    0,    -1,     0},  // 9 state
        };
        QTable t = new QTable(STATE_COUNT, ACTION_COUNT);
        t.setHeaderStates("1", "2", "3", "4", "5", "6", "7", "8", "9");
        t.setHeaderActions("Up","Down","Left","Right");

        for(int i=0; i < PERIOD_COUNT; i++) {
            int currentState = INITIAL_STATE;
            List<Action> acumulatedActions = new ArrayList<>();

            while(currentState != END_STATE) {
                List<Action> actions = getPossibleActions(currentState, baseQ) ;

                Action nextAction = Policy.apply(currentState, actions, t);
                Integer nextStatus = getNextStatus(currentState, nextAction);

                Integer reward = baseQ[currentState-1][nextAction.getValue()];

                Double oldQ = t.getValidQ(currentState, nextAction.getValue());
                Double maxQ = t.maxValidQ(nextStatus);
                Double newQ = oldQ + (reward.doubleValue() + DISCOUNT_FACTOR * maxQ - oldQ);

                acumulatedActions.add(nextAction);

                t.setQ(currentState, nextAction.getValue(), newQ);
                currentState = nextStatus;

                t.show();
            }

            System.out.println("END.......");
            System.out.println("Period: " + i);
            showActions(acumulatedActions);
        }

    }

    public static List<Action> getPossibleActions(int currentState, Integer[][] base) {
        List<Action> result = new ArrayList<>();

        Integer[] allActions = base[currentState-1];
        for ( int i=0; i<allActions.length; i++) {
            if(allActions[i] != 0 ) {
                result.add(Action.getAction(i));
            }
        }

        return result;
    }

    public static void showActions(List<Action> acumulatedActions) {
        System.out.print("Actions sequence: ");
        for(Action act : acumulatedActions) {
            if(act == Action.UP) {
                System.out.print("↑");
            } else if(act == Action.DOWN) {
                System.out.print("↓");
            } else if(act == Action.LEFT) {
                System.out.print("←");
            } else if(act == Action.RIGHT) {
                System.out.print("→");
            }
        }
        System.out.println("");
        System.out.println("");
    }

    public static Integer getNextStatus(int currentStatus, Action next) {
        Integer result = currentStatus;

        if(next == Action.UP) {
            if(currentStatus > 3) {
                result = currentStatus - 3;
            }
        } else if(next == Action.DOWN) {
            if(currentStatus < 7) {
                result = currentStatus + 3;
            }
        } else if(next == Action.LEFT) {
            if(currentStatus > 1) {
                result = currentStatus - 1;
            }
        } else if(next == Action.RIGHT) {
            if(currentStatus < 9) {
                result = currentStatus + 1;
            }
        }

        return result;
    }
}
