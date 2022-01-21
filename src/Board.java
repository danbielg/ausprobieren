import java.awt.*;

public class Board {
    public static final String EMPTY_LINE = "-----------------------------------";
    public static final String VERTICAL_BAR = " | ";
    public static final String STRAIGHT_LINE = "___________________________________";

    static final char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    public static Figure[][] board = new Figure[8][8];

    public Board() {
        board[0][0] = new Figure(PlayerColor.BLACK, FigureName.ROOK, FigureHelper.ROOK, true); // Black klein
        board[0][1] = new Figure(PlayerColor.BLACK, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[0][2] = new Figure(PlayerColor.BLACK, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[0][3] = new Figure(PlayerColor.BLACK, FigureName.QUEEN, FigureHelper.QUEEN, true);
        board[0][4] = new Figure(PlayerColor.BLACK, FigureName.KING, FigureHelper.KING, false);
        board[0][5] = new Figure(PlayerColor.BLACK, FigureName.BISHOP, FigureHelper.BISHOP, true);
        board[0][6] = new Figure(PlayerColor.BLACK, FigureName.KNIGHT, FigureHelper.KNIGHT, false);
        board[0][7] = new Figure(PlayerColor.BLACK, FigureName.ROOK, FigureHelper.ROOK, true);

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

    public void print() {
        printLetters();
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println(EMPTY_LINE);
            printBoardRow(i);
        }
        System.out.println(STRAIGHT_LINE);
        printLetters();
        System.out.println();
    }

    private void printBoardRow(int row) {
        System.out.print((row + 1) + VERTICAL_BAR);
        for (int column = 0; column < 8; column++) {
            Figure figure = board[row][column];
            if (figure != null) {
                System.out.print(figure.getBoardSymbol());
            } else {
                System.out.print("-");
            }
            System.out.print(VERTICAL_BAR);
        }
        System.out.println();
    }

    private static void printLetters() {
        System.out.print(" " + VERTICAL_BAR);
        for (char letter : letters) {
            System.out.print(letter + VERTICAL_BAR);
        }
    }

    private int[] getPositions(String currentPosition, String targetPosition) {

        int currentColumn = Character.toLowerCase(currentPosition.charAt(0)) - 'a';
        int currentRow = currentPosition.charAt(1) - '0' - 1;

        int targetColumn = Character.toLowerCase(targetPosition.charAt(0)) - 'a';
        int targetRow = targetPosition.charAt(1) - '0' - 1;

        return new int[]{currentRow, currentColumn, targetRow, targetColumn};
    }

    public boolean isMoveInsideBoard(String currentField, String targetField, PlayerColor player) {
        if (currentField.length() == 2 && targetField.length() == 2) {

            int[] positions = getPositions(currentField, targetField);

            int currentRow = positions[0];
            int currentColumn = positions[1];

            int targetRow = positions[2];
            int targetColumn = positions[3];

            if (isInBoardBounds(currentRow, currentColumn) &&

                    isInBoardBounds(targetRow, targetColumn) &&
                    !isFigureAtPointNull(currentRow, currentColumn) &&
                    board[currentRow][currentColumn].getColor() == player &&
                    (isFigureAtPointNull(targetRow, targetColumn) ||
                            board[targetRow][targetColumn].getColor() != player)) {

                return board[currentRow][currentColumn].
                        isMoveValid(new Point(currentColumn, currentRow),
                                new Point(targetColumn, targetRow), board);
            }

        }
        return false;
    }

    private boolean isFigureAtPointNull(int row, int column) {
//        if (board[row][column] == null) {
//            return true;
//        } else {
//            return false;
//        }
        return board[row][column] == null;
    }

    private boolean isInBoardBounds(int row, int column) {
        return row >= 0 && column >= 0 && row < 8 && column < 8;
    }

    public void move(String currentPosition, String targetposition) {
        int[] positions = getPositions(currentPosition, targetposition);

        int currentRow = positions[0]; // Spalte bestimmen
        int currentColumn = positions[1];

        int targetRow = positions[2];
        int targetColumn = positions[3];

        board[targetRow][targetColumn] = board[currentRow][currentColumn];
        board[currentRow][currentColumn] = null;

    }

    public boolean isKingAlive(PlayerColor activePlayer) {
        PlayerColor notActivePlayer = activePlayer == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;

        for (Figure[] row : board) {
            for (Figure figure : row) {
                if (figure != null && figure.getName() == FigureName.KING && notActivePlayer == figure.getColor())
                    return true;
            }
        }
        return false;
    }
}
