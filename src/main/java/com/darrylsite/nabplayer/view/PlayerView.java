/**
 * @author Kpizingui Darryl
 * mail : nobi_8@hotmail.com
 * http://darrylsite.com
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer.view;

import com.darrylsite.nabplayer.core.PlayerModel;
import com.darrylsite.nabplayer.dnd.FileDrop;
import com.darrylsite.nabplayer.listener.CommandListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import nabplayer.listener.PlayingListener;

public class PlayerView extends JPanel implements MouseListener, MouseMotionListener, PlayingListener {

    private static final long serialVersionUID = 8842723061241526523L;
    public JFrame fen;
    HashMap<String, Point> boutons = new HashMap<String, Point>();
    int dimBt;
    Point dimSc;
    boolean clickOn = false;
    String cmd = null;
    public JScrollPane scrol = new JScrollPane();
    public JPopupMenu popMenu;
    JLabel time;
    Timer timer;
    //affichage du volume
    int[] tx = {200, 256, 256};
    int[] ty = {205, 205, 185};
    Polygon polygone;
//afficheur de la liste de lecture
    public JList songList;
    //les données
    private PlayerModel model;
    ArrayList<CommandListener> cListener = new ArrayList<CommandListener>();

    public PlayerView(PlayerModel model) {
        this.model = model;
        init();
    }

    public PlayerModel getModel() {
        return model;
    }

    

    public void init() {
        this.setOpaque(false);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setCoordinate();
        this.setLayout(null);
        initComponent();
        model.getBoxPlayer().addPlayingListener(this);
    }

    public void addCommandListener(CommandListener lst) {
        cListener.add(lst);
    }

    public void fireCommandListener(String cmd) {
        for (CommandListener c : cListener) {
            c.processCmd(cmd);
        }
    }

    public static Properties getProps()
    {
        Properties props = new Properties();
        InputStream is =
                PlayerView.class.getResourceAsStream("/com/darrylsite/nabplayer/view/skin.properties");
        try {
            props.load(is);
        } catch (IOException e) {
        } finally {
            return props;
        }
    }

    public void addFiles(File[] files)
    {
        if (files == null) {
            return;
        }
        File f;
        for (int i = 0; i < files.length; i++) {
            f = files[i];
            if (f.isDirectory()) {
                addFiles(f.listFiles());
                continue;
            }

            if (!(f.getPath().toLowerCase().endsWith("mp3") || f.getPath().toLowerCase().endsWith("wav")
                    || f.getPath().toLowerCase().endsWith("au") || f.getPath().toLowerCase().endsWith("flv"))) {
                continue;
            }
            model.getSongData().add(f.getAbsolutePath());
            model.getVirtalSongData().add(f.getName());
        }

        songList.setListData(model.getVirtalSongData().toArray());
    }

    private String decodeTime(float tm) {
        int m = (int) (tm / (60 * 1000));
        float t = tm - m * (60 * 1000);
        int s = (int) (t / (1000));
        String st = "" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return st;
    }

    private void setCoordinate() {
        boutons.put("play", new Point(Integer.parseInt(getProps().getProperty("bouton.play.x")),
                Integer.parseInt(getProps().getProperty("bouton.play.y"))));
        boutons.put("stop", new Point(Integer.parseInt(getProps().getProperty("bouton.stop.x")),
                Integer.parseInt(getProps().getProperty("bouton.stop.y"))));
        boutons.put("pause", new Point(Integer.parseInt(getProps().getProperty("bouton.pause.x")),
                Integer.parseInt(getProps().getProperty("bouton.pause.y"))));
        boutons.put("fastbk", new Point(Integer.parseInt(getProps().getProperty("bouton.fastbk.x")),
                Integer.parseInt(getProps().getProperty("bouton.fastbk.y"))));
        boutons.put("fastwd", new Point(Integer.parseInt(getProps().getProperty("bouton.fastwd.x")),
                Integer.parseInt(getProps().getProperty("bouton.fastwd.y"))));
        boutons.put("vol", new Point(Integer.parseInt(getProps().getProperty("bouton.vol.x")),
                Integer.parseInt(getProps().getProperty("bouton.vol.y"))));
        boutons.put("clock", new Point(Integer.parseInt(getProps().getProperty("bouton.clock.x")),
                Integer.parseInt(getProps().getProperty("bouton.clock.y"))));
        boutons.put("about", new Point(Integer.parseInt(getProps().getProperty("bouton.about.x")),
                Integer.parseInt(getProps().getProperty("bouton.about.y"))));
        boutons.put("up", new Point(Integer.parseInt(getProps().getProperty("bouton.up.x")),
                Integer.parseInt(getProps().getProperty("bouton.up.y"))));
        boutons.put("down", new Point(Integer.parseInt(getProps().getProperty("bouton.down.x")),
                Integer.parseInt(getProps().getProperty("bouton.down.y"))));
        boutons.put("screen", new Point(Integer.parseInt(getProps().getProperty("bouton.screen.x")),
                Integer.parseInt(getProps().getProperty("bouton.screen.y"))));
        dimBt = Integer.parseInt(getProps().getProperty("bouton.width"));
        dimSc = new Point(Integer.parseInt(getProps().getProperty("screen.width")),
                Integer.parseInt(getProps().getProperty("screen.height")));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image pic = loadImage("media.png");
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(pic, 0, 0, this);

        if (clickOn) {
            Point pt = (Point) boutons.get(cmd);
            g2d.setColor(Color.yellow);
            g2d.drawRoundRect(pt.x - 2, pt.y - 2, dimBt + 4, dimBt + 4, 2, 2);
        }
        g2d.setColor(Color.gray);
        polygone = new Polygon(tx, ty, 3);
        g2d.drawPolygon(polygone);
        g2d.setColor(Color.yellow);
        if (model.getBoxPlayer().play != null) {
            float x = tx[0] * (model.getMaxVol() - model.getVolume()) - tx[2] * (model.getMinVol() - model.getVolume());
            x = x / (model.getMaxVol() - model.getMinVol());
            float y = ((ty[2] - ty[0]) * (x - tx[0])) / (tx[2] - tx[0]) + ty[0] + 2;
            int[] tx2 = {200, (int) x, (int) x};
            int[] ty2 = {205, 205, (int) y};
            Polygon pol2 = new Polygon(tx2, ty2, 3);
            g2d.fillPolygon(pol2);
        }

        clickOn = false;
    }

    public Image loadImage(String img) {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/" + img));
    }

    private void processedCmd(String cmd) {
        fireCommandListener(cmd);
    }

    private void initComponent() {
        time = new JLabel("00:00:00");
        time.setOpaque(false);
        time.setForeground(Color.yellow);
        time.setBounds(110, 188, 70, 20);
        this.add(time);
        /******************************/
        ActionListener taskPerformer = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (model.isStop()) {
                    time.setText("00:00:00");
                    return;
                }

                if (model.isPlaying()) {
                    float t1 = model.getBoxPlayer().totalms;
                    time.setText(decodeTime(model.getBoxPlayer().play.getPosition()) + "/" + decodeTime(t1));
                }
            }
        };
        timer = new Timer(500, taskPerformer);
        timer.setRepeats(true);
        timer.start();

        /***********************************************/
        new FileDrop(this, new FileDrop.Listener() {

            public void filesDropped(File[] files) {
                addFiles(files);
            }
        });
        /********************************/
        popMenu = new PopUpMenu(this);

        /*******************************/
        songList = new SongList(model, this);
        scrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrol.setBounds(20, 30, 240, 140);
        scrol.setViewportView(songList);
        scrol.getViewport().setOpaque(false);
        scrol.getViewport().setBorder(null);
        scrol.setBorder(null);
        scrol.setOpaque(false);
        add(scrol);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Iterator<String> iter = boutons.keySet().iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            Point pt = (Point) boutons.get(s);
            if ((pt.x < x) && (pt.x + dimBt) > x) {
                if (pt.y < y && (pt.y + dimBt) > y) {
                    this.processedCmd(s);
                    return;
                }
            }
        }

        if (polygone.contains(x, y)) {
            model.setVolume((model.getMinVol() * (tx[2] - x) - model.getMaxVol() * (tx[0] - x)) / (tx[2] - tx[0]));
            repaint();

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        ptClick.setLocation(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pt = new Point(e.getXOnScreen(), e.getYOnScreen());
        pt.setLocation(pt.x-ptClick.x, pt.y-ptClick.y);
        fen.setLocation(pt);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Iterator<String> iter = boutons.keySet().iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.equals("clock") || s.equals("screen")) {
                continue;
            }
            Point pt = (Point) boutons.get(s);
            if ((pt.x < x) && (pt.x + dimBt) > x) {
                if (pt.y < y && (pt.y + dimBt) > y) {
                    cmd = s;
                    clickOn = true;
                }
            }
        }
        this.repaint();
    }

    public void processPlaying(Status st) {
        if (st.equals(Status.Ending)) {
            if (model.getCurIndex() == model.getSongData().size() - 1) {
                this.songList.setSelectedIndex(0);
                processedCmd("play");
            } else {
                processedCmd("play");
                processedCmd("fastwd");
            }

        } else {
            model.setVolume(model.getVolume());
        }
    }
    Point ptClick = new Point(0, 0);
}
