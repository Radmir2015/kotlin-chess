package game.swing

import game.core.Game
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * Составная панель для настольной игры:
 *
 * * доска с клетками
 * * панель истории партии (список ходов)
 * * управляющая панель
 *   * выбор игроков,
 *   * размера доски,
 *   * счет,
 *   * ...)
 *
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class GamePanel(val game: Game) : JPanel(BorderLayout()) {
    protected var control: GameControlPanel
    private var adorned: AdornedBoard = AdornedBoard()
    private var history: MovesHistory = MovesHistory(game.board.history)

    init {
        add(history, BorderLayout.LINE_END)

        control = GameControlPanel(game)
        add(control, BorderLayout.LINE_START)

        adorned = AdornedBoard()
        add(adorned, BorderLayout.CENTER)
    }

    /**
     * Вставить в панель игры доску с клетками.
     *
     * @param gameBoard
     * - вставляемая доска с клетками.
     */
    protected fun insertSquares(gameBoard: GameBoard) = adorned.insertSquares(gameBoard)

    /**
     * Изменить размеры доски.
     *
     * @param nV
     * - количество вертикалей.
     * @param nH
     * - количество горизонталей.
     */
    open fun resizeBoard(nV: Int, nH: Int) {
        // Новые размеры доски и расстановка фигур.
        game.initBoard(nV, nH)

        adorned.resizeBoard(nV, nH)
    }
}