package auto.simulation.constants;

import lombok.Getter;

@Getter
public enum Direction {

    N("North", "N"),
    S("South", "S"),
    W("West", "W"),
    E("East", "E");

    private final String key;
    private final String value;

    Direction(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
