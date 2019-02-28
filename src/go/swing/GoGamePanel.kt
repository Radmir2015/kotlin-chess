package go.swing

import game.core.*
import game.core.listeners.PutPieceListener
import game.core.listeners.PutPiecePromptListener
import game.swing.AsiaBoard
import game.swing.GamePanel
import game.swing.images.GameImages
import go.Go
import go.pieces.GoPiece
import java.awt.Image

/**
 * Панель для игры в Го.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class GoGamePanel(boardSize: Int) : GamePanel(Go(boardSize)) {
    constructor() : this(10)

    init {
        insertSquares(GoBoardPanel(game))
    }
}

/**
 * Панель для игры [Го](https://ru.wikipedia.org/wiki/%D0%93%D0%BE)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class GoBoardPanel(game: Game) : AsiaBoard(game), IPieceProvider {
    init {
        listener = PutPieceListener(this)

        // Слушатель мыши выдающий подсказки для клеток -
        // можно ли ставить фигуру на клетку доски.
        mouseMoveListener = PutPiecePromptListener(this)
    }

    override fun getImage(piece: Piece): Image? = getPieceImage(piece, piece.color)

    override fun getPieceImage(piece: Piece, color: PieceColor): Image {
        return if (color == PieceColor.WHITE)
            GameImages.stoneWhite
        else
            GameImages.stoneBlack
    }

    override fun getPiece(square: Square, color: PieceColor): Piece {
        return GoPiece(square, color)
    }
}