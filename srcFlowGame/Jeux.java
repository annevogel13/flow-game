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
        // de chemin_courant[1] jusqua l'avant derniere
        for (int i = 1; i < chemin.taille_chemin_courant - 1; i++) {

            chemin.troisCaseDeduireType(chemin.chemin_courant[i - 1], chemin.chemin_courant[i], chemin.chemin_courant[i + 1], tab_jeu);
            chemin.chemin_courant[i].type = tab_jeu[chemin.chemin_courant[i].x][chemin.chemin_courant[i].y].type;

            System.out.println("change " + chemin.chemin_courant[i].x + "," + chemin.chemin_courant[i].y + " en " + chemin.chemin_courant[i].type);

            setChanged();
            notifyObservers();
        }
    }

    /**
     * méthode qui vérifie si le chemin qu'on a déssiner, n'est pas déjà dans le tab_chemin[]
     * @return vrai si le chemin n'est PAS dans le tab_chemin[], return false si le chemin EST dans le tab_chemin[]
     */
    public boolean checkOccurenceChemin() {
        CaseType verifier = chemin.chemin_courant[0].type;
        for (int i = 0; i < tab_chemin.length; i++) {
            // verifier si on n'a pas déjà un chemin qui commence avec S_
            if (tab_chemin[i].chemin_courant[0].type == verifier) {
                return false;
            }
        }
        return true;
    }

    // TODO redonant
    public void construireChemin(int x, int y){
         CaseType cm = this.tab_jeu[x][y].type;
         //on vérifie que la première case soit une forme
        if(chemin.chemin_courant[0].type==CaseType.empty){
            System.out.print("\n LIGNE 53 \n");
            if((cm==CaseType.S1)||(cm==CaseType.S2)||(cm==CaseType.S3)||(cm==CaseType.S4)||(cm==CaseType.S5)||(cm==CaseType.S6)||(cm==CaseType.S7)||(cm==CaseType.S8)||(cm==CaseType.S9)){
                System.out.print("\n LIGNE 55 \n");
                chemin.chemin_courant[0].x = x;
                chemin.chemin_courant[0].y = y;
                chemin.chemin_courant[0].type = cm;
                chemin.taille_chemin_courant = chemin.taille_chemin_courant +1 ;
                System.out.print("Premiere case du chemin est " + chemin.chemin_courant[0].type + "\n" );
            }
        }
        else{
            System.out.print("\n LIGNE 63 \n");
            if(!(chemin.chemin_courant[0].type==CaseType.empty) && (chemin.chemin_courant[0].x != x) || (chemin.chemin_courant[0].y != y)){
                System.out.print("\n LIGNE 65 \n");
                ajouteCaseModelChemin(x, y);
            }
            else{
                System.out.print("\n ==> " + chemin.chemin_courant[0].type + "   " + (chemin.chemin_courant[0].x != x) +" "+ (chemin.chemin_courant[0].y != y) +"\n");
            }
        }
    }

    public void verif_chemin(){
        boolean bool1;
        boolean bool2;
        //1- on vérifie que la première et dernière case soient égales
        bool1 = chemin.prem_der_egales();
        //2 - on vérifie que les cases se suivent
        bool2 = chemin.verif_chemin();

        System.out.print( " ===> "+ bool1 + " " +bool2+"\n");
        //3- on l'ajoute au tableau de chemins trouvés
        if(bool1 && bool2){
            tab_chemin[nombre_chemin].chemin_courant = chemin.chemin_courant;
            //System.out.print(" ==> *************"  + tab_chemin[nombre_chemin].chemin_courant[0].x);
            nombre_chemin += 1;

            chemin = new Chemin();
        }
    }

    // TODO redonant
    public void ajouteCaseModelChemin(int x, int y){
        System.out.print("\nLIGNE 117 : taille chemin courant =  " +  chemin.taille_chemin_courant+"\n");

        int derniereCaseRempli = chemin.taille_chemin_courant;

        chemin.chemin_courant[derniereCaseRempli] = tab_jeu[x][y];

        chemin.taille_chemin_courant += 1 ;


    }

    public void lire_fichier_texte(String s) throws IOException {

        String chaine ="";
        String fichier = "C:\\Users\\Merel\\IdeaProjects\\lifap7\\data\\grilles.txt"; //"../data/grilles.txt";
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
    public void sourisRelacher(){

        chemin.afficherChemin();
        if(checkOccurenceChemin()) {

            affichageCheminGrille();
            verif_chemin();

        }else {
            System.out.println("on a déjà une chemin pour "+chemin.chemin_courant[0].type);
            // afficher le tableau des chemins
            for (int m = 0; m < nombre_chemin; m++) {
                tab_chemin[m].afficherChemin();
            }
        }
    }

    public void enleverCheminGrille(CaseType S_){
            // TODO enlever des chemins si on clique dessous

    }
}
