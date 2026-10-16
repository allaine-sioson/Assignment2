package sheridan.siosona.assignment2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sheridan.siosona.assignment2.model.Profile
import sheridan.siosona.assignment2.network.ProfileDataApi
import kotlin.String

const val imageFolder: String =
    "https://files.tetervak.com/mobile/pet-data-app/images/"

interface ProfileDataRepository {

    suspend fun getAllProfiles(): List<Profile>
}

class NetworkProfileDataRepository constructor(
    private val profileDataApi: ProfileDataApi
): ProfileDataRepository {

    override suspend fun getAllProfiles(): List<Profile> =
        withContext(Dispatchers.IO) {
            val remoteData: RemoteData = profileDataApi.getRemoteData()
            remoteData.profiles.map { remoteProfile -> remoteProfile.toProfile() }
        }
}

fun RemoteProfile.toProfile() = Profile(
    name = this.name,
    born = this.born,
    yearJoined = this.yearJoined,
    position = this.position,
    photo = imageFolder + this.photo
)