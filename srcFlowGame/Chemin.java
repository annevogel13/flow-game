
//ATTENTION quand on fait (1,2) ça fait en réalité (2,1) REPERE CHANGE

public class Chemin {
    CaseModele[] chemin_courant;
    int taille_chemin_courant;
    int TAILLE_MAX = 10;

    public Chemin() {
        chemin_courant = new CaseModele[TAILLE_MAX];
        for(int i = 0; i < TAILLE_MAX; i++){

            chemin_courant[i] = new CaseModele();
        }
    }

    public boolean verif_case_voisine(){
        CaseModele[] tab_case_voisine = new CaseModele[4];
        tab_case_voisine[0].
        for(int i = 0; i < taille_chemin_courant; i++){

        }
    }

    public void calcule_coord_p_ou_m_un(int _x, int _y, CaseModele c){
        CaseModele c2 = new CaseModele();
        c.x = c.x + _x;
        c.y = c.y + _y;
    }

    public boolean dedans_grille(CaseModele c){
        if((c.x < 0)||(c.y < 0)||(c.x < taille_chemin_courant)||(c.y < taille_chemin_courant)){
            return false;
        }
        else{
            return true;
        }
    }

}
