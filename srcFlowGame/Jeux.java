import java.io.*;
import java.util.Observable;
import java.util.Scanner;

public class Jeux extends Observable {
    // Jeu
    public int size;
    public CaseModele [][] tab_jeu;
    public int niveau;
    public int nbr_formes;

    // Chemin
    public int MAX_CHEMIN = 9;
    public int nombre_chemin;
    public Chemin chemin;

    public Chemin [] tab_chemin;
    public String [][]tab_chemins;
    public String [][]tab_joueur;

    public Jeux (int size, int _niveau) throws IOException {
        this.niveau = _niveau;
        this.size = size;
        this.nombre_chemin = 0;

        tab_chemin = new Chemin[MAX_CHEMIN];
        for(int i = 0; i< MAX_CHEMIN; i++){
            tab_chemin[i] = new Chemin();
        }

        String str_niveau = new String();
        String str_dimension = new String();
        String str_dim_niv = new String();
        str_niveau = String.valueOf(niveau);
        str_dimension = String.valueOf(size);
        str_dim_niv = str_dimension + " " + str_niveau;
        lire_fichier_texte(str_dim_niv);

        creation_tab_joueur();

        this.tab_jeu = new CaseModele[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tab_jeu[i][j] = new CaseModele(i, j);
            }
        }

        init_tab_jeu();
        chemin = new Chemin();

    }

    public void rnd(int i, int j) {
        tab_jeu[i][j].rndType();
        setChanged();
        notifyObservers();
    }

    /**
     * méthode qui parcour le chemin_courante et en déduit le type des cases dans chemin_courante
     */
    public void affichageCheminGrille() {
        // de chemin_courant[1] jusqua l'avant dernier
        for (int i = 1; i < chemin.taille_chemin_courant - 1; i++) {

            chemin.troisCaseDeduireType(chemin.chemin_courant[i - 1], chemin.chemin_courant[i], chemin.chemin_courant[i + 1], tab_jeu);
            chemin.chemin_courant[i].type = tab_jeu[chemin.chemin_courant[i].x][chemin.chemin_courant[i].y].type;
           // System.out.println("change " + chemin.chemin_courant[i].x + "," + chemin.chemin_courant[i].y + " en " + chemin.chemin_courant[i].type);

            setChanged();
            notifyObservers();
        }
    }

    public void verif_chemin() throws CloneNotSupportedException {
        boolean bool1;
        boolean bool2;
        //1- on vérifie que la première et dernière case soient égales
        bool1 = chemin.prem_der_egales();
        //2 - on vérifie que les cases se suivent
        bool2 = chemin.verif_chemin();

        //System.out.print( " ===> "+ bool1 + " " +bool2+"\n");

        //3- on l'ajoute au tableau de chemins trouvés
        if(bool1 && bool2){

        Chemin chemin_clone = (Chemin) chemin.clone();
        tab_chemin[nombre_chemin].chemin_courant = chemin_clone.chemin_courant;
        tab_chemin[nombre_chemin].taille_chemin_courant = chemin_clone.taille_chemin_courant;
        affichageCheminGrille();

        nombre_chemin += 1;
        ajoute_chemin_a_tab_jeu(chemin_clone);
        //ajoute_chemin_a_tab_jeu(tab_chemin[nombre_chemin-1]);



         }



        chemin = new Chemin();
    }



    public void ajoute_chemin_a_tab_jeu(Chemin c){


        for(int i = 0; i < c.taille_chemin_courant; i++){
            int x = c.chemin_courant[i].x;
            int y = c.chemin_courant[i].y;
            tab_jeu[x][y].type = c.chemin_courant[i].type;
            tab_jeu[x][y].type_chemin = c.chemin_courant[i].type_chemin;

        }
        for(int v = 0 ; v < 6; v++){
            for(int j = 0 ; j < 6; j++){


                System.out.print("_" + tab_jeu[v][j].type_chemin + " ");
            }
            System.out.println("\n");
        }
    }

    public void lire_fichier_texte(String s) throws IOException {

        String chaine ="";
        String fichier = "../data/grilles.txt"; //"C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\grilles.txt";
        String ligne;

        // lit le fichier ligne par ligne
        try{
            // lire un fichier
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            // lire ligne par ligne
            String ligne_tete;
            while ((ligne_tete=br.readLine())!=null){
                //System.out.println(ligne);
                chaine+=ligne_tete+"\n";
                if(ligne_tete.matches(s)){
                    this.tab_chemins = new String[this.size][this.size];
                    for (int i = 0; i < this.size+1; i++){
                        chaine+=ligne=br.readLine();
                        chaine+=ligne+"\n";

                        if (i == 0){

                            Scanner sca = new Scanner(ligne);
                            this.nbr_formes = sca.nextInt();
                        }else{
                            //System.out.print("\n \n   *** \n Ligne " + i + "  : " + ligne);

                            Scanner scan = new Scanner(ligne);
                            scan.useDelimiter(" ");

                            for (int j = 0; j < this.size; j++){

                                this.tab_chemins[i-1][j] = scan.next();

                                //System.out.print("\n Colonne " + j + "  : " + v);
                            }
                        }
                    }

                    //vérifications
                    System.out.print( "\n  Jeu choisi : dimension = " + this.size + " niveau = " + this.niveau + " nbr de formes à relier = " + this.nbr_formes);
                    System.out.print("\n \n Version avec solutions : \n \n");
                    for (int i = 0 ; i < this.size ; i++){
                        for(int j = 0; j < this.size ; j++){
                            System.out.print(this.tab_chemins[i][j]);
                            System.out.print(" ");
                        }
                        System.out.print("\n");
                    }
                }
            }
            br.close(); // On ferme le flux
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public int[] string_to_ascii(String s){
        //Transformation en char
        int taille = s.length();

        char[] tab_char = new char[taille];
        for(int i = 0; i< taille; i++){
            tab_char[i]=s.charAt(i);
        }

        //Transformation en ASCII
        int[] tab_ascii = new int[taille];
        for(int i = 0; i < taille; i++){
            tab_ascii[i]= (int) tab_char[i];
        }

        return tab_ascii;
    }


    public void creation_tab_joueur(){
        //1 - Conversion du String en ASCII
        int[][] tab_ascii = new int[this.size][this.size];
        for(int i = 0; i < this.size; i++){
            for(int j=0 ; j < this.size; j++ ){
                tab_ascii[i][j]= string_to_ascii(this.tab_chemins[i][j])[0];
            }
        }


        //2 - On supprime les minuscules (qui sont les réponses) donc ASCII > 95
        this.tab_joueur = new String[this.size][this.size];
        for(int i = 0; i < this.size; i++){
            for(int j=0 ; j < this.size; j++ ){
                if(tab_ascii[i][j] < 95){
                    this.tab_joueur[i][j] = this.tab_chemins[i][j];
                }
                else{
                    this.tab_joueur[i][j] = "_";
                }
            }
        }

        //vérification
        System.out.print("\n \n Version joueur début de jeu : \n \n");
        for(int i = 0; i < this.size; i++){
            for(int j=0 ; j < this.size; j++ ){
                System.out.print(tab_joueur[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void init_tab_jeu(){
        for(int i = 0 ; i < this.size; i++){
            for(int j = 0 ; j < this.size; j++){
                if(tab_joueur[i][j]=="_"){
                    tab_jeu[i][j].type= CaseType.empty;
                }else{
                    String s = new String();
                    s = tab_joueur[i][j];
                    switch (s) {
                        case "R" -> tab_jeu[i][j].type = CaseType.S1;
                        case "V" -> tab_jeu[i][j].type = CaseType.S2;
                        case "B" -> tab_jeu[i][j].type = CaseType.S3;
                        case "J" -> tab_jeu[i][j].type = CaseType.S4;
                        case "O" -> tab_jeu[i][j].type = CaseType.S5;
                        case "T" -> tab_jeu[i][j].type = CaseType.S6;
                        case "F" -> tab_jeu[i][j].type = CaseType.S7;
                        case "Y" -> tab_jeu[i][j].type = CaseType.S8;
                        case "Z" -> tab_jeu[i][j].type = CaseType.S9;
                    }

                }

            }
        }
    }

    /**
     * méthode qui règle toutes les réponses sur l'événement "mouseReleased"
     */
    public void sourisRelacher() throws CloneNotSupportedException {

        chemin.afficherChemin();
        // verifier si ce type de case S_ est deja dans le tab_chemin et si le chemin va bien de S_ à S_
        if (!checkOccurenceChemin(chemin.chemin_courant[0].type) && chemin.prem_der_egales()) {

            // on verifie si c'est un bon chemin, si vrai on affiche le chemin (dans le fonction)
            verif_chemin();

        } else {
            // on remet le chemin_courante à 0
            chemin = new Chemin();
            // afficher le tableau des chemins
            afficherTabChemin();
        }
    }

    /**
     * méthode qui règle tous les evenements liée a la clique d'une souris
     * @param ci coördinate x de case tab_jeu ou le soursi a cliqué
     * @param cj coördinate y de case tab_jeu ou le soursi a cliqué
     */
    public void sourisCliquer(int ci, int cj){
        // verifier si on a déjà créé un chemin qui commence avec ce type
        if(checkOccurenceChemin(tab_jeu[ci][cj].type)) {
            // si vrai, on supprime le vieux chemin
            modificationSupprimer(tab_jeu[ci][cj].type);
            // et on rémets le chemin_courant à 0
            chemin = new Chemin();

            // si on essaye de comménce un chemin sur une case vide, on affiche un message dans la console
        } else if(tab_jeu[ci][cj].type == CaseType.empty){
            System.out.println("On n'a pas le droit de commencer sur cette case");
            chemin = new Chemin();
            // autrement on commence un nouveau chemin
        }else chemin.cheminStart(tab_jeu[ci][cj]);

    }

    /**
     * méthode qui fait les modifications necessaire quand on supprime un chemin de type
     * @param cheminType le type de chemin qu'on veut supprimer
     */
    private void modificationSupprimer(CaseType cheminType) {
        int indice = findOccurenceChemin(cheminType);
        // double verification que les deux types sont le même
        assert (cheminType == tab_chemin[indice].chemin_courant[0].type);

        System.out.println("on va supprimer le chemin " + tab_chemin[indice].chemin_courant[0].type);

        // change le type des cases concerncée en empty
        for (int i = 1; i < tab_chemin[indice].taille_chemin_courant - 1; i++) {

            int x = tab_chemin[indice].chemin_courant[i].x;
            int y = tab_chemin[indice].chemin_courant[i].y;
            tab_jeu[x][y].type = CaseType.empty;

        }

        // enlever le chemin du tableau de chemins
        enleverCheminSpecifique(indice);

        // pour mettre à jour l'affichage
        setChanged();
        notifyObservers();
    }

    /**
     * méthode qui enleve la dernière chemin crée avec un bouton undo
     */
    public void enleverDerniereChemin(){

        CaseType typeASupprimer = tab_chemin[nombre_chemin-1].chemin_courant[0].type;
        modificationSupprimer(typeASupprimer);

    }

    /**
     * méthode qui enleve un chemin specifique dans le tab_chemin (quand on clique sur l'icone)
     * * @param indiceDansTabChemin l'indice de chemin dans tab_chemin qui doit être supprimé
     */
    public void enleverCheminSpecifique(int indiceDansTabChemin){

        // on remplace le chemin par un nouveau chemin (donc vide)
        tab_chemin[indiceDansTabChemin] = new Chemin();

        // indice est le premiere place libre dans tab_chemin[]
        int i = indiceDansTabChemin ;
        do{
            // on deplace tous les chemins une à gauche dans le tab_chemin
            tab_chemin[i] = tab_chemin[i+1];
            i++;
        }while(i < nombre_chemin);
        nombre_chemin = nombre_chemin -1 ;
        afficherTabChemin();

    }


    /**
     * méthode qui vérifie si le chemin qu'on a déssiner, est déjà dans le tab_chemin[]
     * @return vrai si le chemin est dans le tab_chemin[], return false si le chemin n'est PAS dans le tab_chemin[]
     */
    public boolean checkOccurenceChemin(CaseType verifier) {
        for (Chemin value : tab_chemin) {
                // verifier si on n'a pas déjà un chemin qui commence avec S_
                if (value.chemin_courant[0].type == verifier) {
                    return true;
                }
            }

        return false;
    }

    /**
     * méthode qui vérifie si le chemin qu'on a déssiner, est déjà dans le tab_chemin[]
     * @return vrai si le chemin est dans le tab_chemin[], return false si le chemin n'est PAS dans le tab_chemin[]
     */
    public int findOccurenceChemin(CaseType verifier) {


        for (int i = 0; i < tab_chemin.length; i++) {
            // verifier si on n'a pas déjà un chemin qui commence avec S_
            if (tab_chemin[i].chemin_courant[0].type == verifier) {
                return i;
            }
        }

        return -1;
    }

    /**
     * méthode qui aide avec le debugage
     */
    public void afficherTabChemin(){
        for (int m = 0; m < nombre_chemin; m++) {
            tab_chemin[m].afficherChemin();
        }
    }


}
