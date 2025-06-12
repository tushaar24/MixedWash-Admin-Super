package mixedwash.superapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform