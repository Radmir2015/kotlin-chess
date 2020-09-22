package game.core.moves;

import game.core.Move;
import game.core.Square;

/**
 * Интерфейс для игр на которых фигура передвигается на доске.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ITransferMove extends Move {
    /**
     * Вернуть клетку откуда пошла фигура.
     */
    Square getSource();

    /**
     * Вернуть клетку куда пошла фигура.
     */
    Square getTarget();
}
