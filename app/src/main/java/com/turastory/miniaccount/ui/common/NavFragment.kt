package com.turastory.miniaccount.ui.common

abstract class NavFragment<Nav : Navigation> : BasicFragment() {
    protected lateinit var navigation: Nav

    fun setup(nav: Nav): NavFragment<Nav> {
        this.navigation = nav
        return this
    }
}