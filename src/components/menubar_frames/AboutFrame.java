package components.menubar_frames;

import javax.swing.*;

import components.MainFrame;

import java.awt.*;

public class AboutFrame extends JFrame{
    public AboutFrame(MainFrame mainFrame) {
        setTitle("About Vector MP");
        setLocation(400,150);
        setSize(450,420);
        setIconImage(mainFrame.backgroundIcon.getImage());
        setMinimumSize(new Dimension(450,400));
        Container cabout = getContentPane();
        JLabel topLabel = new JLabel();
        topLabel.setIcon(mainFrame.backgroundIcon);
        cabout.add(topLabel, BorderLayout.NORTH);
        topLabel.setHorizontalAlignment(mainFrame.CENTER);
        JLabel aboutLabel = new JLabel();
        cabout.add(aboutLabel);
        String aboutContent = "<html>"+
                    "<style type=\"text/css\">"+
                        "h5, h4, h3{"+
                            "font-family:Arial, Helvetica, sans-serif;"+
                            "font-weight: lighter;"+
                        "}"+
                        "h1{ margin-bottom: 0; font-size: 40;}"+
                        "body{ margin-left: 16%; margin-right: 16%;}"+
                    "</style>"+
                    "<p>"+
                        "<h1>Vector Music Player</h1>"+
                        "<h3> &nbsp &nbsp &nbsp &nbsp &nbsp Version 1.0</h3>"+
                    "</p>"+
                    "<hr>"+
                    "<h4>Vector music player is an open source simple audio player which is written in Java using java swing. It is developed as part of a course project by Yonas W., Addis Ababa Institute of Technology.</h4>"+
                    "<h5>The source code can be used for any purposes under terms of Fair Use."+
                        "<br> Feel free to reach me at <i><a href=\"mailto:yonaseth12@gmail.com\">yonaseth12@gmail.com</a></i>.</h5>"+
                "</html>";
        aboutLabel.setText(aboutContent);
    }
}
