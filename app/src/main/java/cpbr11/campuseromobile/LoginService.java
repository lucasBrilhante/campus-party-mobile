package cpbr11.campuseromobile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by igor on 02/02/18.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("/o/token")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("redirect_uri") String redirect_uri,
            @Field("grant_type") String grantType);
}


