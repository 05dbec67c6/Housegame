data class Item(
    var name: String,
    var description: String,
    val room: Room,
    val pickable: Boolean = false,
    var hidden: Boolean = false,
    var openable: Boolean = false,
    var open: Boolean = false,
    var locked: Boolean = false
)
// CellarItems
val cellarStone = Item("stone", "[It is an ordinary stone, the size of your fist.]", cellar, true)
val cellarKey = Item("rusty key", "[A rusty key. It's rusty.]", cellar, true, hidden = true)
val cellarChair = Item("chair", "[An old chair. It hasn't been used for a long time. However, the cushion still seems comfortable.]",cellar)
var cellarBox = Item("box", "[An old box made of wood that has been rotting for a long time. It is closed.]", cellar, openable = true)
var cellarDoor = Item("metal door", "[An old door made of metal.]", cellar, openable = true, locked = true)
val cellarPaper = Item("paper", "[The paper reads: 'Who is a good boy? Wuff, Warf.']", cellar, pickable = true, hidden = true)
// KitchenItems
var kitchenCupboard = Item("cupboard", "[It is a big cupboard with glass windows. Beside the usual kitchen stuff, you see a silver key.]", kitchen,openable = true)
var kitchenCellarDoor = Item("metal door", "[The other side of the metal door.]", kitchen)
val kitchenKey = Item("silver key", "[The key you found in the kitchen.]", kitchen, true, hidden = true)
val kitchenDoorToHallway = Item("other door", "[A door. This time it is made of wood.]", kitchen, false, openable = true, locked = true)
val kitchenDrawer = Item("drawer", "[An ordinary drawer, found in every kitchen on this planet.]", kitchen, false, openable = true)
var kitchenSilverware = Item("knife", "[A kitchen knife, not very  sharp.]", kitchen, pickable = true, hidden = true)

// hallwayItems
var hallwayDog = Item( "dog", "[It is a big dog that is showing you his teeth collection. Make a wrong move and you die here. Let's hope you have saved the game]", hallway)
var hallwayDoor = Item("entrance door", "[The way to freedom.]", hallway, openable = true)

val sumOfItems: MutableSet<Item> = mutableSetOf(
    cellarKey, cellarStone, cellarChair, cellarBox, cellarDoor, cellarPaper,
    kitchenCupboard, kitchenKey, kitchenDoorToHallway, kitchenDrawer, kitchenSilverware, kitchenCellarDoor,
    hallwayDog, hallwayDoor
)