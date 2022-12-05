package figure

import Position
import pt.isel.canvas.Canvas

const val WALL_START_W = 41
const val WALL_START_H = 218

data class Wall(val dim: Dimension, val pos: Position)

/**
 * Drawing the wall
 */
fun Wall.draw(canvas: Canvas) {
    canvas.drawImage(
        "soko|$WALL_START_W,$WALL_START_H,${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height + 1,
        dim.width,
        dim.height
    )
}