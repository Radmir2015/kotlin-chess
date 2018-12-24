package reversi.ui.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения для игры в
 * [Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object ReversiImages {
    private val imageReversi = ImageIO.read(javaClass.getResource("icoReversi.png"))
    val icoReversi = ImageIcon(imageReversi.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    val icoReversiX = ImageIO.read(javaClass.getResource("icoReversiX.png"))
    val imageHoleBlack = ImageIO.read(javaClass.getResource("bHole.png"))
}