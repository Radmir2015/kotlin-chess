package chess.moves

import game.core.Square
import game.core.moves.ICaptureMove

/**
 * Ход европейских шахмат - взятие пешки на проходе.
 * https://ru.wikipedia.org/wiki/Взятие_на_проходе
 */
class EnPassant(source: Square, target: Square, enemy_square: Square) : Capture(source, target), ICaptureMove {
    init {
        capturedSquare = enemy_square
        capturedPiece = enemy_square.getPiece()!!
    }
}