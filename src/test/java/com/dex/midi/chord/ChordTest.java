package com.dex.midi.chord;

import com.dex.midi.model.Pitch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Chord test")
public class ChordTest {
  @Nested
  @DisplayName("Given identifyChords()")
  class IdentifyChords {
    @Nested
    @DisplayName("when input is empty")
    class WhenInputIsEmpty {
      @Test
      @DisplayName("then return empty list of chords")
      void thenReturnEmptyList() {
        final List<Chord> actual = Chord.identifyChords(new ArrayList<>());

        assertEquals(actual, new ArrayList<>());
      }
    }

    @Nested
    @DisplayName("when input has multiple items")
    class WhenInputHasMultipleItems {
      @Test
      @DisplayName("should return chords from notes")
      void shouldReturnChordsFromNotes() {
        final List<StringPitch> input = Arrays.asList(
            new StringPitch(0, Pitch.determinePitch(196)),
            new StringPitch(1, Pitch.determinePitch(246.94)),
            new StringPitch(1, Pitch.determinePitch(293.66))
        );
        final List<Chord> actual = Chord.identifyChords(input);

        assertNotNull(actual, "Chord list should not be null");
        assertTrue(!actual.isEmpty(), "Chord list should not be empty");
        assertEquals(actual.getFirst().toString(), "G");
      }
    }
  }
}
