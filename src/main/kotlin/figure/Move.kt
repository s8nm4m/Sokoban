package figure

import Position

data class Move(
    val previousPosition: Position,
    val nextPosition: Position,
    val previousDir: Direction,
    val nextDir: Direction
)
