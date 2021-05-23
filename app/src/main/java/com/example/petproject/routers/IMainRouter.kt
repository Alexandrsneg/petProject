package com.example.petproject.routers

interface IMainRouter : IBaseRouter {
    companion object{
        var FEED = "feed"
        var PROPOLSALS = "propolsals"
        var ORGANIZATIONS = "organizations"
        var SETTINGS = "SETTINGS"
    }
    fun goToFeed()

}