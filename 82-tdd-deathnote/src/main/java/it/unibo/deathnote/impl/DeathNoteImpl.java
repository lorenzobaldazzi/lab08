package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * la classe implementa DeathNote.
 */

public final class DeathNoteImpl implements DeathNote {
    private final Map<String, Death> deaths;
    private String lastWrittenName;

    /**
     * creazione del libro.
     */

    public DeathNoteImpl() {
        deaths = new LinkedHashMap<>();
        lastWrittenName = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("rule number deve essere >= 1 e <= rules.size");
        }
        final int i = ruleNumber;
        return RULES.get(i - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("il nome non può essere bianco");
        }
        deaths.put(name, new Death(System.currentTimeMillis()));
        lastWrittenName = name;
    }

    /**
     * sostituisce cause e details.
     * 
     * @param cause la nuova causa di morte
     * @param details i nuovi dettagli di morte
     * @return true se l'operazione si è conclusa correttamente, false altrimenti
     */
    public boolean updateLastDeath(final String cause, final String details) {
        if (this.lastWrittenName == null || !deaths.containsKey(lastWrittenName)) {
            return false;
        }
        final Death current = deaths.get(lastWrittenName);
        Death update = current;

        if (cause != null) {
            update = update.updateCause(cause);
        }

        if (details != null) {
            update = update.updateDetails(details);
        }

        if (Objects.equals(current, update)) {
            return false;
        }

        deaths.put(lastWrittenName, update);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("cause non può essere null");
        }
        return updateLastDeath(cause, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("details non puo essere null");
        }
        return updateLastDeath(null, details);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {

        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("il nome non puo essere null");
        }
        return deaths.get(name).cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("il nome non puo essere null");
        }
        return deaths.get(name).details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return deaths.containsKey(name);
    }

    private static final class Death {

        private static final String DEFAULT_CAUSE = "heart attack";
        private static final String DEFAULT_DETAIL = "";
        private static final long CAUSE_TIMEOUT = 40;
        private static final long DETAILS_TIMEOUT = 6000 + CAUSE_TIMEOUT;
        private final String cause;
        private final String details;
        private final long timeOfWrite;

        Death(final String cause, final String details, final long timeOfWrite) {
            this.cause = cause;
            this.details = details;
            this.timeOfWrite = timeOfWrite;
        }

        Death(final long timeOfDeath) {
            this(DEFAULT_CAUSE, DEFAULT_DETAIL, timeOfDeath);
        }

        public Death updateCause(final String newCause) {
            if (System.currentTimeMillis() < timeOfWrite + CAUSE_TIMEOUT) {
                return new Death(newCause, this.details, System.currentTimeMillis());
            }
            return this;
        }

        public Death updateDetails(final String newDetails) {
             if (System.currentTimeMillis() < timeOfWrite + DETAILS_TIMEOUT) {
                return new Death(this.cause, newDetails, System.currentTimeMillis());
            }
            return this;
        }
    }
}
