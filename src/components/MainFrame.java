package components;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

import java.awt.*;
import java.io.File;


public class MainFrame extends JFrame {
    private ImageIcon logo;
    public Container main_page;
    public int CENTER;
    public ControlsPanel controls_panel;
    public JPanel playlist_panel; 
    public TopMenuBar top_menu_bar;
    public ImageIcon backgroundIcon;
    public int loopcounter = 1, musicInPlaylist = 0, choice;
    public AudioInputStream audioStream;
    public JLabel musicLabel, playlist_label, TitlePlaylist;
    public static float currentVolume = 0, lastRecordedVolume = 0;
    public static long timeLineTracker = 0, currentInMicro;
    public static boolean muteState = false, playlistIsActive = false;
    public static File[] directoryListing;
    public static FloatControl volume, reverbration;
    public static Clip clip;

    public static File path = new File("");
    public static JFileChooser fileC = new JFileChooser();



    public MainFrame() {
        setTitle(" Vector Music Player");
        setLocation(250, 150);
        setSize(800,450);
        setMinimumSize(new Dimension(820,450));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        logo = new ImageIcon("../assets/logo.png");
        setIconImage(logo.getImage());

        main_page = getContentPane();
        main_page.setBackground(Color.darkGray);

        // Checking clip
        try {
            clip = AudioSystem.getClip();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "You may redownload the Java JDK or other related components!\n\n  If not, some/all features might not work properly!", "Error: JFrame prevented from initiating", JOptionPane.INFORMATION_MESSAGE);
        }

        //  BUILDING UI


        //Adding Controls Panel
        controls_panel = new ControlsPanel(this);
        main_page.add(controls_panel, BorderLayout.SOUTH);
        controls_panel.setAlignmentY(BOTTOM_ALIGNMENT);
        controls_panel.setBackground(Color.BLACK);
        controls_panel.setLayout(new GridLayout(2,1));
        
        // Adding a Playlist Panel
        playlist_panel = new JPanel();
        main_page.add(playlist_panel, BorderLayout.EAST);
        playlist_panel.setSize(250,320);
        playlist_panel.setLayout(new BorderLayout());
        playlist_panel.setBackground(Color.white);
        
        // Adding a menu bar (top)

        top_menu_bar = new TopMenuBar(this); 
        setJMenuBar(top_menu_bar);
        
        // Adding Background Icon and Image
        backgroundIcon = new ImageIcon("../assets/backgroundIcon.png");
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundIcon);
        main_page.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setHorizontalAlignment(CENTER);

        // Adding Music Label
        musicLabel = new JLabel("");
        main_page.add(musicLabel, BorderLayout.WEST);
        musicLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        musicLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        musicLabel.setForeground(Color.WHITE);

        // Adding a Titleplaylist
        TitlePlaylist = new JLabel("");
        TitlePlaylist.setFont(new Font("Verdana", Font.BOLD, 18));
        playlist_panel.add(TitlePlaylist, BorderLayout.NORTH);
        playlist_label = new JLabel("");
        TitlePlaylist.setFont(new Font("Helvetica", Font.PLAIN, 18));

        setVisible(true);

    }
    
}
