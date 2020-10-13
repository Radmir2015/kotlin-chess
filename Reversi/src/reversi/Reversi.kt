package reversi

import game.core.*
import game.core.players.Vinni
import reversi.pieces.Hole
import reversi.pieces.Stone
import reversi.players.Owl
import reversi.players.Tiger
import java.util.*

/**
 * Игра
 * [Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Reversi private constructor(nHoles: Int) : Game(), IScorable {
    companion object {
        private val pieceProvider: IPieceProvider = object : IPieceProvider {
            override fun getPiece(square: Square, color: PieceColor): Piece = Stone(square, color)
        }

        init {
            addPlayer(Reversi::class.java, IPlayer.HOMO_SAPIENCE)
            addPlayer(Reversi::class.java, Vinni(pieceProvider))
            addPlayer(Reversi::class.java, Owl(pieceProvider))
            addPlayer(Reversi::class.java, Tiger(pieceProvider))
        }
    }

    constructor() : this(0) {}

    override fun initBoardDefault() {
        super.initBoardPanel(8, 8)
        Stone(board.getSquare(3, 3)!!, PieceColor.BLACK)
        Stone(board.getSquare(4, 4)!!, PieceColor.BLACK)
        Stone(board.getSquare(3, 4)!!, PieceColor.WHITE)
        Stone(board.getSquare(4, 3)!!, PieceColor.WHITE)
    }

    override val name: String = "Reversi"
    override val iconImageFile: String = "icoReversi.png"
    override val boardKind: BoardKind = BoardKind.PLAIN
    override val moveKind: MoveKind = MoveKind.PIECE_PUT

    override fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece>, String> {
        val images: MutableMap<Class<out Piece>, String> = HashMap()
        when (color) {
            PieceColor.WHITE -> images[Stone::class.java] = "StoneWhite.png"
            PieceColor.BLACK -> images[Stone::class.java] = "StoneBlack.png"
            else -> {
            }
        }
        return images
    }

    override fun getPiece(square: Square, pieceColor: PieceColor): Piece = Stone(square, pieceColor)

    /**
     * Вернуть инициализированную доску для игры в реверси.
     */
    init {
        initBoardDefault()
        board.whitePlayer = IPlayer.HOMO_SAPIENCE
        board.blackPlayer = Vinni(pieceProvider)
        if (nHoles != 0) {
            val randomV = (8 * Math.random()).toInt()
            val randomH = (8 * Math.random()).toInt()
            val randomSquare = board.getSquare(randomV, randomH)
            Hole(randomSquare!!, PieceColor.BLACK)
        }
    }
}