import figure.*
import pt.isel.canvas.*

data class Game(
    val dim: Dimension,
    val man: Man,
    val walls: List<Position>,
    val boxes: List<Position>,
    val targets: List<Position>
)

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    walls.forEach { Wall(dim, it).draw(canvas) }
    targets.forEach { Target(dim, it).draw(canvas) }
    boxes.forEach { Box(dim, it).draw(canvas, targets) }
    man.draw(canvas, boxes)
}

fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls, boxes)
    val newBoxList = boxes.move(nextMan, walls)
    return copy(man = nextMan, boxes = newBoxList)
}

fun List<Position>.move(nextMan: Man, walls: List<Position>): List<Position> {
    if (contains(nextMan.pos)) {
        val newBox = nextMan.pos.newPos(nextMan.dir)
        val newList = map { i -> if (i == nextMan.pos) newBox else i }
        newList.forEach { if (walls.contains(it)) return this }
        return newList
    }
    return this
}
