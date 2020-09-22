package game.core;

/**
 * Доска игры может быть разных рамеров.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ISizeable {
    /**
     * Выдать допустимые для данной игры размеры доски.
     *
     * @return массив пар (ширина, высота) доски.
     */
    int[][] getSizes();
}
