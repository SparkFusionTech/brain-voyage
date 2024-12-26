package com.sparkfusion.quiz.brainvoyage.utils.text

import kotlin.streams.toList

fun String.splitToCodePoints(): List<String> {
    return codePoints()
        .toList()
        .map { String(Character.toChars(it)) }
}


