/**
 * @author Kpizingui Darryl
 * mail : nobi_8@hotmail.com
 * http://darrylsite.tk
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package nabplayer.listener;

public interface PlayingListener extends java.util.EventListener {

    enum Status {

        Starting, Ending}

    public void processPlaying(Status st);
}
