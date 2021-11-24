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
             // etats debut/finaux
             case S1:
             case S2:
             case S3:
             case S4:
             case S5:
                   // debut d'une chemin
                 // TODO verifier si c'est le debut ou le fin d'une chemin
                    if(cheminCourante[0][0] == -1 && cheminCourante[0][1] == -1){ // donc debut de chemin (premiere pas)
                        cheminCourante[0][0] = x;
                        cheminCourante[0][1] = y;
                        System.out.println("start chemin");
                    }else{
                        ajouteCaseModelChemin(x,y, true);
                    }
             break;

             case empty:
                 ajouteCaseModelChemin(x,y, false);
                 break;

             // le reste des options h0h1, v0v1, cross, h0v0, h0v1, h1v0, h1v1
             case cross:
             case v0v1:
             case h1v1:
             case h1v0:
             case h0v1:
             case h0v0:
             case h0h1:
                 System.out.println("case déjà rempli");
                 break;
         }
    }

    /**
     * methode qui verifie si on le droit de passer a le case suivante
     * en regardent le derniere case du cheminCourante
     * @return vrai si on peut, faux sinon
     */
    public boolean ajouteCaseModelChemin(int x, int y, boolean fin){

        // cherche le derniere case rempli
        int derniereCaseRempli = 0 ;

        for (int i = 0; i < 10; i++) {
           if(cheminCourante[i][0] > -1 ){
               derniereCaseRempli = i;
           }
        }

        // TODO verifier si on a le droit de aller aux pas prochaine

        // remplir le prochaine case du cheminCourante
        cheminCourante[derniereCaseRempli+1][0] = x;
        cheminCourante[derniereCaseRempli+1][1] = y;

        return true;

    }

    /**
     * methode qui initialise le cheminCourante
     * (peut aussi être utilise pour
     * @param longeurChemin
     */
    public void initCaseModelChemin(int longeurChemin){
        // chemin (max longeur 10) avec le 2 pour stocker le case "entered"
        this.cheminCourante = new int[longeurChemin][2];
        // initalise avec -1
        for (int i = 0; i < longeurChemin; i++) {
            cheminCourante[i][0] = -1;
            cheminCourante[i][1] = -1;
        }
    }

    /**
     * methode qui aide avec le debuggage
     * @param longeurChemin : le longeur du cheminCourante
     */
    public void afficherCheminCourante(int longeurChemin){
        System.out.print("{");
        for (int i = 0; i < longeurChemin; i++) {
            System.out.print("{"+cheminCourante[i][0]+","+cheminCourante[i][1]+"}");
        }
        System.out.print("}");

    }


}
