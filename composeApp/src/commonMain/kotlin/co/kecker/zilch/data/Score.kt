package co.kecker.zilch.data

data class Score(
    val playerName: String,
    val score: Int = 0,
    val turn: Int = 0,
    val zilches: Int = 0,
)