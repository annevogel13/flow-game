import java.util.Observable;

public class Jeux extends Observable {
    public int size;
    public CaseModele tab_jeu[][];
   // public int [] cheminCourante[]; // pour stocker le chemin courante // misschien


    public Jeux (int size){
        this.size = size;
        this.tab_jeu = new CaseModele[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tab_jeu[i][j] = new CaseModele();
            }

        }

    }

    public void rnd(int i, int j) {
        tab_jeu[i][j].rndType();
        setChanged();
        notifyObservers();
    }

    public void construireChemin(int x, int y){
         CaseType cm = this.tab_jeu[x][y].type;

        // verifier si une case est une case vide? --> type empty
        // verifier si on croise pas? --> type h...
        // verifier si une case est le fin ? --> exacte le meme type que le debut --> type S_
         switch(cm){
             // etats debut/finaux
             case S1:
             case S2:
             case S3:
             case S4:
             case S5:
                    initCaseModelChemin();
                 break;

             case empty:
                 ajouteCaseModelChemin();
                 System.out.println("case ajoute aux chemin");
                 break;
             // le reste des options h0h1, v0v1, cross, h0v0, h0v1, h1v0, h1v1
             default:
                 System.out.println("case déjà rempli --> croisement");
                 break;



         }



    }

    // reste a faire
    public void ajouteCaseModelChemin(){}

    public void initCaseModelChemin(){}


}
