package figure

import CELL
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
        "soko|$WALL_START_W,$WALL_START_H,${CELL.width},${CELL.height}",
        (pos.col + dim.width / 2) * CELL.width,
        (pos.line + dim.height / 2) * CELL.height + 1,
        CELL.width,
        CELL.height
    )
}