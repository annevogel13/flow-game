
//ATTENTION quand on fait (1,2) ça fait en réalité (2,1) REPERE CHANGE

public class Chemin {
    CaseModele[] chemin_courant;
    int taille_chemin_courant;
    int TAILLE_MAX = 10;

    public Chemin() {
        chemin_courant = new CaseModele[TAILLE_MAX];
        taille_chemin_courant = 0;
        for(int i = 0; i < TAILLE_MAX; i++){

            chemin_courant[i] = new CaseModele(0,0);
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
        if((compare_deux_cases(tab_case_voisine[0], c_suivante)){
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
        for (int i = 0; i < TAILLE_MAX; i++) {
            System.out.print("{"+ chemin_courant[i].x+"," + chemin_courant[i].y+"}");
        }
        System.out.print("} \n");

    }

}
