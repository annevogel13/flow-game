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
        c = new CaseModele(cm.x, cm.y);
        c.type_chemin = cm.type_chemin;
        c.y = cm.y;
        c.x = cm.x;
        c.type = cm.type;
        c.direction_case_suivante = cm.direction_case_suivante;
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
        //épaisseur du quadrillage
        Graphics2D g2 = (Graphics2D) g;
        float epaisseur = 3;
        g2.setStroke(new BasicStroke(epaisseur));

        //Dessin du quadrillage
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, getWidth(), getHeight());


        Rectangle2D deltaText =  g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext()); // "0" utilisé pour gabarit

        //Centrage des images dans les cases (responsive)
        double v = getWidth() * 0.78;
        int dim = (int) v;

        v = getWidth() * 0.11;
        int centrer = (int) v;

        //épaisseur des lignes (responsive)
        v = getWidth() * 0.32;
        epaisseur = (int) v;
        g2.setStroke(new BasicStroke(epaisseur));


        if(c.type_chemin != CaseType.empty){
            System.out.print(" FFFFF " + c.type_chemin);
            set_couleur_lignes(g);
        }


        switch(c.type) {
            case S1 :
                 g.drawImage(img0,centrer, centrer, dim, dim, this);
                //g.drawString("1", getWidth()/2 - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S2 :
                g.drawImage(img1, centrer,centrer, dim, dim, this);
                //g.drawString("2", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S3 :
                g.drawImage(img2,centrer, centrer, dim, dim, this);
                //g.drawString("3", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S4 :
                g.drawImage(img3,centrer, centrer, dim, dim, this);
                //g.drawString("4", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S5 :
                g.drawImage(img4,centrer, centrer, dim,dim, this);
                //g.drawString("5", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S6 :
                g.drawImage(img5,centrer, centrer, dim,dim, this);
                //g.drawString("5", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S7 :
                g.drawImage(img6,centrer, centrer, dim,dim, this);
                //g.drawString("5", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S8 :
                g.drawImage(img7,centrer, centrer, dim,dim, this);
                //g.drawString("5", getWidth()/2  - (int) deltaText.getCenterX(), getHeight()/2 - (int) deltaText.getCenterY());
                break;
            case S9 :
                g.drawImage(img8, centrer, centrer, dim,dim, this);
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
                drawNoon(g);
                drawThree(g);
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


    public void set_couleur_lignes(Graphics g){

        System.out.print(" CHANGEMENT " + c.x + "," + c.y);
        switch (c.type_chemin){
            case S1 :
                g.setColor(Color.decode("#a6d864")); //vert
                break;
            case S2 :
                g.setColor(Color.decode("#659ad2")); //bleu
                break;
            case S3 :
                g.setColor(Color.decode("#ffde00")); //jaune
                break;
            case S4 :
                g.setColor(Color.decode("#984f97"));
                break;
            case S5 :
                g.setColor(Color.decode("#d5be99"));
                break;
            case S6 :
                g.setColor(Color.black);
                break;
            case S7 :
                g.setColor(Color.orange);
                break;
            case S8 :
                g.setColor(Color.pink);
                break;
            case S9:
                g.setColor(Color.red);
                break;
        }
    }



    public void chargement_images(){

        try {
        /*
            img0 = ImageIO.read(new File("../data/images/vert.png"));
            img1 = ImageIO.read(new File("../data/images/bleu.png"));
            img2 = ImageIO.read(new File("../data/images/jaune.png"));
            img3 = ImageIO.read(new File("../data/images/violet.jpg"));
            img4 = ImageIO.read(new File("../data/images/creme.png"));
            img5 = ImageIO.read(new File("../data/images/noir.png"));
            img6 = ImageIO.read(new File("../data/images/orange.jpg"));
            img7 = ImageIO.read(new File("../data/images/rose.png"));
            img8 = ImageIO.read(new File("../data/images/rouge.jpg"));
        */

            img0 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\vert.png"));
            img1 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\bleu.png"));
            img2 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\jaune.png"));
            img3 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\violet.jpg"));
            img4 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\creme.png"));
            img5 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\noir.png"));
            img6 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\orange.jpg"));
            img7 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\rose.png"));
            img8 = ImageIO.read(new File("C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\images\\rouge.jpg"));


        } catch (IOException e) {
            System.out.println("\nL'image ne pouvait pas charger. \n");
        }

    }

}
