import java.util.Random;

public class CaseModele {
    public CaseType type;
    private static Random rnd = new Random();
    public int x, y, direction_case_suivante;

    public CaseModele(int _i, int _j){
        x = _i;
        y = _j;
        //rndType();
       // System.out.println("new rnd type casemodele");
    }

    public void rndType() {

        this.type = CaseType.values()[rnd.nextInt(CaseType.values().length)];
    }

}
