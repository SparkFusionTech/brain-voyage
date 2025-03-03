package com.sparkfusion.quiz.brainvoyage.data.entity.online

data class ServerRequest<T>(
    val action: String,
    val data: T?
) {

    enum class Actions(val content: String) {
        JOIN_OR_CREATE("JOIN_OR_CREATE"),
        ANSWER_ON_QUESTION("ANSWER_ON_QUESTION"),
        NEXT_QUESTION("NEXT_QUESTION"),
        DISCONNECT("DISCONNECT");
    }
}





























