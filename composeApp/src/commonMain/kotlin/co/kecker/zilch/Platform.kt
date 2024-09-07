package co.kecker.zilch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform