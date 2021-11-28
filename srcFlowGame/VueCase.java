import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

// TODO : redéfinir la fonction hashValue() et equals(Object) si vous souhaitez utiliser la hashmap de VueControleurGrille avec VueCase en clef

public class VueCase extends JPanel {


    public CaseModele c;

    public VueCase(CaseModele cm) {

        c = cm;
        chargement_images();
    }

    private void drawNoon(Graphics g) {
        g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, 0);
    }

    private void drawNine(Graphics g) {
        g.drawLine(0, getHeight()/2, getWidth()/2, getHeight()/2);
    }

    private void drawSix(Graphics g) {
        g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, getHeight());
    }

    private void drawThree(Graphics g) {
        g.drawLine(getWidth()/2, getHeight()/2, getWidth(), getHeight()/2);
    }

    public Image img0 = null;    public Image img1 = null;    public Image img2 = null;    public Image img3 = null;
    public Image img4 = null;    public Image img5 = null;    public Image img6 = null;    public Image img7 = null;    public Image img8 = null;




    @Override
    public void paintComponent(Graphics g) {

        g.clearRect(0, 0, getWidth(), getHeight());

        g.drawRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, 5, 5);

        Rectangle2D deltaText =  g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext()); // "0" utilisé pour gabarit



                //TODO rajoute S6 - S9
        switch(c.type) {
            case S1 :
                g.drawImage(img0,0, 0, getWidth(), getHeight(), this);
                //g.drawString("1", getWidth()/2 - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S2 :
                g.drawImage(img1,0, 0, getWidth(), getHeight(), this);
                //g.drawString("2", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S3 :
                g.drawImage(img2,0, 0, getWidth(), getHeight(), this);
                //g.drawString("3", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S4 :
                g.drawImage(img3,0, 0, getWidth(), getHeight(), this);
                //g.drawString("4", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S5 :
                g.drawImage(img4,0, 0, getWidth(), getHeight(), this);
                //g.drawString("5", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case h0v0 :
                drawNine(g);
                drawNoon(g);
                break;
            case h0v1 :
                drawNine(g);
                drawSix(g);
                break;
            case h1v0:
                // TODO

                break;
            case h1v1 :
                drawThree(g);
                drawSix(g);
                break;
            case h0h1:
                drawThree(g);
                drawNine(g);
                break;
            case v0v1:
                drawNoon(g);
                drawSix(g);
                break;
            case cross:
                drawNoon(g);
                drawSix(g);
                drawThree(g);
                drawNine(g);
                break;

        }



    }
    public String toString() {
        return c.x + ", " + c.y;

    }

    public void chargement_images(){
        System.out.println("dedans");

        try {
            img0 = ImageIO.read(new File("../data/images/vert.png"));
            img1 = ImageIO.read(new File("../data/images/bleu.png"));
            img2 = ImageIO.read(new File("../data/images/jaune.png"));
            img3 = ImageIO.read(new File("../data/images/violet.jpg"));
            img4 = ImageIO.read(new File("../data/images/creme.png"));
            img5 = ImageIO.read(new File("../data/images/noir.png"));
            img6 = ImageIO.read(new File("../data/images/orange.jpg"));
            img7 = ImageIO.read(new File("../data/images/rose.png"));
            img8 = ImageIO.read(new File("../data/images/rouge.jpg"));

        } catch (IOException e) {
            System.out.println("\nL'image ne pouvait pas charger. \n");
        }





    }


}
