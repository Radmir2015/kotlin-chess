package game.swing

import game.core.*
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
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class GamePanel(val game: Game) : JPanel(BorderLayout()) {
    protected var control: GameControlPanel
    private var adorned: AdornedBoard = AdornedBoard()
    private var history: MovesHistory = MovesHistory(game.board.history)

    init {
        add(history, BorderLayout.LINE_END)

        control = GameControlPanel(game)
        add(control, BorderLayout.LINE_START)

        adorned = AdornedBoard()
        add(adorned, BorderLayout.CENTER)

        adorned.resizeBoard(game.board.nV, game.board.nH)

        when (game.boardKind) {
            BoardKind.EUROPE -> {
                val gameBoard = object : EuropeBoard(game) {
                    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.PLAIN -> {
                val gameBoard = object : GreenBoard(game) {
                    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.ASIA -> {
                val gameBoard = object : AsiaBoard(game) {
                    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.ASIA_CASTLE_RIVER -> {
                val gameBoard = object : AsiaBoardWithCastleRiver(game) {
                    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            else -> {
            }
        }
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