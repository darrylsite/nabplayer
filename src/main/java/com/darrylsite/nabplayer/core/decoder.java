package com.darrylsite.nabplayer.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

public class decoder {

    private FileInputStream mp3in = null;
    private Bitstream in = null;
    private String son;

    public decoder(String son) {
        this.son = son;
        try {
            mp3in = new FileInputStream(son);
            in = new Bitstream(mp3in);

        } catch (Exception e) {
            logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        }
    }

    public float info() {
        try {

            InputStream id3in = in.getRawID3v2();
            int size = id3in.available();
            Header header = in.readFrame();
            return header.total_ms(mp3in.available());

        } catch (Exception e)
        {
           logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        }
        return 0;
    }
}
