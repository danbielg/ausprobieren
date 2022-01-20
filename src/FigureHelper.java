import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class FigureHelper {
    // final = nicht aenderbar static = es muss kein objekt erstellt werden um es zu nutzen
    // Liste vom typ point BlackPawn = machen eine liste vom typ point die aus einem array besteht
    public final static List<Point> BLACK_PAWN = Arrays.asList(
            new Point(0, 1),
            new Point(1, 1),
            new Point(-1, 1),
            new Point(0, 2)
    );
    public final static List<Point> WHITE_PAWN = Arrays.asList(
            new Point(0, -1),
            new Point(-1, -1),
            new Point(1, -1),
            new Point(0, -2)
    );
    public final static List<Point> KING = Arrays.asList(
            new Point(-1, -1),
            new Point(-1, 0),
            new Point(0, -1),
            new Point(1, 1),
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 1),
            new Point(1, -1)
    );
    public final static List<Point> ROOK = Arrays.asList( // Turm
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 0),
            new Point(0, -1)
    );
    public final static List<Point> BISHOP = Arrays.asList( // Läufer
            new Point(1, 1),
            new Point(-1, -1),
            new Point(-1, 1),
            new Point(1, -1)
    );
    public final static List<Point> KNIGHT = Arrays.asList( // Springer
            new Point(-2, -1),
            new Point(-2, 1),
            new Point(2, -1),
            new Point(2, 1),
            new Point(-1, -2),
            new Point(1, -2),
            new Point(1, 2),
            new Point(-1, 2)
    );
    public final static List<Point> QUEEN = Arrays.asList(
            new Point(-1, -1),
            new Point(-1, 0),
            new Point(0, -1),
            new Point(1, 1),
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 1),
            new Point(1, -1)
    );
}
