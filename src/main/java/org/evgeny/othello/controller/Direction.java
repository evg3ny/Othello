package org.evgeny.othello.controller;

public enum Direction {
    UP(-1,0),
    UP_RIGHT(-1,1),
    RIGHT(0,1),
    DOWN_RIGHT(1,1),
    DOWN(1,0),
    DOWN_LEFT(1,-1),
    LEFT(0,-1),
    UP_LEFT(-1,-1);

    private final int rowStep;
    private final int columnStep;

    Direction(int rowStep, int columnStep) {
        this.rowStep = rowStep;
        this.columnStep = columnStep;
    }

    public int getRowStep() {
        return rowStep;
    }

    public int getColumnStep() {
        return columnStep;
    }
}
