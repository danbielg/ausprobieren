public enum FigureName {

    ROOK("t"), KNIGHT("s"), PAWN("b"), BISHOP("l"), KING("k"), QUEEN("q");
    public final String label;

    FigureName(String label) {
        this.label = label;
    }
}
