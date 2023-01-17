import com.example.elioapp.models.GithubRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface API {

    @GET("user/repos")
    suspend fun getAllRepos(@Header("Authorization") token: String): Response<List<GithubRepository>>
}