package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDeathNote {

    private static final String NAME = "Lorenzo";
    private static final String RANDOM_NAME = "Francesco";
    private static final String EMPTY_STRING = "";
    private static final String HEART_ATTACK = "heart attack";
    private static final String KARTING_ACCIDENT = "karting accident";
    private static final long MILLIS_SLEEP100 = 100;
    private static final String JUMPSCARE = "jumpscare";
    private static final String DETAIL = "ran for too long";
    private static final long MILLIS_SLEEP6100 = 6100;
    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImpl();
    }

    @Test
    void testRules() {
        final int[] invalidIndex = {0, -1, DeathNote.RULES.size() + 1};
        for (final int i: invalidIndex) {
            try {
                deathNote.getRule(i);
            } catch (final IllegalArgumentException e) {
                assertNotNull(e.getMessage());
                assertFalse(e.getMessage().isBlank());
            }
        }
    }

    @Test
    void testRuleNotEmpty() {
        for (final String rule: DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testHumanIsWriten() {
        assertFalse(deathNote.isNameWritten(null));
        deathNote.writeName(NAME);
        assertTrue(deathNote.isNameWritten(NAME));
        assertFalse(deathNote.isNameWritten(RANDOM_NAME));
        assertFalse(deathNote.isNameWritten(EMPTY_STRING));
    }

    @Test 
    void testCauseOfDeath() throws InterruptedException {
        try {
            deathNote.writeDeathCause(HEART_ATTACK);
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }

        deathNote.writeName(NAME);
        assertEquals(HEART_ATTACK, deathNote.getDeathCause(NAME));

        deathNote.writeName(RANDOM_NAME);
        assertTrue(deathNote.writeDeathCause(KARTING_ACCIDENT));
        assertEquals(KARTING_ACCIDENT, deathNote.getDeathCause(RANDOM_NAME));

        Thread.sleep(MILLIS_SLEEP100);
        assertFalse(deathNote.writeDeathCause(JUMPSCARE));
        assertEquals(KARTING_ACCIDENT, deathNote.getDeathCause(RANDOM_NAME));
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            deathNote.writeDetails(DETAIL);
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }

        deathNote.writeName(NAME);
        assertEquals(EMPTY_STRING, deathNote.getDeathDetails(NAME));
        assertTrue(deathNote.writeDetails(DETAIL));
        assertEquals(DETAIL, deathNote.getDeathDetails(NAME));

        deathNote.writeName(RANDOM_NAME);
        Thread.sleep(MILLIS_SLEEP6100);
        assertFalse(deathNote.writeDetails(DETAIL));
        assertEquals(EMPTY_STRING, deathNote.getDeathDetails(RANDOM_NAME));
    }
}
