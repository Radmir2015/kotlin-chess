package game.core

/**
 * Доска игры может быть разных рамеров.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface ISizeable {
    /**
     * Выдать допустимые для данной игры размеры доски.
     *
     * @return массив пар (ширина, высота) доски.
     */
    val sizes: Array<IntArray>
}