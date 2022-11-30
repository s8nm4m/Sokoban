package figure

import Position
import pt.isel.canvas.Canvas

const val PUSHIMG = 4
const val STILLIMG = 1
const val LEFTIMG = 3
const val RIGHTIMG = 1
const val UPIMG = 0
const val DOWNIMG = 2

data class Man(val dim: Dimension, val pos: Position, val dir: Direction)

/**
 * Drawing the map depending on the direction and if it is pushing the box or not
 */
fun Man.draw(canvas: Canvas, boxes: List<Position>) {
    val standing = when (dir) {
        Direction.LEFT -> LEFTIMG
        Direction.RIGHT -> RIGHTIMG
        Direction.DOWN -> DOWNIMG
        else -> UPIMG
    }
    val pushing = isPushing(boxes)
    canvas.drawImage(
        "soko|${pushing * dim.width},${standing * dim.height + 1},${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height,
        dim.width,
        dim.height
    )

}

/**
 * Verifying if the man is pushing a box
 */
fun Man.isPushing(boxes: List<Position>): Int {
    val newPos = pos.newPos(dir)
    return if (boxes.contains(newPos)) PUSHIMG else STILLIMG
}

/**
 * Getting the new position if the man moves through the columns and the lines
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
    return if (walls.contains(newPos.newPos(newDir)) && boxes.contains(newPos) || walls.contains(newPos))
        copy(dir = newDir)
    else copy(pos = newPos, dir = newDir)
}