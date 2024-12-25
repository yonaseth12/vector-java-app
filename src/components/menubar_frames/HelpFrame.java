package components.menubar_frames;

import javax.swing.*;

import components.MainFrame;

import java.awt.*;

public class HelpFrame extends JFrame{
    public HelpFrame(MainFrame mainFrame) {
        setTitle("Help Center");
        setLocation(400,130);
        setSize(550,480);
        setIconImage(mainFrame.backgroundIcon.getImage());
        JPanel helpPanel = new JPanel();
        JLabel helpLabel = new JLabel();
        helpPanel.add(helpLabel);
        add(BorderLayout.CENTER, new JScrollPane(helpPanel));
        helpPanel.setLayout(new GridLayout(1,1));
        helpLabel.setText("<html>"+
                    "<h1><u>HELP</u></h1>"+
                    "<h2><u>Functionalities</u></h2>"+
                    "<ul>"+
                        "<li>Play/Pause(▶️|⏸️): -lets you pause and play current media file.</li>"+
                        "<li>Previous Media(⏮️): -plays the previous media file in current playlist.</li>"+
                        "<li>Next Media(⏭️): -plays the next media file in current playlist.</li>"+
                        "<li>Loop <ul>"+
                            "<li>Loop off(🔄): -once current playing media file is fully played, player stops.</li>"+
                            "<li>Loop once(🔂): -current playing media file will be played once again.</li>"+
                            "<li>Loop infinitely(🔁): -current media file will be played indefinitely.</li>"+
                        "</ul></li>"+
                        "<li>Reset(↩️): -resets the current seconds in timeline to 00:00:00.</li>"+
                        "<li>Open file(📁): -creates and displays a file chooser window for the user.</li>"+
                        "<li>Playlist(♬): -import a playlist/folder containing media files.</li>"+
                        "<li>Controls(🎛️): -contains different mixer functionalities.</li>"+
                        "<li>Mute/Unmute(🔈/🔇): -mute and unmute the output channel alternatively.</li>"+
                        "<li>Volume Down(🔉): - decreases the volume.</li>"+
                        "<li>Volume Up(🔊): -increases the volume.</li>"+
                        "<li>Volume Slider: -control the volume using a slider.</li>"+
                        "<li>Timeline Slider: -shows current time relative to total time and provides adjustment capabilities.</li>"+
                        "<li><i>All the menubar items have same functionalities as those on the bottom toolbar.<br>"+
                        "Exceptions are 'About' & 'Help'.</i></li>"+
                    "</ul>"+
                    "<br>"+
                    "<h2><u>ShortCuts</u></h2>"+
                    "<ul>"+
                        "<li>Alt + ENTER : Play/Pause</li>"+
                        "<li>Alt + LEFT : Play Previous Media file</li>"+
                        "<li>Alt + RIGHT : Play Next Media file</li>"+
                        "<li>Alt + UP : Increase audio volume</li>"+
                        "<li>Alt + DOWN : Decrease audio volume</li>"+
                        "<li>Alt + L : Alternates between different looping choices</li>"+
                        "<li>Alt + M : Alternates between mute and unmute</li>"+
                        "<li>Alt + C : Opens window containing mixer utilizing controls</li>"+
                        "<li>Alt + R : Reset current time</li>"+
                        "<li>Alt + O : Opens file chooser to open file</li>"+
                        "<li>Alt + P : Opens filechooser to import playlist</li>"+
                    "</ul>"+
                "</html>");
    }
}
