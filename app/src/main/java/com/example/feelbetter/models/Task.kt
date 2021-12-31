package com.example.feelbetter.models

class Task(
    var startTime: String? = "",
    val dueTime: String = "",
    val task: String = "",
    val owner: String? = "",
    val finished: Boolean? = false,
    var documentId: String? = "",
)