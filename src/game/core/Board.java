package game.core;

import java.util.*;

/**
 * Доска для расстановки фигур.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Board extends Observable {
    /**
     * Количество вертикалей на доске.
     */
    public int nV;

    /**
     * Количество горизонталей на доске.
     */
    public int nH;
    /**
     * История партии (последовательность ходов игры).
     */
    public History history = new History(this);
    private Map<PieceColor, IPlayer> players = new HashMap<>();
    /**
     * Клетки доски.
     */
    private Square[][] squares;
    /**
     * Цвет фигуры которая должна сделать ход.
     */
    private PieceColor moveColor = PieceColor.WHITE;

    {
        setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        setBlackPlayer(IPlayer.HOMO_SAPIENCE);
    }

    public Board() {
        reset(0, 0);
    }

    /**
     * Дать цвет противоположный заданному цвету.
     *
     * @param color - заданный цвет фигуры.
     * @return противоположный цвет фигур.
     */
    static
    public PieceColor getOpponentColor(PieceColor color) {
        return color == PieceColor.WHITE
                ? PieceColor.BLACK : PieceColor.WHITE;
    }

    /**
     * Изменить размеры доски и очистить историю игры.
     *
     * @param nV - количество вертикалей доски.
     * @param nH - количество горизонталей доски.
     */
    void reset(int nV, int nH) {
        this.nV = nV;
        this.nH = nH;

        squares = new Square[nV][nH];
        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++)
                squares[v][h] = new Square(this, v, h);

        history.clear();
        moveColor = PieceColor.WHITE;

        setBoardChanged();
    }

    /**
     * Уведомить обозревателей доски (классы реализующие интерфейс <b>Observable</b>)<br>
     * что на доске произошли изменения.
     *
     * @see Observable
     * @see java.util.Observer
     */
    public void setBoardChanged() {
        // Вызвать protected метод базового класса - Observer.
        super.setChanged();
        super.notifyObservers();
    }

    /**
     * Смена цвета (игрока который должен сделать ход).
     */
    public void changeMoveColor() {
        for (; ; ) {
            moveColor = getOpponentColor(moveColor);

            IPlayer player = players.get(moveColor);
            if (player == IPlayer.HOMO_SAPIENCE)
                break; // Ход сделает человек мышкой.

            try {
                player.doMove(this, moveColor);
            } catch (GameOver e) {
                break;
            }
        }
    }

    /**
     * Запуск цикла передачи ходов от одного игрока к другому.
     * Выход из цикла при завершении игры или
     * при передаче хода игроку - человеку.
     */
    public void startGame() {
        for (; ; ) {
            IPlayer player = players.get(moveColor);
            if (player == IPlayer.HOMO_SAPIENCE)
                break; // Ход сделает человек.

            try {
                player.doMove(this, moveColor);
            } catch (GameOver e) {
                break;
            }

            moveColor = getOpponentColor(moveColor);
        }
    }

    /**
     * Выдать цвет фигуры, которая должна сделать ход.
     *
     * @return - цвет фигуры.
     */
    public PieceColor getMoveColor() {
        return moveColor;
    }

    /**
     * Вернуть клетку доски.
     *
     * @param v - вертикаль клетки.
     * @param h - горизонталь клетки.
     * @return клетка с задаными вертикалью и горизонталью.
     */
    public Square getSquare(int v, int h) {
        return squares[v][h];
    }

    /**
     * Проверка выхода координат клетки за границы доски.
     *
     * @param v - вертикаль клетки
     * @param h - горизонталь клетки
     * @return есть ли клетка с такими координатами на доске.
     */
    public boolean onBoard(int v, int h) {
        if (v < 0) return false;
        if (h < 0) return false;

        return (h <= nH - 1) && (v <= nV - 1);
    }

    /**
     * @param v - вертикаль
     * @param h - горизонталь
     * @return Пуста ли клетка с заданными координатами?
     */
    public boolean isEmpty(int v, int h) {
        return getSquare(v, h).isEmpty();
    }

    /**
     * Выдать игрока белыми фигурами.
     *
     * @return игрок белыми фигурами
     */
    public IPlayer getWhitePlayer() {
        return players.get(PieceColor.WHITE);
    }

    /**
     * Задать игрока белыми фигурами.
     *
     * @param player - игрок белыми фигурами.
     * @see IPlayer
     */
    public void setWhitePlayer(IPlayer player) {
        players.put(PieceColor.WHITE, player);
        setBoardChanged();
    }

    /**
     * Выдать игрока черными фигурами.
     *
     * @return игрок черными фигурами
     */
    public IPlayer getBlackPlayer() {
        return players.get(PieceColor.BLACK);
    }

    /**
     * Задать игрока черными фигурами.
     *
     * @param player - игрок черными фигурами.
     * @see IPlayer
     */
    public void setBlackPlayer(IPlayer player) {
        players.put(PieceColor.BLACK, player);
        setBoardChanged();
    }

    /**
     * Выдать список всех расположенных на доске фигур заданного цвета.
     *
     * @param color - цвет фигуры.
     * @return - список фигур.
     */
    public List<Piece> getPieces(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();

        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++) {
                Square s = getSquare(v, h);

                Piece p = s.getPiece();
                if (p == null) continue;

                if (p.color != color)
                    continue;

                pieces.add(p);
            }

        return pieces;
    }

    /**
     * Выдать список всех пустых клеток доски.
     *
     * @return - список всех клеток доски.
     */
    public List<Square> getEmptySquares() {
        List<game.core.Square> emptySquares = new ArrayList<>();

        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++) {
                game.core.Square square = getSquare(v, h);
                if (square.isEmpty())
                    emptySquares.add(square);
            }

        return emptySquares;
    }

    /**
     * Для заданной фигуры найти список клеток, на которые ход данной фигурой
     * допустим.
     *
     * @param piece - проверяемая фигура.
     * @return список допустимых для хода клеток.
     */
    public List<Square> getPieceTargets(Piece piece) {
        List<Square> targets = new ArrayList<>();

        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++) {
                Square target = getSquare(v, h);

                if (piece.isCorrectMove(target))
                    targets.add(target);
            }

        return targets;
    }

    /**
     * Выдать список всех клеток доски.
     *
     * @return - список
     */
    public List<Square> getSquares() {
        List<Square> allSquares = new ArrayList<>();

        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++)
                allSquares.add(getSquare(v, h));

        return allSquares;
    }

    /**
     * Максимальное расстояние между клетками доски.
     *
     * @return расстояние между клетками
     */
    public int maxDistance() {
        return nH + nV;
    }
}
