package co.yunchao.client.views;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.net.URL;

public class home {
    Image cardImg = null;
    Image logo = null;
    home() {
        try {
            URL url = new URL("https://cdn.discordapp.com/attachments/915957941365010473/915963203337461770/Card_Deck-42.png");
            this.cardImg = ImageIO.read(url);
            this.logo = cardImg.getScaledInstance(254,360, Image.SCALE_FAST);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JFrame homeFr = new JFrame();
        JLabel homeLogo = new JLabel(new ImageIcon(logo));
        JButton playBtn = new JButton("Play");

        playBtn.setBackground( Color.WHITE );
        playBtn.setPreferredSize(new Dimension(60, 40));

        homeFr.getContentPane().setBackground( Color.WHITE );
        homeFr.add(homeLogo);
        homeFr.add(playBtn, BorderLayout.SOUTH);
        homeFr.setPreferredSize(new Dimension(100,800));
        homeFr.setVisible(true);
        homeFr.pack();
    }

    public static void main(String[] args) {
        new home();
    }
}
