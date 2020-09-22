package game.core.listeners

import game.core.Square

/**
 * Слушатель событий на доске.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IGameListener {
    /**
     * Нажата кнопка мыши над клеткой **s** доски.
     *
     * @param s      - клеткой доски.
     * @param button - номер кнопки.
     */
    fun mouseDown(s: Square, button: Int)

    /**
     * Отпущена кнопка мыши над клеткой **s** доски.
     *
     * @param s      - клеткой доски.
     * @param button - номер кнопки.
     */
    fun mouseUp(s: Square, button: Int)

    companion object {
        /**
         * Единственный экземпляр пустой реализации слушателя доски.
         */
        val EMPTY: IGameListener = object : IGameListener {
            override fun mouseDown(s: Square, button: Int) {}
            override fun mouseUp(s: Square, button: Int) {}
        }
    }
}