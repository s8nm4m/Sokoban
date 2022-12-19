package figure

import Position

data class Move(val boxes: List<Position>, val pos: Position, val dir: Direction, val boxMoved: Boolean = false)