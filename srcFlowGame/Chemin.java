
//ATTENTION quand on fait (1,2) ça fait en réalité (2,1) REPERE CHANGE

public class Chemin {
    CaseModele[] chemin_courant;
    int taille_chemin_courant;
    int TAILLE_MAX = 10;

    public Chemin() {
        chemin_courant = new CaseModele[TAILLE_MAX];
        for(int i = 0; i < TAILLE_MAX; i++){

            chemin_courant[i] = new CaseModele(0,0);
        }
    }
/*
    public boolean verif_case_voisine(CaseModele c){
        CaseModele[] tab_case_voisine = new CaseModele[4];
        for(int i = 0; i < 4; i++){
            tab_case_voisine[0]= calcule_coord_p_ou_m_un(1, 1, c);
        }

        for(int i = 0; i < taille_chemin_courant; i++){

        }
    }

    public CaseModele calcule_coord_p_ou_m_un(int _x, int _y, CaseModele c){
        CaseModele c2 = new CaseModele(c.x + _x,  c.y + _y);
        return c2;
    }

    public boolean dedans_grille(CaseModele c){
        if((c.x < 0)||(c.y < 0)||(c.x < taille_chemin_courant)||(c.y < taille_chemin_courant)){
            return false;
        }
        else{
            return true;
        }
    }
    */
 

    /**
     * methode qui aide avec le debuggage
     */
    public void afficherChemin(){
        System.out.print("{");
        for (int i = 0; i < TAILLE_MAX; i++) {
            System.out.print("{"+ chemin_courant[i].x+"," + chemin_courant[i].y+"}");
        }
        System.out.print("}");

    }

}
