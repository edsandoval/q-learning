package com.qlearning.qtable;

import java.util.ArrayList;
import java.util.List;

public class QTable {
    private String[] hActions;
    private String[] hStates;
    private List<List<Double>> _sa;
    private final Double DEFAULT = 0.0;

    public QTable(int sCount, int aCount) {
        this.initialize(sCount, aCount);
    }

    private void initialize(int sCount, int aCount) {
        this._sa = new ArrayList<>();
        for (int i = 0; i<sCount; i++) {
            List<Double> actions = new ArrayList<>();
            for (int j = 0; j<aCount; j++) {
                actions.add(DEFAULT);
            }
            this._sa.add(actions);
        }
    }

    public void setHeaderActions(String... hActions) {
        this.hActions = hActions;
    }

    public void setHeaderStates(String... hStates) {
        this.hStates = hStates;
    }

    public void setQ(int sIndex, int aIndex, Double q) {
        this._sa.get(sIndex-1).set(aIndex, q);
    }

    public Double getQ(int sIndex, int aIndex) {
        return this._sa.get(sIndex-1).get(aIndex);
    }

    public Double getValidQ(int sIndex, int aIndex) {
        Double result = getQ(sIndex, aIndex);
        return (result == null) ? 0.0 : result;
    }

    public Double maxQ(int sIndex) {
        Double result = Double.NEGATIVE_INFINITY;
        List<Double> vec = this._sa.get(sIndex-1);
        for (Double val : vec) {
            if(val != null) {
                result = Math.max(val, result);
            }
        }
        return result;
    }

    public Double maxValidQ(int sIndex) {
        Double result = maxQ(sIndex);
        return (result == Double.NEGATIVE_INFINITY) ? 0.0 : result;
    }

    public Double minQ(int sIndex) {
        Double result = Double.POSITIVE_INFINITY;
        List<Double> vec = this._sa.get(sIndex-1);
        for (Double val : vec) {
            if(val != null) {
                result = Math.min(val, result);
            }
        }
        return result;
    }

    public void show() {
        System.out.print("      ");
        for(int r = 0; r<hActions.length; r++) {
            System.out.print(hActions[r]);
            System.out.print("          ");
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------");

        for (int i = 0; i<this._sa.size(); i++) {
            System.out.print(this.hStates[i] + "|   ");
            for (int j = 0; j<this._sa.get(i).size(); j++) {
                System.out.print(this._sa.get(i).get(j));
                System.out.print("          ");
            }
            System.out.println("");
        }
    }
}
