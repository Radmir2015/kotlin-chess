package game.core.listeners

import game.core.Square

/**
 * Интерфейс слушателя перемещения мыши над клеткой.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IMouseMoveListener {
    /**
     * Действия, которые необходимо выполнить при перемещении
     * мыши над клеткой.
     *
     * @param squareUnderMouse - клетка под мышкой.
     */
    fun mouseMove(squareUnderMouse: Square)

    companion object {
        /**
         * Простейшая реализация интерфейса без реализации
         * реакции на перемещения мыши.
         */
        val EMPTY = object : IMouseMoveListener {
            override fun mouseMove(squareUnderMouse: Square) = Unit
        }
    }
}