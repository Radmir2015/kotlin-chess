package game.swing

import game.core.Game
import game.core.IPlayer
import java.awt.Color
import java.awt.Font
import java.util.stream.Collectors
import javax.swing.*
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.event.ListSelectionEvent

/**
 * Панель выбора игроков для игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class PlayersPanel
/**
 * Создание панели выбора игроков для игры.
 * @param game
 */
(game: Game) : JPanel(true) {
    /**
     * Список игроков для заданной игры.
     */
    private val players: List<IPlayer>

    init {
        val board = game.board

        // Получим всех игроков для этой игры.
        players = game.getPlayers(game.javaClass)

        // Получим индексы в списке игроков для текущих
        // белого и черного игроков этой игры.
        val wPlayerNumber = getPlayerIndex(board.whitePlayer, players)
        val bPlayerNumber = getPlayerIndex(board.blackPlayer, players)

        font = Font("mono", 10, Font.BOLD or Font.ITALIC)
        background = Color(0, 80, 0, 255)
        val boxLayout = BoxLayout(this, BoxLayout.Y_AXIS)
        layout = boxLayout

        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.alignmentX = JPanel.CENTER_ALIGNMENT
        panel.background = Color(0, 255, 100, 255)
        panel.isOpaque = false
        add(panel)

        val titledBorder = TitledBorder(LineBorder(TITLE_COLOR), "Игроки")
        titledBorder.titleColor = TITLE_COLOR
        panel.border = titledBorder

        // Список для выбора игроков черными фигурами.
        val bList = getPlayersList(panel, bPlayerNumber, "Черные", BLACK_COLOR)
        bList.addListSelectionListener {
            board.blackPlayer = getSelectedPlayer(it)
        }

        // Список для выбора игроков белыми фигурами.
        val wList = getPlayersList(panel, wPlayerNumber, "Белые", WHITE_COLOR)
        wList.addListSelectionListener {
            board.whitePlayer = getSelectedPlayer(it)
        }

        // Кнопка запуска игры.
        //
        val start = JButton("Старт")
        start.alignmentX = JPanel.CENTER_ALIGNMENT
        start.addActionListener {
            game.initBoardDefault()
            board.startGame()
        }

        add(start)
    }

    /**
     * Выдать игрока выбранного в списке игроков.
     *
     * @param e
     * - событие выбора в списке.
     * @return выбраный из списка игрок.
     */
    private fun getSelectedPlayer(e: ListSelectionEvent): IPlayer {
        val source = e.source as JList<*>
        return players[source.selectedIndex]
    }

    /**
     * Выдать управляющий элемент - список игроков.
     *
     * @param playerNumber
     * - номер выделенного в списке игрока.
     * @param titleText
     * - текст заголовка списка.
     * @param titleColor
     *
     * @return управляющий элемент - список игроков.
     */
    private fun getPlayersList(
            panel: JPanel,
            playerNumber: Int,
            titleText: String,
            titleColor: Color
    ): JList<String> {
        val title = JLabel(titleText)
        title.foreground = titleColor
        title.alignmentX = JLabel.CENTER_ALIGNMENT
        title.layout = BoxLayout(title, BoxLayout.Y_AXIS)
        panel.add(title)

        val x = players.stream().map { it.name }.collect(Collectors.toList())
        val list = JList(x.toTypedArray())
        panel.add(list)

        list.alignmentX = JLabel.CENTER_ALIGNMENT
        list.layout = BoxLayout(list, BoxLayout.Y_AXIS)
        list.border = LineBorder(Color.BLACK)
        list.foreground = BORDER_COLOR
        list.background = LIST_COLOR
        list.selectedIndex = playerNumber
        list.selectionMode = ListSelectionModel.SINGLE_SELECTION

        return list
    }

    /**
     * Найти номер игрока в списке игроков.
     *
     * @param player
     * - заданный игрок.
     * @param players
     * - список игроков.
     *
     * @return номер найденного игрока.
     */
    private fun getPlayerIndex(player: IPlayer, players: List<IPlayer>): Int {
        val x = players.stream()
                .filter { p -> p.javaClass === player.javaClass }
                .findFirst()

        return if (x.isPresent) players.indexOf(x.get()) else 0
    }

    companion object {
        private val WHITE_COLOR = Color(255, 255, 255, 255)
        private val BLACK_COLOR = Color(0, 0, 0, 255)

        private val TITLE_COLOR = Color(255, 255, 0, 255)
        private val LIST_COLOR = Color(255, 255, 255, 255)
        private val BORDER_COLOR = Color(0, 0, 0, 255)
    }
}
