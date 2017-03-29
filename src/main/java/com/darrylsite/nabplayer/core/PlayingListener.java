package nabster.core;

/**
 * Listener for handling event ocurring during a sound plays
 * @author Darryl Kpizingui
 */
public interface PlayingListener {

    public void startPlaying(PlayingEvent evt);

    public void StopPlaying(PlayingEvent evt);

    public void ErrorPlaying(PlayingEvent evt);
}
