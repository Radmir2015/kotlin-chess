package chinachess

import chinachess.pieces.*
import chinachess.players.Confucious
import chinachess.players.SunTzu
import game.core.*
import game.core.players.Neznaika
import java.util.*

/**
 * Игра [Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class ChinaChess : Game() {
    companion object {
        private fun putPieces(board: Board, color: PieceColor) {
            val hPiece = if (color === PieceColor.BLACK) 0 else board.nH - 1
            val hGun = if (color === PieceColor.BLACK) 2 else board.nH - 3
            val hPawn = if (color === PieceColor.BLACK) 3 else board.nH - 4

            // Расставляем пешки через одну, на все четные линии
            var v = 0
            while (v < board.nV) {
                Pawn(board.getSquare(v, hPawn)!!, color)
                v += 2
            }

            // Guns on positions
            Gun(board.getSquare(1, hGun)!!, color)
            Gun(board.getSquare(7, hGun)!!, color)

            // Kings in the castle
            King(board.getSquare(4, hPiece)!!, color)

            // Guardians with it's king
            Guardian(board.getSquare(3, hPiece)!!, color)
            Guardian(board.getSquare(5, hPiece)!!, color)

            // Bishops in positions
            Bishop(board.getSquare(2, hPiece)!!, color)
            Bishop(board.getSquare(6, hPiece)!!, color)

            // Knights on positions
            Knight(board.getSquare(7, hPiece)!!, color)
            Knight(board.getSquare(1, hPiece)!!, color)

            // Rooks in positions
            Rook(board.getSquare(0, hPiece)!!, color)
            Rook(board.getSquare(8, hPiece)!!, color)
        }

        init {
            addPlayer(ChinaChess::class.java, IPlayer.HOMO_SAPIENCE)
            addPlayer(ChinaChess::class.java, Neznaika())
            addPlayer(ChinaChess::class.java, SunTzu())
            addPlayer(ChinaChess::class.java, Confucious())
        }
    }

    override fun initBoardDefault() {
        super.initBoardPanel(9, 10)
        putPieces(board, PieceColor.BLACK)
        putPieces(board, PieceColor.WHITE)
    }

    override val name: String
        get() = "China Chess"
    override val iconImageFile: String
        get() = "icoChinaChess.png"
    override val boardKind: BoardKind
        get() = BoardKind.ASIA_CASTLE_RIVER
    override val moveKind: MoveKind
        get() = MoveKind.PIECE_MOVE

    override fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece>, String> {
        val images: MutableMap<Class<out Piece>, String> = HashMap()
        when (color) {
            PieceColor.WHITE -> {
                images[Pawn::class.java] = "wPawn.png"
                images[Rook::class.java] = "wRook.png"
                images[Knight::class.java] = "wKnight.png"
                images[Bishop::class.java] = "wBishop.png"
                images[King::class.java] = "wKing.png"
                images[Guardian::class.java] = "wGuard.png"
                images[Gun::class.java] = "wGun.png"
            }
            PieceColor.BLACK -> {
                images[Pawn::class.java] = "bPawn.png"
                images[Rook::class.java] = "bRook.png"
                images[Knight::class.java] = "bKnight.png"
                images[Bishop::class.java] = "bBishop.png"
                images[King::class.java] = "bKing.png"
                images[Guardian::class.java] = "bGuard.png"
                images[Gun::class.java] = "bGun.png"
            }
            else -> {
            }
        }
        return images
    }

    override fun getPiece(square: Square, pieceColor: PieceColor): Piece = King(square, pieceColor)

    init {
        initBoardDefault()
        board.whitePlayer = IPlayer.HOMO_SAPIENCE
        board.blackPlayer = Neznaika()
    }
}