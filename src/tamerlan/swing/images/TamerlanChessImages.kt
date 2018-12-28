package tamerlan.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения для
 * [Шахмат Тамерлана](https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object TamerlanChessImages {
    private val imageTamerlanChess = ImageIO.read(javaClass.getResource("TamerlanChess.gif"))
    val icoTamerlanChess = ImageIcon(imageTamerlanChess.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    val imageKingBlack: Image = ImageIO.read(javaClass.getResource("bKingZurich.gif"))
    val imageQueenBlack: Image = ImageIO.read(javaClass.getResource("bQueenZurich.gif"))
    val imageBishopBlack: Image = ImageIO.read(javaClass.getResource("bBishopZurich.gif"))
    val imageKnightBlack: Image = ImageIO.read(javaClass.getResource("bKnightZurich.gif"))
    val imageRookBlack: Image = ImageIO.read(javaClass.getResource("bRookZurich.gif"))
    val imagePawnBlack: Image = ImageIO.read(javaClass.getResource("bPawnZurich.gif"))
    val imageVizirBlack: Image = ImageIO.read(javaClass.getResource("bVizirZurich.gif"))
    val imageWarMachineBlack: Image = ImageIO.read(javaClass.getResource("bWarMachineZurich.gif"))
    val imageGiraffeBlack: Image = ImageIO.read(javaClass.getResource("bGiraffeZurich.gif"))

    val imageKingWhite: Image = ImageIO.read(javaClass.getResource("wKingZurich.gif"))
    val imageQueenWhite: Image = ImageIO.read(javaClass.getResource("wQueenZurich.gif"))
    val imageBishopWhite: Image = ImageIO.read(javaClass.getResource("wBishopZurich.gif"))
    val imageKnightWhite: Image = ImageIO.read(javaClass.getResource("wKnightZurich.gif"))
    val imageRookWhite: Image = ImageIO.read(javaClass.getResource("wRookZurich.gif"))
    val imagePawnWhite: Image = ImageIO.read(javaClass.getResource("wPawnZurich.gif"))
    val imageVizirWhite: Image = ImageIO.read(javaClass.getResource("wVizirZurich.gif"))
    val imageWarMachineWhite: Image = ImageIO.read(javaClass.getResource("wWarMachineZurich.gif"))
    val imageGiraffeWhite: Image = ImageIO.read(javaClass.getResource("wGiraffeZurich.gif"))
}
