import java.util.Observable;

public class Jeux extends Observable {
    public int size;
    public CaseModele [][] tab_jeu;
    public int [][] cheminCourante; // pour stocker le chemin courante // misschien


    public Jeux (int size){
        this.size = size;
        this.tab_jeu = new CaseModele[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tab_jeu[i][j] = new CaseModele();
            }
        }

        initCaseModelChemin(10);

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
             // etats debut
             case S1:
             case S2:
             case S3:
             case S4:
             case S5:
                    // debut d'une chemin
                    if(cheminCourante[0][0] == -1 && cheminCourante[0][1] == -1){ // donc debut de chemin (premiere pas)
                        cheminCourante[0][0] = x;
                        cheminCourante[0][1] = y;
                    }
             break;

             case empty:
                 if(ajouteCaseModelChemin()){
                     System.out.println("case ajoute aux chemin");
                 }else System.out.println("on n'a pas le droit"); // doit arrêter le desin de chemin

                 break;

             // le reste des options h0h1, v0v1, cross, h0v0, h0v1, h1v0, h1v1
             default:
                 System.out.println("case déjà rempli --> croisement");
                 break;

         }

    }

    /**
     * methode qui verifie si on le droit de passer a le case suivante
     * en regardent le derniere case du cheminCourante
     * @return vrai si on peut, faux sinon
     */
    public boolean ajouteCaseModelChemin(){

        return true;
    }

    public void initCaseModelChemin(int longeurChemin){
        // chemin (max longeur 10) avec le 2 pour stocker le case
        this.cheminCourante = new int[longeurChemin][2];
        // initalise avec -1
        for (int i = 0; i < longeurChemin; i++) {
            cheminCourante[i][0] = -1;
            cheminCourante[i][1] = -1;
        }
    }


}
