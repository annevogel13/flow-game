public class CaseModele {
    public CaseType type;
    public CaseType type_chemin;
    public int x, y, direction_case_suivante;

    public CaseModele(int _i, int _j) {
        x = _i;
        y = _j;
        type = CaseType.empty;
        type_chemin = CaseType.empty;

    }
}
