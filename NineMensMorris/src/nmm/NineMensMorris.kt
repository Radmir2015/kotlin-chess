package nmm

import game.core.*
import game.core.players.Vinni

/**
 * Девять пляшущий человечков.
 * [Правила](https://archive.akopyan.ru/page.php?id=3603)
 */
class NineMensMorris : Game() {
    override val name = "NineMensMorris"
    override val iconImageFile = "icoNMM.png"
    override val boardKind = BoardKind.NINE_MANS
    override val moveKind = MoveKind.PIECE_PUT

    override fun initBoardDefault() {
        super.initBoardPanel(7, 7)
    }

    override fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece?>, String> {
        val images: MutableMap<Class<out Piece>, String> = HashMap()
        when (color) {
            PieceColor.WHITE -> images[NMMPiece::class.java] = "wStone.png"
            PieceColor.BLACK -> images[NMMPiece::class.java] = "bStone.png"
            else -> {
            }
        }
        return images
    }

    override fun getPiece(square: Square, pieceColor: PieceColor) = NMMPiece(square, pieceColor)

    init {
        super.initBoardPanel(7, 7)
        board.whitePlayer = IPlayer.HOMO_SAPIENCE
        board.blackPlayer = Vinni(pieceProvider)
    }

    companion object {
        private val pieceProvider: IPieceProvider = object : IPieceProvider {
            override fun getPiece(square: Square, color: PieceColor) = NMMPiece(square, color)
        }

        init {
            addPlayer(NineMensMorris::class.java, IPlayer.HOMO_SAPIENCE)
            addPlayer(NineMensMorris::class.java, Vinni(pieceProvider))
        }
    }
}
