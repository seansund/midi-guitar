package com.dex.midi.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamLoader {
    public static InputStream buildInputStream(String fileName, Class<?> clazz) {
        final InputStream is = clazz.getResourceAsStream(fileName);
        if (is == null) {
            throw new IllegalArgumentException(String.format("Unable to load file as stream: %s", fileName));
        }

        return is;
    }

    public static BufferedReader buildBufferedReader(String fileName, Class<?> clazz) {
        return new BufferedReader(new InputStreamReader(buildInputStream(fileName, clazz)));
    }
}
