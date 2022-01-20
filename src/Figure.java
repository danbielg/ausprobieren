import java.awt.Point;
import java.util.List;

public class Figure {

    private PlayerColor color;
    private FigureName name;
    private List<Point> movements;
    private boolean hasMultipleMovements;
    private boolean hasTwoSteps;

    public Figure(PlayerColor color, FigureName name, List<Point> movements, boolean hasMultipleMovements) {
        this.color = color;
        this.name = name;
        this.movements = movements;
        this.hasMultipleMovements = hasMultipleMovements;
        if (FigureName.PAWN == name) {
            hasTwoSteps = true;
        }
    }

    public PlayerColor getColor() {
        return color;
    }
    // gibt den symbol der jeweiligen figur wieder /klein/grossbuchstaben
    public String getBoardSymbol() {
        if (color == PlayerColor.WHITE) {
            return name.label.toUpperCase();
        } else {
            return name.label.toLowerCase();
        }
    }
    // gibt den namen zurueck
    public FigureName getName() {
        return name;
    }
    // überprüfen ob der move im moveset der figur passt
    public boolean isMoveValid(Point startPosition, Point targetPosition, Figure[][] board) {
        // hier wird das zielmovement bestimmt in dem man die anfangswerte und die zielwert miteinander subtrahiert
        Point targetMovement = new Point(targetPosition.x - startPosition.x,
                targetPosition.y - startPosition.y);
        // wenn es nicht im movesemt vom figurehelkper ist dann
        if (!isInMovements(targetMovement)) {
            return false;
        }
        // wenn er ein multiplemovement hat dann
        if (this.hasMultipleMovements) {
            // startposition wird zu currentposition
            Point currentPosition = startPosition;
            // wenn die x koordinate sich aender oder die y koordinate sich aendert
            while (currentPosition.x != targetPosition.x || currentPosition.y != targetPosition.y) {
                // hier wird die entfernung normaliziert und movement zugeordnet
                int movementX = normalizePoint(targetMovement.x);
                int movementY = normalizePoint(targetMovement.y);
                //  hier wird movement (normaliziert) mit der startposition addiert um so die einzelnen schritte zu gehen
                int nextPositionX = currentPosition.x + movementX;
                int nextPositionY = currentPosition.y + movementY;
                // wenn die nächste position ungleich der zielposition ist UND an dem platz eine figur ist dann return false
                if ((nextPositionX != targetPosition.x || nextPositionY != targetPosition.y) &&
                        board[nextPositionY][nextPositionX] != null) {
                    return false;
                }
                // wenn nciht rechne den EINEN schritt eiter
                currentPosition.x += movementX;
                currentPosition.y += movementY;

            }
            // wenn du kein multiplemovemnets hast dann
        } else {
            // hier wird die entfernung normaliziert und movement zugeordnet
            int movementX = normalizePoint(targetMovement.x);
            int movementY = normalizePoint(targetMovement.y);
            //  hier wird movement (normaliziert) mit der startposition addiert um den einzelnen schritt zu gehen
            int nextPositionX = startPosition.x + movementX;
            int nextPositionY = startPosition.y + movementY;
            // wenn es der bauer ist dann
            if (name == FigureName.PAWN) {
                // wenn der absolut y zwei schritte machen will UND er noch ein two steps hat
                if (Math.abs(targetMovement.y) == 2 && hasTwoSteps) {
                    // wenn dann di naechste position eine figur steht ODER
                    if (board[nextPositionY][nextPositionX] != null ||
                            // ODER an der zweiten schritt (letzetr schritt) eine figur ist retun false
                            board[nextPositionY + movementY][nextPositionX] != null) {
                        return false;
                    }
                // wenn er dann weniger als 1 sich in y achse verschiebt dann return false
                } else if (Math.abs(targetMovement.y) > 1) {
                    return false;
                }
                // wenn er sich in x und y  achse 1 bewegt UND
                if (Math.abs(movementX) == 1 && Math.abs(movementY) == 1 &&
                        // Und das naechste feld frei ist frei ist return false
                        board[nextPositionY][nextPositionX] == null) {
                    return false;
                }
                // wenn er sich in y achse einen bewegt und in x achse 0 bewegt UND
                if (Math.abs(movementY) == 1 && movementX == 0 &&
                        // UND der naechste schritt eine figur ist return false
                        board[nextPositionY][nextPositionX] != null) {
                    return false;
                }
                // wenn die farbe weiss ist und die naechste position 0 ist
                if (color == PlayerColor.WHITE && nextPositionY == 0 ||
                        // oder schwarz ist und  die naechste position 7 ist
                        color == PlayerColor.BLACK && nextPositionY == 7) {
                    // dann wird der bauer zur queen
                    name = FigureName.QUEEN;
                    // kriegt das moveset
                    movements = FigureHelper.QUEEN;
                    // und de multiplemovements auf true
                    hasMultipleMovements = true;

                }

                hasTwoSteps = false;
            }
        }

        return true;
    }
    // methode die zurueckgibt ob der aktuelle move im figurehelper der figur ist
    private boolean isInMovements(Point targetMovement) {
        // wenn es um den bauern geht dann...
        if (name == FigureName.BISHOP) { // läufer
            // wenn der abs wert vom x wert nicht y wert nicht
            if (Math.abs(targetMovement.x) != Math.abs(targetMovement.y)) {
                return false;
            }
            // wenn es um die koenigin geht dann...
        } else if (name == FigureName.QUEEN) {
            // wenn x wert UND y wert nicht null sind UND abs wert vom x targetmovement nicht abs wert vom y movement
            if (targetMovement.x != 0 && targetMovement.y != 0 &&
                    (Math.abs(targetMovement.x) != Math.abs(targetMovement.y))) {
                return false;
            }
        }
        // wenn der wert hasmultipalmovemnts true ist dann
        if (this.hasMultipleMovements) {
            // man nimmt die die abs werte von y und y und weisst es variablen zu
            targetMovement.x = normalizePoint(targetMovement.x);
            targetMovement.y = normalizePoint(targetMovement.y);
        }
        // hier wird geguckt ob der movemnet im figurehelpüer zu finden ist
        for (Point figureMovement : this.movements) {
            // wenn die wert im figurehelper x und y zu finden sind dann mache true
            if (figureMovement.getX() == targetMovement.getX() &&
                    figureMovement.getY() == targetMovement.getY()) {
                return true;
            }
        }
        return false;
    }
    // eine mathematische operation wo der absolutwert uebernommen wird
    private int normalizePoint(int x) {
        // hier soll der wert mit sich selbst geteilt werden wenn es nicht 0 ist
        return x == 0 ? 0 : x / Math.abs(x);
    }
}

//case TURM:
//        if (Math.abs(currentPosition.x - targetPosition.x) == 0
//        ^ targetPosition.y - currentPosition.y == 0) {
//        return true;
//        }
//
//        return false;
//
//        case KOENIG:
//        int shiftX = Math.abs(currentPosition.x - targetPosition.x);
//        int shiftY = Math.abs(currentPosition.y - targetPosition.y);
//
//        if (shiftX == 0 ^ shiftY == 0 && shiftX + shiftY == 1) {
//        return true;
//        }
//
//        if (shiftX == 1 && shiftY == 1) {
//        return true;
//        }
//
//        return false;
//
//        case LAEUFER:
//        shiftX = Math.abs(currentPosition.x - targetPosition.x);
//        shiftY = Math.abs(currentPosition.y - targetPosition.y);
//
//        return shiftX > 0 && shiftY > 0 && shiftX == shiftY;
//
//        case KOENIGIN:
//        shiftX = Math.abs(currentPosition.x - targetPosition.x);
//        shiftY = Math.abs(currentPosition.y - targetPosition.y);
//
//        if (shiftX > 0 && shiftY > 0 && shiftX == shiftY) { // Läufer
//        return true;
//        }
//
//        if (Math.abs(currentPosition.x - targetPosition.x) == 0 // absolut sorgt dafür das das vorzeichen nicht berücksichtig wird (Betrag) TURM
//        ^ targetPosition.y - currentPosition.y == 0) { // Bildverarbeitendes Koordinatensystem wird bei der Programmierung benutzt TURM
//        return true;
//        }
//
//        return false;
//
//        case SPRINGER:
//        shiftX = Math.abs(currentPosition.x - targetPosition.x);
//        shiftY = Math.abs(currentPosition.y - targetPosition.y);
//
//        if (shiftX == 2 && shiftY == 1 || shiftX == 1 && shiftY == 2) {
//        return true;
//        }
//
//        return false;

