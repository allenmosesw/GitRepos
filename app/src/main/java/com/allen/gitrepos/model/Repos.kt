package com.allen.gitrepos.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.squareup.moshi.Json
import java.security.acl.Owner

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 */
@Entity(
    indices = [
        Index("id"),
        Index("owner_login")],
    primaryKeys = ["name", "owner_login"]
)
data class Repo(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "full_name")
    val fullName: String,
    @field:Json(name = "owner")
    val owner: Owner,
    @field:Json(name = "description")
    val description: String
) {
    data class Owner(
        @field:Json(name = "login")
        val login: String,
        @field:Json(name = "id")
        val id: Int,
        @field:Json(name = "node_id")
        val nodeId: String,
        @field:Json(name = "avatar_url")
        val avatarUrl: String
    )
}
