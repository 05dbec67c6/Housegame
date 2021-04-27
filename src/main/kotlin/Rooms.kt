data class Room(val name: String, val description: String)

val cellar = Room(
    name = "cellar",
    description = "[You are in a dark cellar.]\n[Water is dripping down the walls and it smells musty.]"
)
val kitchen = Room(name = "kitchen", description = "[A normal looking kitchen. Nothing special.]")
val hallway = Room(
    name = "hallway",
    description = "[A hallway with the entrance door.]\n[However, between you and your freedom is a big aggressive dog.]"
)
val outside = Room(name = "outside", description = "[It's a nice day.]")
