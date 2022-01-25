package com.naufal.mal.search

//type = tv movie ova special ona music
// use type without delimeter
enum class AnimeType(val type: String) {
    ALL("tvmovieovaspecialonamusic"),
    TV("tv"),
    MOVIE("movie"),
    OVA("ova"),
    SPECIAL("special"),
    ONA("ona"),
    MUSIC("music"),
}
