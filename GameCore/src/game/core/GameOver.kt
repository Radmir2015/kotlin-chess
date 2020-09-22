package game.core

/**
 * Ситуация окончания игры. Параметр ситуации - результат игры
 * (переменная типа GameResult)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class GameOver(@JvmField val result: GameResult) : Exception() {
    companion object {
        private const val serialVersionUID = 1L
    }
}