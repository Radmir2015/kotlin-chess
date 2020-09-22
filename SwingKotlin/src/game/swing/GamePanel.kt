package game.swing

import game.core.*
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box
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
    private var control: GameControlPanel
    private var adorned: AdornedBoard = AdornedBoard()
    private var history: MovesHistory = MovesHistory(game.board.history)

    init {
        add(history, BorderLayout.LINE_END)

        control = GameControlPanel(game)
        add(control, BorderLayout.LINE_START)

        adorned = AdornedBoard()
        add(adorned, BorderLayout.CENTER)

        adorned.resizeBoard(game.board.nV, game.board.nH)

        var gameBoard: GameBoard? = null

        when (game.boardKind) {
            BoardKind.EUROPE -> {
                gameBoard = object : EuropeBoard(game) {
                    override fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece? {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.PLAIN -> {
                gameBoard = object : GreenBoard(game) {
                    override fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece? {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.ASIA -> {
                gameBoard = object : AsiaBoard(game) {
                    override fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece? {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.ASIA_CASTLE_RIVER -> {
                gameBoard = object : AsiaBoardWithCastleRiver(game) {
                    override fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece? {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            BoardKind.ASIA_CASTLE -> {
                gameBoard = object : AsiaBoardWithCastle(game) {
                    override fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece? {
                        return game.getPiece(mouseSquare, moveColor)
                    }
                }
                insertSquares(gameBoard)
            }
            else -> {
            }
        }

        gameBoard?.game?.initBoardPanel(gameBoard)

        if (game is ISizeable) {
            val bsp = BoardSizePanel(this, game.sizes)
            bsp.alignmentX = Component.CENTER_ALIGNMENT

            control.add(Box.createRigidArea(Dimension(0, 5)))
            control.add(bsp)
        }

        val scorePanel = ScorePanel(game)
        scorePanel.alignmentX = Component.CENTER_ALIGNMENT

        control.add(Box.createRigidArea(Dimension(0, 5)))
        control.add(scorePanel)
    }

    /**
     * Вставить в панель игры доску с клетками.
     *
     * @param gameBoard
     * - вставляемая доска с клетками.
     */
    private fun insertSquares(gameBoard: GameBoard) = adorned.insertSquares(gameBoard)

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
        game.initBoardPanel(nV, nH)

        adorned.resizeBoard(nV, nH)
    }
}