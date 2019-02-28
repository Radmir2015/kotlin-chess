package chess.swing

import chess.Chess
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.core.listeners.MovePiecePromptListener
import game.swing.EuropeBoard
import game.swing.GamePanel

/**
 * Панель для доски стандартных шахмат.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ChessBoard : GamePanel(Chess()) {
    init {
        val gameBoard = object : EuropeBoard(game) {
            override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        gameBoard.listener = MovePieceListener(gameBoard)
        gameBoard.mouseMoveListener = MovePiecePromptListener(gameBoard)

        insertSquares(gameBoard)
    }
}