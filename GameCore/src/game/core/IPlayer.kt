package game.core

/**
 * Интерфейс для игроков (программ и человека).
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IPlayer {
    /**
     * Выдать имя игрока для отображения имени на панели игры.
     *
     * @return имя игрока.
     */
    val name: String

    /**
     * Выдать имя автора программы для отображения имени на панели игры.
     *
     * @return имя автора программы.
     */
    val authorName: String

    /**
     * Сделать ход на доске фигурой заданного цвета.
     *
     * @param board - доска для хода.
     * @param color - фигура какого цвета должна сделать ход.
     * @throws GameOver во время выполнения хода может возникнуть ситуация GameOver.
     * @see GameResult
     */
    @Throws(GameOver::class)
    fun doMove(board: Board, color: PieceColor)

    companion object {
        /**
         * Простейшая реализация игрока.
         * Все действия на доске делает человек.
         */
        @JvmField
        val HOMO_SAPIENCE: IPlayer = object : IPlayer {
            override val name: String
                get() = "Homo sapience"
            override val authorName: String
                get() = "Это я"

            override fun doMove(board: Board, color: PieceColor) {}
            override fun toString(): String = name
        }
    }
}