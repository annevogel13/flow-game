import java.util.Observable;

public class Jeux extends Observable {
    public int size;
    public CaseModele tab_jeu[][];
    public int [] cheminCourante[]; // pour stocker le chemin courante


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
        if(cm == CaseType.empty){
                ajouteCaseModelChemin();
        }
        // verifier si on croise pas? --> type h...

        // verifier si une case est le fin ? --> exacte le meme type que le debut --> type S_
    }

    public void ajouteCaseModelChemin(){

    }


}
