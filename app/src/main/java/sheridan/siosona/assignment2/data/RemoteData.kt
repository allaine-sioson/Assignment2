package sheridan.siosona.assignment2.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteData (
    @SerialName("dancers")
    val profiles: List<RemoteProfile>
)