package game.core.moves;

import game.core.Move;
import game.core.Square;

/**
 * Интерфейс для игр на которых фигура ставится на доску.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IPutMove extends Move {
    /**
     * Вернуть клетку куда пошла фигура.
     */
    Square getTarget();
}
