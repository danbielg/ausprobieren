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

    public String getBoardSymbol() {
        if (color == PlayerColor.WHITE) {
            return name.label.toUpperCase();
        } else {
            return name.label.toLowerCase();
        }
    }

    public FigureName getName() {
        return name;
    }

    public boolean isMoveValid(Point startPosition, Point targetPosition, Figure[][] board) {
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

    private boolean isInMovements(Point targetMovement) {
        if (name == FigureName.BISHOP) { // läufer
            if (Math.abs(targetMovement.x) != Math.abs(targetMovement.y)) {
                return false;
            }

        } else if (name == FigureName.QUEEN) {
            if (targetMovement.x != 0 && targetMovement.y != 0 &&
                    (Math.abs(targetMovement.x) != Math.abs(targetMovement.y))) {
                return false;
            }
        }

        if (this.hasMultipleMovements) {
            targetMovement.x = normalizePoint(targetMovement.x);
            targetMovement.y = normalizePoint(targetMovement.y);
        }
        for (Point figureMovement : this.movements) {
            if (figureMovement.getX() == targetMovement.getX() &&
                    figureMovement.getY() == targetMovement.getY()) {
                return true;
            }
        }
        return false;
    }

    private int normalizePoint(int x) {
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

