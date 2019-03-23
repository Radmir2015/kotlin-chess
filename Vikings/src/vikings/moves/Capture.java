package vikings.moves;

import game.core.*;
import game.core.moves.ICaptureMove;
import vikings.pieces.Cyning;
import vikings.pieces.VikingsPiece;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * ПОБЕДА БЕЛЫХ
 * 1.	Если им удаётся поставить короля на открытую прямую к одной из таких клеток,
 * они объявляют «Raichi» (шах), если сразу на две прямые — Tuichi (мат).
 *
 * ПОБЕДА ЧЕРНЫХ
 * 1.	Король считается захваченным, когда его окружают с четырёх сторон.
 * При этом сторонами могут считаться угловые клетки, трон, и стороны доски.
 *
 * 2.	Когда королю угрожает опасность быть захваченным следующим ходом,
 * чёрные предупреждают белых (шах королю).
 *
 * УНИЧТОЖЕНИЕ ФИШЕК
 * 1.	Если фишка своим ходом зажимает фишку противника между собой и другой фишкой
 * или между собой и угловым квадратом, фишка противника считается съеденной.
 * Съедаться может более одной фишки за раз.
 *
 * 2.	Черные фишки может входить между двумя вражескими, в этом случае она не считается съеденной.
 * При этом белые могут спокойно поставить свою фишку между двумя чёрными.
 * </pre>
 */
public class Capture extends SimpleMove implements ICaptureMove {
    /**
     * Захваченные фигуры противника.
     */
    private final List<Piece> captured;

    public Capture(List<Piece> captured, Square[] squares) {
        super(squares);
        this.captured = captured;
    }

    /**
     * Вернуть список захваченных фигур.
     *
     * @param source - откуда пошла вражеская фигура.
     * @param target - куда пошла вражеская фигура.
     * @return список клеток где стоят захваченные фигуры противника.
     */
    static
    public List<Piece> collectCaptured(Square source, Square target) {
        return source.getPiece().getEnemies()
                .stream()
                .filter(p -> isCaptured(p, source, target))
                .collect(Collectors.toList());
    }

    /**
     * Захвачена ли заданная фигура <b>piece</b> фигурами противника?
     *
     * @param piece  - заданная фигура.
     * @param source - откуда пошла вражеская фигура.
     * @param target - куда пошла вражеская фигура.
     * @return захвачена заданая фигура или нет.
     */
    static
    private boolean isCaptured(Piece piece, Square source, Square target) {
        return piece instanceof Cyning
                ? isKingCaptured(piece, source, target)
                : isPieceCaptured(piece, source, target);
    }

    /**
     * Захвачен ли простой викинг фигурами противника с 2-х сторон?
     *
     * @param piece  - фигура - простой викинг.
     * @param source - откуда пошла вражеская фигура.
     * @param target - куда пошла вражеская фигура.
     * @return захватывается ли фигура вражескими фигурами.
     */
    static
    private boolean isPieceCaptured(Piece piece, Square source, Square target) {
        // Есть ли окружение фигуры с 2-х сторон по горизонтали?
        if (isCaptureSide(piece, source, target, Dirs.LEFT) &&
                isCaptureSide(piece, source, target, Dirs.RIGHT))
            return true;

        // Есть ли окружение фигуры с 2-х сторон по вертикали?
        return isCaptureSide(piece, source, target, Dirs.UP) &&
                isCaptureSide(piece, source, target, Dirs.DOWN);
    }

    /**
     * Захвачен ли король фигурами противника с 4-х сторон?
     *
     * @param king   - король
     * @param source - откуда пошла вражеская фигура.
     * @param target - куда пошла вражеская фигура.
     * @return захватывается ли король вражескими фигурами.
     */
    static
    private boolean isKingCaptured(Piece king, Square source, Square target) {
        // Короля на троне захватить нельзя.
        if (VikingsPiece.isTron(king.square))
            return false;

        // Есть ли окружение короля с 4-х сторон.
        return isCaptureSide(king, source, target, Dirs.LEFT) &&
                isCaptureSide(king, source, target, Dirs.RIGHT) &&
                isCaptureSide(king, source, target, Dirs.UP) &&
                isCaptureSide(king, source, target, Dirs.DOWN);
    }

    /**
     * В заданном направлении от фигуры <b>piece</b> могут находится:
     * <ul>
     * <li>клетка-трон,</li>
     * <li>клетка-выход,</li>
     * <li>край лоски,</li>
     * <li>вражеская фигура</li>
     * </ul>
     * которые используются для окружения викинга или короля.
     *
     * @param piece - проверяемая на окружение фигура.
     * @param dir   - заданное направление от проверяемой фигуры.
     * @return может ли клетка на этой стороне фигуры быть использована для
     * окружения проверяемой фигуры.
     */
    static
    private boolean isCaptureSide(Piece piece, Square source, Square target, Dirs dir) {
        boolean isKing = (piece instanceof Cyning);

        // В этом направлении край доски, клетки нет.
        // Окружение на краю доски возможно только для короля.
        if (!piece.square.hasNext(dir)) return isKing;

        Square next = piece.next(dir);

        // В этом направлении трон. Окружение возможно.
        if (VikingsPiece.isTron(next) && isKing) return true;

        // В этом направлении выход. Окружение возможно.
        if (VikingsPiece.isExit(next)) return true;

        // В этом направлении клетка с которой ушла вражеская фигура.
        // Окружение невозможно.
        if (next == source) return false;

        // В этом направлении клетка на которую пришла вражеская фигура.
        // Окружение возможно.
        if (next == target) return true;

        // В этом направлении пустая клетка.
        // Окружение невозможно.
        if (next.isEmpty()) return false;

        // Если в этом направлении вражеская фигура,
        // то окружение фигуры возможно.
        return next.getPiece().isEnemy(piece);
    }

    @Override
    public List<Square> getCaptured() {
        return captured
                .stream()
                .map(p -> p.square)
                .collect(Collectors.toList());
    }

    public List<Piece> getCapturedPieces() {
        return captured;
    }

    @Override
    public void doMove() throws GameOver {
        super.doMove();

        // Для каждой клетки фигуры убираем ссылку на эту фигуру.
        captured.forEach(p -> p.square.removePiece());

        // Дошел ли белый король до выхода?
        if ((piece instanceof Cyning) && VikingsPiece.isExit(piece.square))
            throw new GameOver(GameResult.WHITE_WIN);

        // Есть ли среди захваченных фигур белый король?
        if (captured.stream().anyMatch(p -> p instanceof Cyning))
            throw new GameOver(GameResult.BLACK_WIN);
    }

    @Override
    public void undoMove() {
        super.undoMove();

        // Для каждой клетки фигуры ставим ссылку на фигуру.
        captured.forEach(p -> p.square.setPiece(p));
    }

    public String toString() {
        return "" + piece + source + "x" + target;
    }
}
