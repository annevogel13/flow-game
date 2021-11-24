import java.util.Random;

public class CaseModele {
    public CaseType type;
    private static Random rnd = new Random();
    public int x, y;

    public CaseModele(){
        //rndType();
       // System.out.println("new rnd type casemodele");
    }

    public void rndType() {

        this.type = CaseType.values()[rnd.nextInt(CaseType.values().length)];
    }

}
