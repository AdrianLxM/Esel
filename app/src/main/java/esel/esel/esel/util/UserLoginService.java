package esel.esel.esel.util;

import android.os.SystemClock;

import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import java.time.LocalDateTime;
public interface UserLoginService {
    @FormUrlEncoded
    @POST("token")
    Call<SenseonicsTokenDto> authenticate(@Field("grant_type") String paramString1, @Field("client_id") String paramString2, @Field("client_secret") String paramString3, @Field("username") String paramString4, @Field("password") String paramString5);


    public final class SenseonicsTokenDto {

        @SerializedName("access_token")
        private final String accessToken;

        @SerializedName("as:client_id")
        private final String clientId;

        @SerializedName("expires")
        private final String expired;

        @SerializedName("expires_in")
        private final Long expiresIn;

        @SerializedName(".issued")
        private final String issued;

        @SerializedName("refresh_token")
        private String refresh_token;

        @SerializedName("token_type")
        private final String tokenType;

        @SerializedName("userName")
        private final String userName;

        public SenseonicsTokenDto(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
            this.accessToken = paramString1;
            this.tokenType = paramString2;
            this.expiresIn = paramLong;
            this.refresh_token = paramString3;
            this.clientId = paramString4;
            this.userName = paramString5;
            this.issued = paramString6;
            this.expired = paramString7;

            SetExpireDateTime();
        }

        public void SetExpireDateTime(){
            long currentTime = System.currentTimeMillis();

            this.expireDateTime =  currentTime + (expiresIn * 1000);
        }

        public long expireDateTime;

        public String GetBearerToken(){
            return "Bearer "+ accessToken;
        }

    }
}
