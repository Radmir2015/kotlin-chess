package renju.swing

import game.core.Board
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.swing.AsiaBoard
import java.awt.Image

class RenjuBoard : AsiaBoard(Board()) {
    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage(piece: Piece): Image? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
