package me.feehgamer.msc.shared;

public enum ErrorEnum {
    SYNTAX_ERROR("&cInvalid Syntax!\n&7The valid syntax is: &6{SYNTAX}");

    private final String[] message;

    ErrorEnum(String... message) {
        this.message = message;
    }

    public String[] getMessage() {
        return this.message;
    }
}
