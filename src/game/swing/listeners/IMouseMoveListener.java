package game.swing.listeners;

import game.core.Square;

/**
 * Интерфейс слушателя перемещения мыши над клеткой.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IMouseMoveListener {
    /**
     * Простейшая реализация интерфейса без реализации
     * реакции на перемещения мыши.
     */
    IMouseMoveListener EMPTY = new IMouseMoveListener() {
        @Override
        public void mouseMove(Square s) {
        }
    };

    /**
     * Действия, которые необходимо выполнить при перемещении
     * мыши над клеткой.
     *
     * @param squareUnderMouse - клетка под мышкой.
     */
    void mouseMove(Square squareUnderMouse);
}
