package com.allen.gitrepos.model

import androidx.room.Entity

@Entity(primaryKeys = ["query"])
data class RepoSearchResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)