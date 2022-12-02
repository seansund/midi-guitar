package com.dex.midi.model;

public class GuitarPositions {
    private final GuitarPosition[] positions = new GuitarPosition[6];

    public GuitarPositions() {
    }

    public GuitarPositions(GuitarPosition... positions) {
        this();

        setPositions(positions);
    }

    public GuitarPosition[] getPositions() {
        return positions.clone();
    }

    public void setPositions(GuitarPosition[] positions) {
        if (positions == null) {
            positions = new GuitarPosition[6];
        }

        System.arraycopy(positions, 0, this.positions, 0, Math.min(positions.length, 6));
    }

    public GuitarPosition getPosition(int stringIndex) {
        if (stringIndex < 0 || stringIndex > 5) {
            throw new StringIndexOutOfBoundsException("The stringIndex must be between 0 and 5. Provided: " + stringIndex);
        }

        return positions[stringIndex];
    }

    public void setPosition(int stringIndex, GuitarPosition position) {
        if (stringIndex < 0 || stringIndex > 5) {
            throw new StringIndexOutOfBoundsException("The stringIndex must be between 0 and 5. Provided: " + stringIndex);
        }

        positions[stringIndex] = position;
    }
}
