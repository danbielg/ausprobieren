// Enumeration welche zustaende die einzelnen figur haben werden
public enum FigureName {

    ROOK("t"), KNIGHT("s"), PAWN("b"), BISHOP("l"), KING("k"), QUEEN("q");
    // Variable labe deklariert
    public final String label;
    // der konstruktor des enums
    FigureName(String label) {
        this.label = label;
    }
}
