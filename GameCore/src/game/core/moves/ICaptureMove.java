package game.core.moves;

import game.core.Move;
import game.core.Square;

import java.util.List;

/**
 * Интерфейс для игр на которых фигура может захватывать фигуры противника.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ICaptureMove extends Move {
    /**
     * Вернуть клетки на которых стоят захваченные фигуры.
     */
    List<Square> getCaptured();
}
