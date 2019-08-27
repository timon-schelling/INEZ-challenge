package gui.views

import gui.models.MainModel
import tornadofx.*

/**
 * TornadoFX list view representing [MainModel.elements]
 */
class ListView : View() {
    override val root = listview(MainModel.elements) {
        isEditable = false
        cellFragment(ItemFragment::class)
    }
}
