package main.kotlin.roles

import main.kotlin.thavalon.Game
import main.kotlin.thavalon.Updater
import main.kotlin.thavalon.UpdaterPriority

class Merlin : Role() {

    override val role = RoleType.Merlin
    override fun getUpdaters(g: Game): List<Updater> {
        return listOf(this.getMerlinUpdater())
    }

    /**
     * Helper used to produce the merlin updater, runs getSeen helper and wraps roles into
     * the appropriate thavalon info class
     */
    private fun getMerlinUpdater() : Updater {
        return Pair(updater@{g : Game ->
            information.addAll(getSeen(g)
                .map { ThavalonInformation.SingleSeenInformation(it)})
            return@updater
        }, UpdaterPriority.Ten)
    }

    private fun getSeen(g : Game) : MutableList<Role> {
        // merlin cannot see mordred
        val seen : MutableList<Role> = g.getEvilRoles().filter { it.role != RoleType.Mordred }.toMutableList()
        // try to find lance if we can, if we cannot we just return the evil role list minus mordred
        val lance = g.getGoodRoles().find { it.role == RoleType.Lancelot } ?: return seen
        // we found lance, so add it
        seen.add(lance)
        return seen
    }

    override fun prepareInformation(): MutableMap<String, List<String>> {
        val m = super.prepareInformation()

        m["seen"] = m["seen"]!!.map { "You see $it as Evil (Or Lancelot)" }
        return m
    }
}