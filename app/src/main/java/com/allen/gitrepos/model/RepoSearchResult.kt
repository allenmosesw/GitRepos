package com.allen.gitrepos.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.allen.gitrepos.db.GitTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(GitTypeConverters::class)
data class RepoSearchResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)