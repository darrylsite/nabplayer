package nabster.core;

/**
 * This class represent Event that can occur when playing a sound
 * @author Darryl Kpizingui
 */
public class PlayingEvent {

    public enum Status {

        START, STOP, PAUSE, ERROR;
    }

    private String errorCause;
    Status status;

    public PlayingEvent(Status st) {
        status = st;
    }

    public Status getStatus() {
        return status;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public String getErrorCause() {
        return errorCause;
    }
}
