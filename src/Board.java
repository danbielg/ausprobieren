import java.awt.*;

public class Board {
    // statisch final nicht abaenderbare variablen deklariert 3x
    public static final String EMPTY_LINE = "-----------------------------------";
    public static final String VERTICAL_BAR = " | ";
    public static final String STRAIGHT_LINE = "___________________________________";

    // statische final variable deklariert vom typ array
    static final char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    // 2 dimensionales array erstellt vom typ figure
    public static Figure[][] board = new Figure[8][8];
    // konstruktor des boards wo die figure objekte erstellt werden
    public Board() {
        // dem bord werden na bestimmten positionen figuren mit bestimmten eigenschaften zugewiesen
        board[0][0] = new Figure(PlayerColor.BLACK, FigureName.ROOK, FigureHelper.ROOK, true); // Black klein
        board[0][1] = new Figure(PlayerColor.BLACK, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[0][2] = new Figure(PlayerColor.BLACK, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[0][3] = new Figure(PlayerColor.BLACK, FigureName.QUEEN, FigureHelper.QUEEN, true);
        board[0][4] = new Figure(PlayerColor.BLACK, FigureName.KING, FigureHelper.KING, false);
        board[0][5] = new Figure(PlayerColor.BLACK, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[0][6] = new Figure(PlayerColor.BLACK, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[0][7] = new Figure(PlayerColor.BLACK, FigureName.ROOK, FigureHelper.ROOK, true);
        // mit der forschleife werden bauern erstellt
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Figure(PlayerColor.BLACK, FigureName.PAWN, FigureHelper.BLACK_PAWN, false);
        }

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Figure(PlayerColor.WHITE, FigureName.PAWN, FigureHelper.WHITE_PAWN, false);
        }

        board[7][0] = new Figure(PlayerColor.WHITE, FigureName.ROOK, FigureHelper.ROOK, true); // White gross
        board[7][1] = new Figure(PlayerColor.WHITE, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[7][2] = new Figure(PlayerColor.WHITE, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[7][3] = new Figure(PlayerColor.WHITE, FigureName.QUEEN, FigureHelper.QUEEN, true);
        board[7][4] = new Figure(PlayerColor.WHITE, FigureName.KING, FigureHelper.KING, false);
        board[7][5] = new Figure(PlayerColor.WHITE, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[7][6] = new Figure(PlayerColor.WHITE, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[7][7] = new Figure(PlayerColor.WHITE, FigureName.ROOK, FigureHelper.ROOK, true);
    }
    // hier wird das komplette board geprinted
    public void print() {
        // buchstaben werden geprinted
        printLetters();
        // absatz
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println(EMPTY_LINE);
            // i = int row ab der stelle 0( eine ganze zeile wird geschrieben)
            printBoardRow(i);
        }
        System.out.println(STRAIGHT_LINE);
        printLetters();
        System.out.println();
    }
    // methode die eine reihe erstellt
    private void printBoardRow(int row) {
        // + 1 damit wir bei 1 anfangen und nicht null
        System.out.print((row + 1) + VERTICAL_BAR);
        // hier geht er jede zelle in der reihe durch
        for (int column = 0; column < 8; column++) {
            // will dann wissen was fuer eine figur drauf ist
            Figure figure = board[row][column];
            // wenn da eine figur ist
            if (figure != null) {
                // dann soll er das symbol der figu raufprinten
                System.out.print(figure.getBoardSymbol());
            } else {
                // wen nicht lass es leer (-)
                System.out.print("-");
            }
            // dann wieder eine vertikale bar
            System.out.print(VERTICAL_BAR);
        }
        System.out.println();
    }
    // Methode die die zeile mit den buchstaben erstellt
    private static void printLetters() {
        System.out.print(" " + VERTICAL_BAR);
        // for each schleiufe fuer jeden buchstaben in: letters
        for (char letter : letters) {
            System.out.print(letter + VERTICAL_BAR);
        }
    }
    // hier soll die aktuelle position und die zielposition zurückgegeben werden
    private int[] getPositions(String currentPosition, String targetPosition) {
        // erstmal großbuchstaben zu kleinbuchstaben machen, currentposition an der stelle null minus dem "a" damit der wert in die ascii tabelle umgewandelt wird umso es verrechnen zu könmnen
        int currentColumn = Character.toLowerCase(currentPosition.charAt(0)) - 'a';
        // weil der zweite character eine zahl ist macht man minus "0" ( an der stelle null der ascii tabelle) und danach minus 1 weil wir das wieder zurueckrechnen muessen weil es ja bei null anfaengt
        int currentRow = currentPosition.charAt(1) - '0' - 1;
        // getauscht durch die einzelnen positionen an den stellen ... (0)....(1)
        int targetColumn = Character.toLowerCase(targetPosition.charAt(0)) - 'a';
        int targetRow = targetPosition.charAt(1) - '0' - 1;
        // hier werden die ganzen werte ausgegeben wenn ich gepoitions benutze
        return new int[]{currentRow, currentColumn, targetRow, targetColumn};
    }

    public boolean isMoveInsideBoard(String currentField, String targetField, PlayerColor player) {
        // hier wird überprüft ob die eingabe genau zwei zeichen lang sind
        if (currentField.length() == 2 && targetField.length() == 2) {
            // hier werden start und zielort  zugewiesen
            int[] positions = getPositions(currentField, targetField);
            // hier wird int[] position definiert
            int currentRow = positions[0];
            int currentColumn = positions[1];

            int targetRow = positions[2];
            int targetColumn = positions[3];

            // Bewegung innerhalb des Feldes wenn isinboardbounce true ist
            if (isInBoardBounds(currentRow, currentColumn) &&
                    isInBoardBounds(targetRow, targetColumn) &&
                    // hier wird geguzct ob an der startposition eine figur ist
                    !isFigureAtPointNull(currentRow, currentColumn) &&
                    // hier wird geguckt ob die ausgewählte figur dem spieler gehört wer dran ist
                    board[currentRow][currentColumn].getColor() == player &&
                    // hier wird geguckt ob auf dem ziel nichts steht
                    (isFigureAtPointNull(targetRow, targetColumn) ||
                            // oder es ist eine gegnerische figur
                            board[targetRow][targetColumn].getColor() != player)) {
                // hier entscheidet nicht das board weil an der stelle eine figur ist Figure.ismovevalid
                return board[currentRow][currentColumn].
                        isMoveValid(new Point(currentColumn, currentRow),
                                new Point(targetColumn, targetRow), board);
            }

        }
        return false;
    }
    // hier wird geguckt ob an einer stelle row und collumn eine figur ist
    private boolean isFigureAtPointNull(int row, int column) {
//        if (board[row][column] == null) {
//            return true;
//        } else {
//            return false;
//        }
        return board[row][column] == null;
    }
    // hier werden zeile und spalte übergeben
    private boolean isInBoardBounds(int row, int column) {
        // zeile und spalte MÜSSEN im spielfeld sein
        return row >= 0 && column >= 0 && row < 8 && column < 8;
    }
    // hier wird die methode erstellt die die figur bewegt
    public void move(String currentPosition, String targetposition) {
        // eine aray variable wird erstellt von den werten von getposition
        int[] positions = getPositions(currentPosition, targetposition);
        // hier werden die vier zeichen des arrays variablen zugewiesen
        int currentRow = positions[0]; // Spalte bestimmen
        int currentColumn = positions[1];

        int targetRow = positions[2];
        int targetColumn = positions[3];
        // hier wird die zielposition zur aktuellenposition dargestellt
        board[targetRow][targetColumn] = board[currentRow][currentColumn];
        // hier wird dann aus der position von der die Figur gestartete ist ein leeres feld gemacht
        board[currentRow][currentColumn] = null;

    }
    //methode die bestimmt ob der könig von einem jeweiligen spieler am leben ist
    public boolean isKingAlive(PlayerColor activePlayer) {
        // nonactivplayer ist wenn activplayer weiss ist dann ist nonactivplayer black wenn nciht dann weiss
        PlayerColor notActivePlayer = activePlayer == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        // für jede figur in der reihe vom board durchgegangen
        for (Figure[] row : board) {
            // hier soll dann bei jeder figure in der reihe... etwas geprüft werden
            for (Figure figure : row) {
                // wenn figure mit einer figur besetzt ist UND es ein könig ist UND vom gegnerischen spieler die figur ist
                if (figure != null && figure.getName() == FigureName.KING && notActivePlayer == figure.getColor())
                    // dann reurn true
                    return true;
            }
        }
        return false;
    }
}
