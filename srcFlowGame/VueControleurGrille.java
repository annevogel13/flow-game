import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VueControleurGrille extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 60;
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)
    private JComponent currentComponent;

    public VueControleurGrille(Jeux jeu) {
        int size = jeu.size;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();

        JPanel contentPane = new JPanel(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int ci = i;
                final int cj = j;
                tabCV[i][j] = new VueCase(jeu.tab_jeu[i][j]);
                contentPane.add(tabCV[i][j]);

                hashmap.put(tabCV[i][j], new Point(j, i));


                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue

                        // rnd en commentaire autrement je peut pas tester le chemin
                        //jeu.rnd(ci, cj);    // remplace --> ((VueCase) e.getSource()).rndType();

                        System.out.println("mousePressed : " + e.getSource());
                        System.out.println("mousePressed on case : " + ci +" , "+cj);
                        jeu.construireChemin(ci,cj);

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();
                        System.out.println("mouseEntered : " + e.getSource());
                        // doesn't work, always returns 0 0

                        System.out.println("mouseEntered in case : " + ci +" , "+cj);
                        //TODO mousepressed + entered
                        //jeu.construireChemin(ci,cj);

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + currentComponent);

                        jeu.construireChemin(ci,cj);
                        // afficher le chemin
                        jeu.afficherCheminCourante(10);

                        // TODO chemin check
                        // detruire le chemin (par une nouveau init)
                        jeu.initCaseModelChemin(10);
                        System.out.println("cheminCourante detruit");

                    }

                });

            }
        }

        setContentPane(contentPane);

    }




    @Override
    public void update(Observable o, Object arg) {


        for (int i = 0; i < tabCV.length; i++) {
            for (int j = 0; j < tabCV.length; j++) {

                tabCV[i][j].repaint();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        Jeux jeu = new Jeux(6,1);
        VueControleurGrille vue = new VueControleurGrille(jeu);
        jeu.addObserver(vue);
        vue.setVisible(true);
    }
}
