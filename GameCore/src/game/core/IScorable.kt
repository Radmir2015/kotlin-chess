package game.core

/**
 * Интерфейс для игр в которых можно показать текущий счет.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IScorable {
    /**
     * Счет для играющего фигурами заданного цвета.
     *
     * @param color цвет фигур
     * @return счет игрока
     */
    fun getScore(color: PieceColor): Int
}