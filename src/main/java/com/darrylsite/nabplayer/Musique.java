/**
 * @author Kpizingui Darryl
 * mail : nabster@darrylsite.com
 * http://darrylsite.com
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer;

import com.darrylsite.nabplayer.core.PlayerModel;
import com.darrylsite.nabplayer.listener.CommandListener;
import com.darrylsite.nabplayer.view.PlayerView;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Musique {

    public static void main(String[] args) 
    {
        JFrame fen = new JFrame("NabPlayer 1.1");
        
        PlayerModel model = new PlayerModel();
        PlayerView console = new PlayerView(model);
        console.addCommandListener(new CommandListener(model, console));
        console.fen = fen;
        fen.add(console);
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setUndecorated(true);
        fen.setIconImage(console.loadImage("logo.png"));
        fen.setLocation(200, 200);
        fen.setPreferredSize(new Dimension(300, 300));
        fen.pack();
        fen.setVisible(true);

        if(args.length!=0)
        {
            List<File> files = new ArrayList<File>();
            
            for(String s : args)
            {
                files.add(new File(s)); 
            }
            
            console.addFiles((File[])files.toArray());
        }
    }
}
