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

        if (!isInMovements(targetMovement)) {
            return false;
        }

        if (this.hasMultipleMovements) {
            Point currentPosition = startPosition;

            while (currentPosition.x != targetPosition.x || currentPosition.y != targetPosition.y) {
                int movementX = normalizePoint(targetMovement.x);
                int movementY = normalizePoint(targetMovement.y);

                int nextPositionX = currentPosition.x + movementX;
                int nextPositionY = currentPosition.y + movementY;

                if ((nextPositionX != targetPosition.x || nextPositionY != targetPosition.y) &&
                        board[nextPositionY][nextPositionX] != null) {
                    return false;
                }

                currentPosition.x += movementX;
                currentPosition.y += movementY;

            }
            // schräg schlagen mit bauern
        } else {
            int movementX = normalizePoint(targetMovement.x);
            int movementY = normalizePoint(targetMovement.y);

            int nextPositionX = startPosition.x + movementX;
            int nextPositionY = startPosition.y + movementY;

            if (name == FigureName.PAWN) {
                if (Math.abs(targetMovement.y) == 2 && hasTwoSteps) {
                    if (board[nextPositionY][nextPositionX] != null ||
                            board[nextPositionY + movementY][nextPositionX] != null) {
                        return false;
                    }

                } else if (Math.abs(targetMovement.y) > 1) {
                    return false;
                }

                if (Math.abs(movementX) == 1 && Math.abs(movementY) == 1 &&
                        board[nextPositionY][nextPositionX] == null) {
                    return false;
                }

                if (Math.abs(movementY) == 1 && movementX == 0 &&
                        board[nextPositionY][nextPositionX] != null) {
                    return false;
                }

                if (color == PlayerColor.WHITE && nextPositionY == 0 ||
                        color == PlayerColor.BLACK && nextPositionY == 7) {
                    name = FigureName.QUEEN;
                    movements = FigureHelper.QUEEN;
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

