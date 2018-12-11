package chess.pieces;

import chess.moves.Capture;
import chess.moves.EnPassant;
import chess.moves.Promotion;
import chess.moves.SimpleMove;
import game.core.*;

import java.util.List;

/**
 * Класс представляющий на доске пешку европейских шахмат.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Pawn extends ChessPiece {
    private boolean flgMakeDoubleMove;

    public Pawn(Square square, PieceColor color) {
        super(square, color);
    }

    public Square GetEnemyPieceForEnPassantMove(Square source, Square target) {
        int dv = Math.abs(target.v - source.v);
        //Firstly, we have to ensure whether either our or opponent's piece is situated on the right position
        if ((source.h == 3 && isWhite() || source.h == 4 && isBlack()) && dv == 1) {
            //Then we should find appropriate figure in the collection of enemies
            List<Piece> listEnemies = getEnemies();

            //Lets find the instance of enemy Pawn object, which satisfies the following requirements.
            for (Piece enemy : listEnemies) {
                if (enemy.getClass() == Pawn.class) {
                    flgMakeDoubleMove = (boolean) ((Pawn) enemy).IfMakeDoubleMove();
                }

                if (
                    //Cases which must be done for white piece
                    //1) The found piece is black
                    //2) Beginning from the top to bottom area, several subcases could be found, which are essential for successful executing of enpassant move.
                    // - The black pawn is on the third row AND
                    // - It makes double move simultaneously AND
                    // - Horizontal subcase: h-axes between black pawn and target point differs by exactly one square AND
                    // - Vertical subcase: v-axe position of black pawn and target point must be absolutely equal with each other
                        (isWhite() && enemy.isBlack() && enemy.square.h == 3 && flgMakeDoubleMove && enemy.square.h - 1 == target.h && enemy.square.v == target.v)

                                ||

                                //This case is a reverse pattern of aforementioned one, because the same rules should be shared for the opponent.
                                (isBlack() && enemy.isWhite() && enemy.square.h == 4 && flgMakeDoubleMove && enemy.square.h + 1 == target.h && enemy.square.v == target.v)) {
                    //If at least one case is true, then we return instance of the current object.
                    return enemy.square;
                }
            }

        }

        //Do we have the lack of instance of Pawn object after the unsuccessful loop execution mentioned before? In this case, we have to return null.
        return null;
    }


    /* Shortly speaking, this method returns the possible ways where exactly could move a selected chess piece.
     * Class MovePiecePromptListener has a method called "mouseMove", which is declared in interface IMouseMoveListener.
     *By focusing on some chess piece on the board, each square on the board will be checked (namely 64 times) through the loop, which is implemented in the method mentioned before.
     */
    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square source = square;
        Square target = squares[0];

        int dv = Math.abs(target.v - source.v);
        int dh = isWhite()
                ? source.h - target.h
                : target.h - source.h;


        if (dv != 0) {
            // Есть смещение пешки по вертикали.
            // Возможно это взятие пешкой вражеской фигуры.

            if (dv > 1)
                return false; // Смещение больше, чем на 1 клетку

            if (GetEnemyPieceForEnPassantMove(source, target) != null)
                return true;


            if (target.isEmpty())
                return false; // Бить некого.

            if (dh <= 0)      // Смещение пешки назад.
                return false; // Назад пешки не бьют.

            if (dh > 1)       // Так далеко пешки не бьют.
                return false;


            return true;
        }

        // По вертикали пешка фигуры не бьет.
        if (!target.isEmpty())
            return false;

        // Пошла ли пешка с начальной позиции.
        boolean isStartPosition = isWhite() ? source.h == 6 : source.h == 1;


        // Насколько клеток может пойти пешка.
        int upper = isStartPosition ? 2 : 1;

        if (isStartPosition && (dh == 2)) {
            // Пешка прыгает с начальной позиции.

            // Не пытается ли пешка перепыгнуть через фигуру (барьер)?
            int barierV = source.v;
            int barierH = (source.h + target.h) / 2;

            Board board = source.getBoard();
            if (!board.isEmpty(barierV, barierH))
                return false; // Перепрыгивать нельзя.
        }

        if ((1 <= dh) && (dh <= upper))
            return true;

        return false;
    }

    @Override
    public Move makeMove(Square... squares) {

        flgMakeDoubleMove = false;

        Square source = squares[0];
        Square target = squares[1];

        int dv = Math.abs(target.v - source.v);

        boolean isLastHorizontal = isWhite()
                ? target.h == 0 : target.h == 7;

        if (isLastHorizontal) // Ход на последнюю горизонталь.
            return new Promotion(squares);

        if (Math.abs(target.v - source.v) == 1) // Ход по диагонали.
        {
            Piece target_location = squares[1].getPiece();
            Square enemy_square = GetEnemyPieceForEnPassantMove(source, target);
            if (target_location == null && enemy_square != null && enemy_square.getPiece() != null) {
                return new EnPassant(squares, enemy_square);
            } else {
                return new Capture(squares);
            }
        }

        //Empassant
        if (Math.abs(target.h - source.h) == 2) // Ход по вертикали на 2 позиции
        {
            flgMakeDoubleMove = true;
        }

        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "";
    }

    public boolean IfMakeDoubleMove() {
        return flgMakeDoubleMove;
    }
}
