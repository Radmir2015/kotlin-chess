package game.core.players

import game.core.*

/**
 * Винни - простой игрок для игр в которых ставятся фигуры на доску.
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Vinni : PutPiecePlayer {
    override val name: String
        get() = "Винни"
    override val authorName: String
        get() = "Романов В.Ю."

    override fun toString(): String = name

    private var maxMoves = 80

    /**
     * Винни - простой игрок для игр в которых ставятся фигуры на доску.
     * Он случайным образом выбирает клетку на которую можно поставить фигуру.
     */
    constructor(pieceProvider: IPieceProvider, maxMoves: Int) : super(pieceProvider) {
        this.pieceProvider = pieceProvider
        this.maxMoves = maxMoves
    }

    /**
     * Винни - простой игрок для игр в которых ставятся фигуры на доску.
     * Он случайным образом выбирает клетку на которую можно поставить фигуру.
     */
    constructor(pieceProvider: IPieceProvider) : super(pieceProvider) {
        this.pieceProvider = pieceProvider
    }

    @Throws(GameOver::class)
    override fun doMove(board: Board, color: PieceColor) {
        val correctMoves = getCorrectMoves(board)
        if (correctMoves.isEmpty()) return

        // Винни делает случайный ход.
        correctMoves.shuffle()
        val randomMove = correctMoves[0]

        try {
            randomMove.doMove()
        } catch (e: GameOver) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(randomMove)
            board.history.result = e.result

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged()
            throw GameOver(GameResult.DRAWN)
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(randomMove)

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged()

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.moves.size > maxMoves) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.result = GameResult.DRAWN
            throw GameOver(GameResult.DRAWN)
        }
    }
}