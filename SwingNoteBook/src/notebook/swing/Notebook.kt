package notebook.swing

import checkers.Checkers
import chess.Chess
import chinachess.swing.ChinaChessGamePanel
import chinachess.swing.images.ChinaChessImages
import game.core.Game
import game.swing.GamePanel
import go.swing.GoGamePanel
import go.swing.images.GoImages
import halma.swing.HalmaGamePanel
import halma.swing.images.HalmaImages
import renju.swing.RenjuBoard
import renju.swing.images.RenjuImages
import reversi.swing.ReversiGamePanel
import reversi.swing.images.ReversiImages
import tamerlan.TamerlanChess
import vikings.swing.VikingsGamePanel
import vikings.swing.images.VikingImages
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main(args: Array<String>) {
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNotebook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)
    frame.setLocationRelativeTo(null)
    frame.layout = BorderLayout()

    val tabbedPane = JTabbedPane()
    frame.add(tabbedPane, BorderLayout.CENTER)

    tabbedPane.addGame(Chess())
    tabbedPane.addGame(Checkers())
    tabbedPane.addGame(TamerlanChess())

    val renjuPanel = RenjuBoard()
    val reversiPanel = ReversiGamePanel()
    val vikingsPanel = VikingsGamePanel()
    val halmaPanel = HalmaGamePanel()
    val goPanel = GoGamePanel()
    val chinaChessPanel = ChinaChessGamePanel()

    tabbedPane.addTab("China Chess", ChinaChessImages.icoChinaChess, chinaChessPanel)
    tabbedPane.addTab("Go", GoImages.icoGo, goPanel)
    tabbedPane.addTab("Halma", HalmaImages.icoHalma, halmaPanel)
    tabbedPane.addTab("Vikings", VikingImages.icoVikings, vikingsPanel)
    tabbedPane.addTab("Reversi", ReversiImages.icoReversi, reversiPanel)
    tabbedPane.addTab("Renju", RenjuImages.icoReversi, renjuPanel)

    frame.isVisible = true
}

private fun JTabbedPane.addGame(game: Game) {
    val url: URL = game.javaClass.getResource("images/${game.iconImageFile}")
    val iconImage = ImageIO.read(url)
    val icon = ImageIcon(iconImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    addTab(game.name, icon, GamePanel(game))
}