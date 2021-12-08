import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VueControleurGrille extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 100;
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)
    private JComponent currentComponent;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Choix grille");

    private JMenuItem g51;    private JMenuItem g52;    private JMenuItem g53;
    private JMenuItem g61;    private JMenuItem g62;    private JMenuItem g63;
    private JMenuItem g71;    private JMenuItem g72;    private JMenuItem g81;
    private JMenuItem g91;


    public VueControleurGrille(Jeux jeu) {
        int size = jeu.size;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();

        JPanel contentPane = new JPanel(new GridLayout(size, size));

        menuBar.add(menu);


        g51 = new JMenuItem(new AbstractAction(" Taille : 5  Niveau 1") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Jeux _jeu = new Jeux(5,1);
                    VueControleurGrille vue = new VueControleurGrille(_jeu);
                    dispose();
                    _jeu.addObserver(vue);
                    vue.setVisible(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        g52 = new JMenuItem(new AbstractAction(" Taille : 5  Niveau 2") {
            @Override

            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(5,2);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        g53 = new JMenuItem(new AbstractAction(" Taille : 5  Niveau 3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(5,3);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        g61 = new JMenuItem(new AbstractAction(" Taille : 6  Niveau 1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(6,1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g62 = new JMenuItem(new AbstractAction(" Taille : 6  Niveau 2") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(6,2);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g63 = new JMenuItem(new AbstractAction(" Taille : 6  Niveau 3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(6,3);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g71 = new JMenuItem(new AbstractAction(" Taille : 7  Niveau 1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(7,1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g72 = new JMenuItem(new AbstractAction(" Taille : 7  Niveau 2") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(7,2);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g81 = new JMenuItem(new AbstractAction(" Taille : 8  Niveau 1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(8,1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        g91 = new JMenuItem(new AbstractAction(" Taille : 9  Niveau 1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nouvelle_partie(9,1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        menu.add(g51);  menu.add(g52); menu.add(g53); menu.add(g61); menu.add(g62);
        menu.add(g63);  menu.add(g71); menu.add(g72); menu.add(g81); menu.add(g91);

        setJMenuBar(menuBar);

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
                        // Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue
                        // jeu.rnd(ci, cj);    // remplace --> ((VueCase) e.getSource()).rndType();
                        System.out.println("mousePressed : " + e.getSource());
                        try {
                            jeu.sourisCliquer(ci,cj);

                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                    }


                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentComponent = (JComponent) e.getSource();
                        System.out.println("mouseEntered : " + e.getSource());
                        jeu.chemin.cheminReste(jeu.tab_jeu[ci][cj]);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        //System.out.println("mouseReleased : " + currentComponent);
                        System.out.println("chemin de : "+ e.getSource()+" a "+currentComponent);

                        // code d'avant jeu.sourisRelacher(ci,cj);

                        try {
                            jeu.sourisRelacher();

                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }


                        for(int i = 0 ; i < tabCV.length; i++){
                            for(int j = 0 ; j < tabCV.length; j++){
                                tabCV[i][j].c.type = jeu.tab_jeu[i][j].type;
                                tabCV[i][j].c.type_chemin = jeu.tab_jeu[i][j].type_chemin;
                                tabCV[i][j].c.x = jeu.tab_jeu[i][j].x;
                                tabCV[i][j].c.y = jeu.tab_jeu[i][j].y;

                            }

                        }

                    }

                });
            }
        }

        setContentPane(contentPane);

    }

    @Override
    public void update(Observable o, Object arg) {
    System.out.println(" \n \n");
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

    public void nouvelle_partie(int taille, int niveau) throws IOException {
        Jeux _jeu = new Jeux(taille,niveau);
        VueControleurGrille vue = new VueControleurGrille(_jeu);
        dispose();
        _jeu.addObserver(vue);
        vue.setVisible(true);
    }
}
