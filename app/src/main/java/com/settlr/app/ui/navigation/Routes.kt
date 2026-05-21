package com.settlr.app.ui.navigation

object Routes {
    const val People = "people"
    const val Activity = "activity"
    const val You = "you"
    const val ContactDetail = "contact_detail/{personId}"
    const val QuickAdd = "quick_add"

    fun createContactDetailRoute(personId: String) = "contact_detail/$personId"
}
