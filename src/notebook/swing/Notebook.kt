package notebook.swing

import checkers.swing.CheckersGamePanel
import checkers.swing.images.CheckersImages
import chess.swing.ChessBoard
import chess.swing.images.ChessImages
import chinachess.swing.ChinaChessGamePanel
import chinachess.swing.images.ChinaChessImages
import go.swing.GoGamePanel
import go.swing.images.GoImages
import halma.swing.HalmaGamePanel
import halma.swing.images.HalmaImages
import renju.swing.RenjuBoard
import renju.swing.images.RenjuImages
import reversi.swing.ReversiGamePanel
import reversi.swing.images.ReversiImages
import tamerlan.swing.TamerlanChessGamePanel
import tamerlan.swing.images.TamerlanChessImages
import vikings.swing.VikingsGamePanel
import vikings.swing.images.VikingImages
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main(args: Array<String>) {
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNotebook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(700, 500)
    frame.setLocationRelativeTo(null)
    frame.layout = BorderLayout()

    val tabbedPane = JTabbedPane()
    frame.add(tabbedPane, BorderLayout.CENTER)

    val chessPanel = ChessBoard()
    val renjuPanel = RenjuBoard()
    val reversiPanel = ReversiGamePanel()
    val vikingsPanel = VikingsGamePanel()
    val halmaPanel = HalmaGamePanel()
    val goPanel = GoGamePanel()
    val checkersPanel = CheckersGamePanel()
    val chinaChessPanel = ChinaChessGamePanel()
    val tamerlanChessPanel = TamerlanChessGamePanel()

    tabbedPane.addTab("Tamerlan Chess", TamerlanChessImages.icoTamerlanChess, tamerlanChessPanel)
    tabbedPane.addTab("China Chess", ChinaChessImages.icoChinaChess, chinaChessPanel)
    tabbedPane.addTab("Checkers", CheckersImages.icoCheckers, checkersPanel)
    tabbedPane.addTab("Go", GoImages.icoGo, goPanel)
    tabbedPane.addTab("Halma", HalmaImages.icoHalma, halmaPanel)
    tabbedPane.addTab("Vikings", VikingImages.icoVikings, vikingsPanel)
    tabbedPane.addTab("Reversi", ReversiImages.icoReversi, reversiPanel)
    tabbedPane.addTab("Chess", ChessImages.icoChess, chessPanel)
    tabbedPane.addTab("Renju", RenjuImages.icoReversi, renjuPanel)

    frame.isVisible = true
}