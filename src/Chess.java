import java.util.Scanner;

public class Chess {

    public static void main(String[] args) {
        // ist der König geschlagen?
        boolean isFinished = false;
        // Festgelegt wer anfängt
        PlayerColor activePlayer = PlayerColor.WHITE;
        // Brett wird erstellt
        Board board = new Board();
        // Aufforderung einer Eingabe
        Scanner scanner = new Scanner(System.in);
        // while schleife bis der könig geschlagen wird
        while (!isFinished) {
            // board wird geprintet
            board.print();
            // tenerer ausdruck kleines if else ?true :false
            String characters = activePlayer == PlayerColor.WHITE ? "Großbuchstaben" : "Kleinbuchstaben";
            // ausgabe wer grade am zug ist
            System.out.println("Spieler " + activePlayer + " (" + characters + ") ist am Zug");
            // festgelegt das die eingabewerte als strings genommen wird
            String inputCurrentPosition, inputTargetPosition;
            // do weil er mindestens einmal abfragen muss wie der zug ist der zug aber nciht valide ist
            do {
                // ausgabe
                System.out.print("Bitte Gib den Figurenplatz an den du Benutzen möchtest: ");
                // die eingabe wird inputcurrentposition zugewiesen
                inputCurrentPosition = scanner.next();
                // ausgabe
                System.out.print("Wohin soll die Figur gehen? ");
                // die eingabe wird inputtargetposition zugewiesen
                inputTargetPosition = scanner.next();
            //prüfe ob der zug valide ist
            } while (!board.isMoveInsideBoard(inputCurrentPosition, inputTargetPosition, activePlayer));
            // die methode move bewegt die figur
            board.move(inputCurrentPosition, inputTargetPosition);
            // es ist vorbei wenn der könig vom dem der jetzt dran wär tot ist
            isFinished = !board.isKingAlive(activePlayer);
            // wenn könig tot ist
            if (!isFinished) {
                // hier wird bestimmt wer grade nicht dran ist
                activePlayer = PlayerColor.BLACK == activePlayer ? PlayerColor.WHITE : PlayerColor.BLACK;
            } else {
                // wenn jmand tot ist dann wird das ausgegeben
                System.out.println(activePlayer + " ist !!! Chessmaster !!!");
            }

        }
    }
}
