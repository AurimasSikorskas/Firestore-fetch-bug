package app.studio.sikauris.firestore_fetch_bug

data class BookModel(
    val volumes: List<Volume?> = emptyList(),
    val id: String = "",
    val startedAt: Long = 0,
    val completedAt: Long? = null
) {
    data class Question(
        val id: String = "",
        val description: String = "",
        val answers: List<String> = emptyList(),
        val timestamp: Long = 0
    )

    data class Chapter(
        val id: String = "",
        val index: Int = 0,
        val startedAt: Long = 0,
        val passedAt: Long? = null,
        val question: Question? = null
    )

    data class Volume(
        val id: String = "",
        val index: Int = 0,
        var startedAt: Long? = 0,
        val completedAt: Long? = 0,
        val chapters: List<Chapter?> = emptyList()
    )

    companion object {
        fun getInitialData (): BookModel {
            return  BookModel(
                volumes = listOf(
                    BookModel.Volume(
                        chapters = listOf(
                            BookModel.Chapter(
                                id = "volume1.chapter1",
                                question = getQuestion()
                            ),
                            BookModel.Chapter(
                                id = "volume1.chapter2"
                            ),
                            BookModel.Chapter(
                                id = "volume1.chapter3"
                            ),
                            BookModel.Chapter(
                                id = "volume1.chapter4"
                            ),
                            BookModel.Chapter(
                                id = "volume1.chapter5"
                            ),
                            BookModel.Chapter(
                                id = "volume1.chapter6"
                            )
                        )
                    ),
                    BookModel.Volume(
                        chapters = listOf(
                            BookModel.Chapter(
                                id = "volume2.chapter1",
                                question = getQuestion()
                            ),
                            BookModel.Chapter(
                                id = "volume2.chapter2"
                            ),
                            BookModel.Chapter(
                                id = "volume2.chapter3"
                            ),
                            BookModel.Chapter(
                                id = "volume2.chapter4"
                            ),
                            BookModel.Chapter(
                                id = "volume2.chapter5"
                            ),
                            BookModel.Chapter(
                                id = "volume2.chapter6"
                            )
                        )
                    )
                )
            )
        }

        private fun getQuestion (): Question {
            return BookModel.Question(
                id = "id",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. ",
                timestamp = System.currentTimeMillis()
            )
        }

        fun getVolume (startTime: Long): Volume {
            return BookModel.Volume(
                startedAt = startTime,
                chapters = listOf(
                    BookModel.Chapter(
                        id = "volume2.chapter1",
                        question = getQuestion()
                    ),
                    BookModel.Chapter(
                        id = "volume2.chapter2"
                    ),
                    BookModel.Chapter(
                        id = "volume2.chapter3"
                    ),
                    BookModel.Chapter(
                        id = "volume2.chapter4"
                    ),
                    BookModel.Chapter(
                        id = "volume2.chapter5"
                    ),
                    BookModel.Chapter(
                        id = "volume2.chapter6"
                    )
                )
            )
        }
    }
}
