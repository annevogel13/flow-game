import java.io.*;
import java.util.Observable;
import java.util.Scanner;

public class Jeux extends Observable {
    public int size;
    public CaseModele [][] tab_jeu;
    public Chemin chemin ;//public int [][] cheminCourante; // pour stocker le chemin courante // misschien
    public String [][]tab_chemins;
    public String [][]tab_joueur;
    public int niveau;
    public int nbr_formes;



    public Jeux (int size, int _niveau) throws IOException {
        this.niveau = _niveau;
        this.size = size;

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

    public void construireChemin(CaseModele cm){
         CaseType cmType = cm.type;

        // verifier si une case est une case vide? --> type empty
        // verifier si on croise pas? --> type h...
        // verifier si une case est le fin ? --> exacte le meme type que le debut --> type S_
         switch(cmType){
             // etats debut/finaux
             case S1:
             case S2:
             case S3:
             case S4:
             case S5:
             case S6:
             case S7:
             case S8:
             case S9:
                    // debut d'une chemin
                    // TODO verifier si c'est le debut ou le fin d'une chemin

                    if((chemin.chemin_courant[0].type == null)&& chemin.mousepressed){ // donc debut de chemin (premiere pas)

                        chemin.ajouteCase(cm);
                        System.out.println("start chemin a partir de "+cmType);

                    }else if(chemin.checkDebutFin(cm)){
                        chemin.ajouteCase(cm);
                        System.out.println("fin de chemin "+cmType);
                    }


             break;

             case empty:
                 chemin.ajouteCase(cm);
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


}
