package figure

import CELL
import Position
import pt.isel.canvas.Canvas

const val PUSH_IMG = 4
const val STILL_IMG = 1
const val LEFT_IMG = 3
const val RIGHT_IMG = 1
const val UP_IMG = 0
const val DOWN_IMG = 2

data class Man(val dim: Dimension, val pos: Position, val dir: Direction)

/**
 * Drawing the man depending on the direction and if it is pushing the box or not
 */
fun Man.draw(canvas: Canvas, boxes: List<Position>) {
    val direction = when (dir) {
        Direction.LEFT -> LEFT_IMG
        Direction.RIGHT -> RIGHT_IMG
        Direction.DOWN -> DOWN_IMG
        else -> UP_IMG
    }
    val pushing = isPushing(boxes)
    canvas.drawImage(
        "soko|${pushing * CELL.width}," + "${direction * CELL.height + 1}," + "${CELL.width}," + "${CELL.height}",
        (pos.col + dim.width / 2) * CELL.width,
        (pos.line + dim.height / 2) * CELL.height,
        CELL.width,
        CELL.height
    )
}

/**
 * Verifying if the man is pushing a box
 */
fun Man.isPushing(boxes: List<Position>): Int {
    val newPos = pos.newPos(dir)
    return if (boxes.contains(newPos)) PUSH_IMG else STILL_IMG
}

/**
 * Getting the new position if the man moves through the columns or the lines
 */
fun Position.newPos(dir: Direction?): Position {
    return when (dir) {
        Direction.LEFT -> Position(col - 1, line)
        Direction.RIGHT -> Position(col + 1, line)
        Direction.UP -> Position(col, line - 1)
        Direction.DOWN -> Position(col, line + 1)
        else -> this
    }
}

/**
 * Getting the correct direction when man moves
 * Verifying if there's a wall or a box so the man doesn't phase through
 */
fun Man.move(key: Int, walls: List<Position>, boxes: List<Position>): Man {
    val direction = key.toDir()
    val newPos = pos.newPos(direction)
    val newDir = when (direction) {
        Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP -> direction
        else -> dir
    }
    return when {
        boxes.contains(newPos) && (walls.contains(newPos.newPos(newDir)) || boxes.contains(newPos.newPos(newDir))) || walls.contains(
            newPos
        ) -> copy(dir = newDir)

        else -> copy(pos = newPos, dir = newDir)
    }
}