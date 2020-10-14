package renju

import game.core.*
import game.core.players.Vinni
import renju.pieces.RenjuPiece
import java.util.*

/**
 * Игра [Рэндзю](https://ru.wikipedia.org/wiki/%D0%A0%D1%8D%D0%BD%D0%B4%D0%B7%D1%8E)
 */
class Renju : Game() {
    companion object {
        private val pieceProvider: IPieceProvider = object : IPieceProvider {
            override fun getPiece(square: Square, color: PieceColor): Piece = RenjuPiece(square, color)
        }

        init {
            addPlayer(Renju::class.java, IPlayer.HOMO_SAPIENCE)
            addPlayer(Renju::class.java, Vinni(pieceProvider))
        }
    }

    override fun initBoardDefault() {
        super.initBoardPanel(15, 15)
    }

    override val name: String = "Renju"
    override val iconImageFile: String = "icoRenju.png"
    override val boardKind: BoardKind = BoardKind.ASIA
    override val moveKind: MoveKind = MoveKind.PIECE_PUT

    override fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece>, String> {
        val images: MutableMap<Class<out Piece>, String> = HashMap()
        when (color) {
            PieceColor.WHITE -> images[RenjuPiece::class.java] = "StoneWhite.png"
            PieceColor.BLACK -> images[RenjuPiece::class.java] = "StoneBlack.png"
            else -> {
            }
        }
        return images
    }

    override fun getPiece(square: Square, pieceColor: PieceColor): Piece = RenjuPiece(square, pieceColor)

    init {
        initBoardDefault()
        board.whitePlayer = IPlayer.HOMO_SAPIENCE
        board.blackPlayer = Vinni(pieceProvider)
    }
}