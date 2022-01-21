import java.util.Scanner;

public class Chess {

    public static void main(String[] args) {

        boolean isFinished = false;
        PlayerColor activePlayer = PlayerColor.WHITE;
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        while (!isFinished) {
            board.print();
            String characters = activePlayer == PlayerColor.WHITE ? "Großbuchstaben" : "Kleinbuchstaben";
            System.out.println("Spieler " + activePlayer + " (" + characters + ") ist am Zug");
            String inputCurrentPosition, inputTargetPosition;

            do {
                System.out.print("Bitte Gib den Figurenplatz an den du Benutzen möchtest: ");
                inputCurrentPosition = scanner.next();
                System.out.print("Wohin soll die Figur gehen? ");
                inputTargetPosition = scanner.next();
            }
            while (!board.isMoveInsideBoard(inputCurrentPosition, inputTargetPosition, activePlayer));

            board.move(inputCurrentPosition, inputTargetPosition);

            isFinished = !board.isKingAlive(activePlayer);

            if (!isFinished) {
                activePlayer = PlayerColor.BLACK == activePlayer ? PlayerColor.WHITE : PlayerColor.BLACK;
            } else {
                System.out.println(activePlayer + " ist !!! Chessmaster !!!");
            }

        }
    }
}
