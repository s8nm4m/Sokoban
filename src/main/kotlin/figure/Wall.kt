package figure

import Position
import pt.isel.canvas.*

const val WALLSTARTW = 41
const val WALLSTARTH = 218

data class Wall(val dim: Dimension, val pos: Position)

/**
 * Drawing the wall
 */
fun Wall.draw(canvas: Canvas) {
    canvas.drawImage(
        "soko|$WALLSTARTW,$WALLSTARTH,${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height + 1,
        dim.width,
        dim.height
    )
}