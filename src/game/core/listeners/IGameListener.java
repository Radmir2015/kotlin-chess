package game.core.listeners;

import game.core.Square;

/**
 * Слушатель событий на доске.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IGameListener {
    /**
     * Единственный экземпляр пустой реализации слушателя доски.
     */
    IGameListener EMPTY = new IGameListener() {
        @Override
        public void mouseDown(Square s, int button) {
        }

        @Override
        public void mouseUp(Square s, int button) {
        }
    };

    /**
     * Нажата кнопка мыши над клеткой <b>s</b> доски.
     *
     * @param s      - клеткой доски.
     * @param button - номер кнопки.
     */
    void mouseDown(Square s, int button);

    /**
     * Отпущена кнопка мыши над клеткой <b>s</b> доски.
     *
     * @param s      - клеткой доски.
     * @param button - номер кнопки.
     */
    void mouseUp(Square s, int button);
}
