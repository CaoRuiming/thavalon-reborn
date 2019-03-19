package roles

import thavalon.Game
import thavalon.Updater
import thavalon.UpdaterPriority

/**
 * Old percival implementation. Doesn't care if they don't see anyone
 */
open class OldPercival : Role() {
    override val role: RoleType = RoleType.Percival

    val seen : Set<RoleType> = setOf(RoleType.Morgana, RoleType.Merlin)

    override fun getUpdaters(g: Game): List<Updater> {
        return listOf(makePercivalUpdater())
    }

    fun seenByPercival(r : Role) : Boolean {
        return r.role in seen
    }

    fun makePercivalUpdater() : Updater {
        return Pair(updater@{g : Game ->
            this.information.addAll(g.rolesInGame.filter { seenByPercival(it)}
                .map { ThavalonInformation.SingleSeenInformation(it)})
            return@updater
        }, UpdaterPriority.Nine)
    }

    override fun prepareInformation(): MutableMap<String, List<String>> {
        val m = super.prepareInformation()

        m["seen"] = m["seen"]!!.map { "You see $it as Merlin (Or Morgana)" }
        return m
    }
}

/**
 * New Percival implementation, must see at least one role
 */
class NewPercival : OldPercival() {
    override fun gameOk(g: Game): Boolean {
        return g.rolesInGame.any {seenByPercival(it)}
    }
}