package gui.views

import gui.models.MainModel
import tornadofx.*

class ListView : View() {
    override val root = listview(MainModel.elements) {
        isEditable = false
        cellFragment(ItemFragment::class)
    }
}
