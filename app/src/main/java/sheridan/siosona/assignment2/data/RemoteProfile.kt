package sheridan.siosona.assignment2.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProfile(
    val name: String,
    val born: String,
    val yearJoined: Int,
    val position: String,
    val photo: String
)