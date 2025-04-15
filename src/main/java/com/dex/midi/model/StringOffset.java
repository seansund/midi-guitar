package com.dex.midi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class StringOffset {

    private int stringIndex;
    private int value;

    public StringOffset() {
    }

    public StringOffset(int stringIndex, int value) {
        this.stringIndex = stringIndex;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringOffset that = (StringOffset) o;
        return stringIndex == that.stringIndex && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringIndex, value);
    }
}
