package com.darrylsite.nabplayer.view;

import com.darrylsite.nabplayer.core.PlayList;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PopUpMenu extends JPopupMenu {

    JMenuItem item = null;
    PlayerView parent;

    public PopUpMenu(PlayerView parent) {
        this.parent = parent;
        init();
    }

    private void init() {
        setBackground(new Color(0x85, 0x95, 0xD3));
        item = new JMenuItem("Ajouter mp3");
        item.setBackground(new Color(0x85, 0x95, 0xD3));
        item.setForeground(new Color(0x00, 0x00, 0xC1));
        item.addActionListener(afficherMenuListener);
        add(item);
        item = new JMenuItem("Play list");
        item.addActionListener(afficherMenuListener);
        item.setBackground(new Color(0x85, 0x95, 0xD3));
        item.setForeground(new Color(0x00, 0x00, 0xC1));
        add(item);
        item = new JMenuItem("A propos");
        item.addActionListener(afficherMenuListener);
        item.setBackground(new Color(0x85, 0x95, 0xD3));
        item.setForeground(new Color(0x00, 0x00, 0xC1));
        add(item);
        item = new JMenuItem("Quitter");
        item.addActionListener(afficherMenuListener);
        item.setBackground(new Color(0x85, 0x95, 0xD3));
        item.setForeground(new Color(0x00, 0x00, 0xC1));
        add(item);

    }

    public Image loadImage(String img) {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/" + img));
    }
    ActionListener afficherMenuListener = new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equals("Ajouter mp3")) {
                JFileChooser fc = new JFileChooser();
                fc.setDragEnabled(true);
                FileFilter filter = new FileNameExtensionFilter("fichier mp3", "mp3", "MP3");
                fc.addChoosableFileFilter(filter);
                fc.setMultiSelectionEnabled(true);
                fc.showOpenDialog(parent);
                parent.addFiles(fc.getSelectedFiles());
            } else if (event.getActionCommand().equals("Quitter"))
            {
                if(!parent.getModel().getSongData().isEmpty())
                 PlayList.save((String[])parent.getModel().getSongData().toArray(new String[0]));

                Runtime.getRuntime().exit(0);
            }
            else if (event.getActionCommand().equals("A propos")) {
                JDialog dialog = new JDialog();
                dialog.setTitle("A Propos");
                JLabel label = new JLabel("");
                label.setBounds(0, 0, 280, 180);
                label.setIcon(new ImageIcon(loadImage("about.png")));
                dialog.add(label);
                dialog.setLayout(null);
                dialog.setSize(288, 210);
                dialog.setResizable(false);
                dialog.setModal(true);
                dialog.setLocation(parent.getLocationOnScreen());
                dialog.setVisible(true);
            }
             else if (event.getActionCommand().equals("Play list")) {

                 int t=  parent.getModel().getVirtalSongData().size();
                 for(int i=0; i<t; i++)
                 {
                    parent.getModel().getSongData().remove(0);
                    parent.getModel().getVirtalSongData().remove(0);
                 }
                parent.addFiles(PlayList.load());
            }

        }
    };
}
