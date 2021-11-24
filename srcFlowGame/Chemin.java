import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.Scanner;

//ATTENTION quand on fait (1,2) ça fait en réalité (2,1) REPERE CHANGE

public class Chemin {
    public String [][]tab_chemins;
    public String [][]tab_joueur;
    public int niveau;
    public int nbr_formes;
    public int dimension;
    public int f_majuscule;
    public int f_minuscule;
    public int nbr_formes_trouvees;

    public Chemin(int _dimension, int _niveau) throws IOException {
        //On init les symboles du chemin à null
        this.f_majuscule = 0;
        this.f_minuscule = 0;
        this.nbr_formes_trouvees = 0;

        //Implémentation de la grille corrigée
        this.niveau = _niveau;
        this.dimension = _dimension;
        String str_niveau = new String();
        String str_dimension = new String();
        String str_dim_niv = new String();
        str_niveau = String.valueOf(niveau);
        str_dimension = String.valueOf(dimension);
        str_dim_niv = str_dimension + " " + str_niveau;
        lire_fichier_texte(str_dim_niv);

        //Implémentation de la grille de départ du joueur
        creation_tab_joueur();

        //test
        CaseModele[] tab_cm = new CaseModele[4];
        CaseModele cm = new CaseModele();
        CaseModele cm1 = new CaseModele();
        CaseModele cm2 = new CaseModele();
        CaseModele cm3 = new CaseModele();
        cm.x = 4; cm.y = 0;
        tab_cm[0] = cm;
        cm1.x = 4 ; cm1.y = 1;
        tab_cm[1] = cm1;
        cm2.x = 4 ; cm2.y = 2;
        tab_cm[2] = cm2;
        cm3.x = 3 ; cm3.y = 2;
        tab_cm[3] = cm3;


        System.out.print( " =>  " + check_chemin_entier(tab_cm) +  " <=  ");

        ecrire_debut_partie();

    }

    public void lire_fichier_texte(String s) throws IOException {

        String chaine ="";
        String fichier = "../data/grilles.txt";
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
                    this.tab_chemins = new String[this.dimension][this.dimension];
                    for (int i = 0; i < this.dimension+1; i++){
                        chaine+=ligne=br.readLine();
                        chaine+=ligne+"\n";

                        if (i == 0){

                            Scanner sca = new Scanner(ligne);
                            this.nbr_formes = sca.nextInt();
                        }else{
                            //System.out.print("\n \n   *** \n Ligne " + i + "  : " + ligne);

                            Scanner scan = new Scanner(ligne);
                            scan.useDelimiter(" ");

                            for (int j = 0; j < this.dimension; j++){

                                this.tab_chemins[i-1][j] = scan.next();

                                //System.out.print("\n Colonne " + j + "  : " + v);
                            }
                        }
                    }

                    //vérifications
                    System.out.print( "\n  Jeu choisi : dimension = " + this.dimension + " niveau = " + this.niveau + " nbr de formes à relier = " + this.nbr_formes);
                    System.out.print("\n \n Version avec solutions : \n \n");
                    for (int i = 0 ; i < this.dimension ; i++){
                        for(int j = 0; j < this.dimension ; j++){
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

    public void creation_tab_joueur(){
        //1 - Conversion du String en ASCII
        int[][] tab_ascii = new int[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension; i++){
            for(int j=0 ; j < this.dimension; j++ ){
                tab_ascii[i][j]= string_to_ascii(this.tab_chemins[i][j])[0];
            }
        }


        //2 - On supprime les minuscules (qui sont les réponses) donc ASCII > 95
        this.tab_joueur = new String[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension; i++){
            for(int j=0 ; j < this.dimension; j++ ){
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
        for(int i = 0; i < this.dimension; i++){
            for(int j=0 ; j < this.dimension; j++ ){
                System.out.print(tab_joueur[i][j] + " ");
            }
            System.out.print("\n");
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

    public String recup_string(CaseModele c){
        int x = c.x;
        int y = c.y;
        String c_s = new String();
        c_s = this.tab_chemins[x][y];
        return c_s;
    }


    public boolean check_debut_fin_ok(CaseModele c){
        //1- On récupère le string aux coordonnées de c
        String c_s = new String();
        c_s = recup_string(c);


        //2- On transforme le string en ascii
        int c_ascii = string_to_ascii(c_s)[0];

        //3- On vérifie si c'est une majuscule = début ou fin de chemin
        if( c_ascii < 95){
            //3a - Si c'est un début majuscule doit etre initialiser et les minuscules seront +32 en ascii
            if( this.f_majuscule == 0){
                this.f_majuscule = c_ascii;
                this.f_minuscule = c_ascii + 32;
                return true;
            }
            //3b - Si c'est une fin majuscule est déjà initialisée et doit avoir la meme valeur que f_majuscule
            //     sinon ça voudrait dire que c'est la fin d'un autre chemin
            else {
                if(this.f_majuscule == c_ascii){
                    return true;
                }else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }


    // fonction qui verifie que le chemin en parametre est ok
    public boolean check_milieu(CaseModele c){
        //1- On récupère le string aux coordonnées de c
        String c_s = new String();
        c_s = recup_string(c);

        //2- On transforme le string en ascii
        int c_ascii = string_to_ascii(c_s)[0];

        //3- On vérifie si c'est une minuscule
        if(c_ascii > 95){
            //si c'est une minuscule doit déjà avoir cliqué sur une majuscule qui implémente minuscule
            //si ce n'est pas le cas minuscule = 0 et donc ce sera faux
            if (c_ascii == f_minuscule){
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

    public boolean check_chemin_entier(CaseModele[] tab_case){
        int taille = tab_case.length;

        //1- On vérifie le début : majuscule => choix forme

        if(check_debut_fin_ok(tab_case[0])){
            //2- On vérifie que le reste du chemin
            for(int i = 1 ; i < (taille-1); i++){
                if(!(check_milieu(tab_case[i]))){
                    System.out.print(" \n \n *** Vous n'avez pas le bon chemin *** \n \n ");
                    return false;
                }
            }
            //3- On vérifie que la fin est bien une majuscule
            if(check_debut_fin_ok(tab_case[taille-1])){
                return true;
            }
            else{
                System.out.print(" \n \n *** Vous ne terminez pas par la bonne forme *** \n \n ");
                return false;
            }
        }
        else{
            System.out.print(" \n \n *** Impossible, il faut commencer en cliquant sur une forme *** \n \n ");
            return false;
        }
    }

    public void ecrire_dans_score(String s) throws IOException{
        try(FileWriter fw = new FileWriter("../data/score.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(s);
        } catch (IOException e) {
        }
    }

    public void ecrire_debut_partie() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String s = new String();
        s = "Partie du " + dateFormat.format(date) + "\nScore :\n";
        ecrire_dans_score(s);
    }

    public static void main (String[] args) throws IOException {
        Chemin c = new Chemin(5, 2);

    }
}
