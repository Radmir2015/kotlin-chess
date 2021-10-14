package japanchess

import game.core.*
import game.core.players.Neznaika

/**
 * Японские шахматы:
 * https://ru.wikipedia.org/wiki/%D0%A1%D1%91%D0%B3%D0%B8
 */
class JapanChess : Game() {
    override val name = "JapanChess"
    override val iconImageFile = "icoShogi.png"
    override val boardKind = BoardKind.PLAIN
    override val moveKind = MoveKind.PIECE_MOVE

    /**
     * Расстановка фигур в начальную позицию.
     */
    init {
        initBoardDefault()
        board.whitePlayer = IPlayer.HOMO_SAPIENCE
        board.blackPlayer = Neznaika()
    }

    override fun getPiece(square: Square, pieceColor: PieceColor): Piece {
        TODO("Not yet implemented")
    }

    override fun initBoardDefault() {
        super.initBoardPanel(9, 10)
        putPieces(board, PieceColor.BLACK)
        putPieces(board, PieceColor.WHITE)
    }

    private fun putPieces(board: Board, black: PieceColor) {
    }

    override fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece>, String> {
        val images = HashMap<Class<out Piece>, String>()

        // TODO

        return images
    }

    companion object {
        private val pieceProvider: IPieceProvider = object : IPieceProvider {
            override fun getPiece(square: Square, color: PieceColor) = TODO("Not yet implemented")
        }

        init {
            addPlayer(JapanChess::class.java, IPlayer.HOMO_SAPIENCE)
            addPlayer(JapanChess::class.java, Neznaika())
        }
    }
}
