package org.evgeny.othello.controller;

public enum Side {
    DARK('X'),
    LIGHT('O'),
    NONE('-');

    private final char character;

    Side(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
