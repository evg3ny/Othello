package org.evgeny.othello.testutil;

public class MultiLineStringBuilder {

    private String expectation = "";

    public MultiLineStringBuilder add(String string) {
        if (!expectation.equals("")){
            expectation+= System.lineSeparator();
        }
        expectation+=string;
        return this;
    }

    public String build() {
        return expectation;
    }
}
