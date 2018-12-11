package chess.images

import java.awt.Image
import javax.imageio.ImageIO


/**
 * Класс для доступа к уникальным изображениям шахматных фигур.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
object ChessImages {
    var imageKingBlack: Image = ImageIO.read(javaClass.getResource("bKingZurich.gif"))
    val imageQueenBlack: Image = ImageIO.read(javaClass.getResource("bQueenZurich.gif"))
    val imageBishopBlack: Image = ImageIO.read(javaClass.getResource("bBishopZurich.gif"))
    val imageKnightBlack: Image = ImageIO.read(javaClass.getResource("bKnightZurich.gif"))
    val imageRookBlack: Image = ImageIO.read(javaClass.getResource("bRookZurich.gif"))
    val imagePawnBlack: Image = ImageIO.read(javaClass.getResource("bPawnZurich.gif"))

    val imageKingWhite: Image = ImageIO.read(javaClass.getResource("wKingZurich.gif"))
    val imageQueenWhite: Image = ImageIO.read(javaClass.getResource("wQueenZurich.gif"))
    val imageBishopWhite: Image = ImageIO.read(javaClass.getResource("wBishopZurich.gif"))
    val imageKnightWhite: Image = ImageIO.read(javaClass.getResource("wKnightZurich.gif"))
    val imageRookWhite: Image = ImageIO.read(javaClass.getResource("wRookZurich.gif"))
    val imagePawnWhite: Image = ImageIO.read(javaClass.getResource("wPawnZurich.gif"))

    val icoChess = imageKnightBlack
}
