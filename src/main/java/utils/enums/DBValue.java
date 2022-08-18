package utils.enums;

import lombok.Getter;

public enum DBValue {
    URI("/uri"),
    USER("/user"),
    PASSWORD("/password");

    @Getter
    private final String value;

    DBValue(String value) {
        this.value = value;
    }
}