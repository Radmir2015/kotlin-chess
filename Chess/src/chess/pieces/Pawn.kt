package chess.pieces

import chess.moves.Capture
import chess.moves.EnPassant
import chess.moves.Promotion
import chess.moves.SimpleMove
import game.core.Move
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Класс представляющий на доске пешку европейских шахмат.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Pawn(square: Square, color: PieceColor) : ChessPiece(square, color) {
    private var longStep = false

    private fun GetEnemyPieceForEnPassantMove(source: Square, target: Square): Square? {
        val dv = Math.abs(target.v - source.v)
        //Firstly, we have to ensure whether either our or opponent's piece is situated on the right position
        if ((source.h == 3 && isWhite || source.h == 4 && isBlack) && dv == 1) {
            //Then we should find appropriate figure in the collection of enemies
            val listEnemies = enemies

            //Lets find the instance of enemy Pawn object, which satisfies the following requirements.
            for (enemy in listEnemies) {
                if (enemy.javaClass == Pawn::class.java) {
                    longStep = (enemy as Pawn).isLongStep()
                }
                if (//Cases which must be done for white piece
                //1) The found piece is black
                //2) Beginning from the top to bottom area, several subcases could be found, which are essential for successful executing of enpassant move.
                // - The black pawn is on the third row AND
                // - It makes double move simultaneously AND
                // - Horizontal subcase: h-axes between black pawn and target point differs by exactly one square AND
                // - Vertical subcase: v-axe position of black pawn and target point must be absolutely equal with each other
                        isWhite && enemy.isBlack && enemy.square.h == 3 && longStep && enemy.square.h - 1 == target.h && enemy.square.v == target.v
                        ||  //This case is a reverse pattern of aforementioned one, because the same rules should be shared for the opponent.
                        isBlack && enemy.isWhite && enemy.square.h == 4 && longStep && enemy.square.h + 1 == target.h && enemy.square.v == target.v) {
                    //If at least one case is true, then we return instance of the current object.
                    return enemy.square
                }
            }
        }

        //Do we have the lack of instance of Pawn object after the unsuccessful loop execution mentioned before? In this case, we have to return null.
        return null
    }

    /* Shortly speaking, this method returns the possible ways where exactly could move a selected chess piece.
     * Class MovePiecePromptListener has a method called "mouseMove", which is declared in interface IMouseMoveListener.
     *By focusing on some chess piece on the board, each square on the board will be checked (namely 64 times) through the loop, which is implemented in the method mentioned before.
     */
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val source = square
        val target = squares[0]

        val dv = abs(target.v - source.v)
        val dh = if (isWhite) source.h - target.h else target.h - source.h

        if (dv != 0) {
            // Есть смещение пешки по вертикали.
            // Возможно это взятие пешкой вражеской фигуры.
            if (dv > 1) return false // Смещение больше, чем на 1 клетку
            if (GetEnemyPieceForEnPassantMove(source, target) != null) return true
            if (target.isEmpty) return false // Бить некого.

            return if (dh <= 0) false else dh <= 1 // Назад пешки не бьют.

            // Так далеко пешки не бьют.
        }

        // По вертикали пешка фигуры не бьет.
        if (!target.isEmpty) return false

        // Пошла ли пешка с начальной позиции.
        val isStartPosition = if (isWhite) source.h == 6 else source.h == 1


        // Насколько клеток может пойти пешка.
        val upper = if (isStartPosition) 2 else 1
        if (isStartPosition && dh == 2) {
            // Пешка прыгает с начальной позиции.

            // Не пытается ли пешка перепыгнуть через фигуру (барьер)?
            val barierV = source.v
            val barierH = (source.h + target.h) / 2
            val board = source.board
            if (!board.isEmpty(barierV, barierH)) return false // Перепрыгивать нельзя.
        }
        return dh in 1..upper
    }

    override fun makeMove(vararg squares: Square): Move {
        val source = squares[0]
        val target = squares[1]
        longStep = abs(target.h - source.h) == 2

        val isLastHorizontal = if (isWhite) target.h == 0 else target.h == 7
        if (isLastHorizontal) // Ход на последнюю горизонталь.
            return Promotion(source, target)

        if (abs(target.v - source.v) == 1) { // Ход по диагонали.
            val target_location = squares[1].getPiece()
            val enemy_square = GetEnemyPieceForEnPassantMove(source, target)

            return if (isEnPassant(target_location, enemy_square))
                EnPassant(source, target, enemy_square!!)
            else
                Capture(source, target)
        }

        return SimpleMove(source, target)
    }

    private fun isEnPassant(target_location: Piece?, enemy_square: Square?) =
            target_location == null && enemy_square != null && enemy_square.getPiece() != null

    override fun toString(): String = ""

    private fun isLongStep(): Boolean = longStep
}