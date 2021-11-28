
//ATTENTION quand on fait (1,2) ça fait en réalité (2,1) REPERE CHANGE

public class Chemin {
    CaseModele[] chemin_courant;
    int taille_chemin_courant;
    int TAILLE_MAX = 50;

    public Chemin() {
        chemin_courant = new CaseModele[TAILLE_MAX];
        taille_chemin_courant = 0;

        for(int i = 0; i < TAILLE_MAX; i++){

            chemin_courant[i] = new CaseModele(0,0);
            chemin_courant[i].type = CaseType.empty;

        }
    }

    public boolean verif_chemin(){
        for(int i = 0; i < taille_chemin_courant-1; i++){
            if(!(verif_case_voisine(chemin_courant[i], chemin_courant[i+1]))){
                return false;
            }
        }
        return true;
    }

    public boolean verif_case_voisine(CaseModele c, CaseModele c_suivante){
        CaseModele[] tab_case_voisine = new CaseModele[4];
        tab_case_voisine[0]= new CaseModele(c.x - 1,  c.y + 0); //haut
        tab_case_voisine[1]= new CaseModele(c.x + 0,  c.y + 1); //droit
        tab_case_voisine[2]= new CaseModele(c.x + 1,  c.y + 0); //bas
        tab_case_voisine[3]= new CaseModele(c.x + 0,  c.y - 1); //gauche

        // droite = 1, gauche = -1, haut = 2, bas = -2
        if((compare_deux_cases(tab_case_voisine[0], c_suivante))){

            c.direction_case_suivante = 2;
            return true;
        }
        if(compare_deux_cases(tab_case_voisine[1], c_suivante)){
            c.direction_case_suivante = 1;
            return true;
        }
        if(compare_deux_cases(tab_case_voisine[2], c_suivante)){
            c.direction_case_suivante = -2;
            return true;
        }
        if(compare_deux_cases(tab_case_voisine[3], c_suivante)){
            c.direction_case_suivante = -1;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean prem_der_egales(){
        System.out.print("\n " + chemin_courant[0].type +  " " + chemin_courant[taille_chemin_courant-1].x + "  " + chemin_courant[taille_chemin_courant-1].y + " " +chemin_courant[taille_chemin_courant-1].type );
        if(chemin_courant[0].type == chemin_courant[taille_chemin_courant-1].type){
            return true;
        }else{
            return false;
        }
    }

    public boolean compare_deux_cases(CaseModele c1, CaseModele c2){
        if(c1.x == c2.x){
            if(c1.y == c2.y){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * methode qui aide avec le debuggage
     */
    public void afficherChemin(){
        System.out.print("{");
        for (int i = 0; i < 10; i++) {
            System.out.print("{"+ chemin_courant[i].x+"," + chemin_courant[i].y+"}");
        }
        System.out.print("} \n");

    }

    /**
     * methode qui aide avec le debuggage
     */
    public void afficherCheminTypes(){
        System.out.println((taille_chemin_courant));
        for (int i = 0; i < taille_chemin_courant; i++) {
            System.out.println("type de case "+i+ " est " + chemin_courant[i].type);
        }

    }

    /**
     * ajout une caseModele au chemin
     * @param cm : le case a ajouter
     */
    public void ajouteCase(CaseModele cm) {

        chemin_courant[taille_chemin_courant].x = cm.x;
        chemin_courant[taille_chemin_courant].y = cm.y;
        chemin_courant[taille_chemin_courant].type = cm.type;
        taille_chemin_courant = taille_chemin_courant + 1;

    }

    /**
     * methode qui commence le chemin, par mettre le casemodele de type S_ dans le chemin_courante
     * @param cm : le caseModele à ajouter dans le chemin_courante (place 0)
     */
    public void cheminStart(CaseModele cm){
        // si le type n'est pas vide → du coup, ça serait un type S_
        // et on peut ajoute la case en chemin_courante
        if(cm.type != CaseType.empty){

            ajouteCase(cm);
        }
    }

    /**
     * methode qui remplit le chemin_courante avec des caseModeles passé en parametre
     * et qui ajoute le derniere element si le cm type est de type S_
     * @param cm : le caseModele à ajouter dans le chemin_courante (place taille_chemin_courant+1)
     */
    public void cheminReste(CaseModele cm){
        // verifier si le chemin a commencé sur un type S_ (le premiere case de tableaux remplit
        if(chemin_courant[0].type != CaseType.empty){

            // verifier si le case vide et peut du coup faire partie d'un chemin
            if(cm.type == CaseType.empty){

                ajouteCase(cm);

            }else {
                // verifier si cm est le fin d'un chemin
                if (cm.type == chemin_courant[0].type) {
                    System.out.println("(Debut, Fin) de " + cm.type);
                    ajouteCase(cm);
                }
            }
        }
    }

    public void troisCaseDeduireType(CaseModele cm1,CaseModele cm2, CaseModele cm3, CaseModele [][] tab_jeu){

        /*          v0
                     _
                 h0 | | h1
                     -
                     v1        */

        // les lignes droites
        if(cm1.x == cm3.x){
            tab_jeu[cm2.x][cm2.y].type = CaseType.h0h1;
        }else if(cm1.y == cm3.y) {
            tab_jeu[cm2.x][cm2.y].type = CaseType.v0v1;
        }
        /*
        // à partir de gauche (H0)
        if(cm1.x + 1 == cm3.x){
            if(cm1.y + 1 == cm3.y){
                tab_jeu[cm2.x][cm2.y].type = CaseType.h0v1;
            }else if(cm1.y -1 == cm3.y){
                tab_jeu[cm2.x][cm2.y].type = CaseType.h0v0;
            }
        }
        // à partir de droite (H1)
        if((cm1.x - 1 == cm3.x)){
            if((cm1.y + 1 == cm3.y)){
                tab_jeu[cm2.x][cm2.y].type = CaseType.h1v1;
            }else if((cm1.y - 1 == cm3.y)){
                tab_jeu[cm2.x][cm2.y].type = CaseType.h1v0;
            }
        } */
    }
}
