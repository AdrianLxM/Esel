package esel.esel.esel.util;


import com.google.gson.annotations.SerializedName;
import java.util.List;

import esel.esel.esel.datareader.SGV;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Header;
import retrofit2.Call;

public interface CareService {
    //public static final Companion Companion = Companion.$$INSTANCE;

    @GET("api/care/GetCurrentValues")
    Call<List<CurrentValuesDto>> getCurrentValues(@Header("Authorization") String token,@Query("FollowerUserID") int paramInt);

    @GET("api/care/GetFollowingUserSensorGlucose")
    Call<List<UserEventDto>> getFollowingUserSensorGlucose(@Header("Authorization") String token,@Query("UserID") int paramInt, @Query("startDate") String paramString1, @Query("endDate") String paramString2);


    @GET("api/care/GetUserProfile")
    Call<List<UserProfileDto>> getUserProfile(@Header("Authorization") String token);

    public static final class Companion {
        static final Companion $$INSTANCE = new Companion();

        private static final String CARE_BASE_PATH = "api/care/";
    }


    public final class CurrentValuesDto {
        @SerializedName("CurrentGlucose")
        public final int currentGlucose;

        @SerializedName("GlucoseTrend")
        public final int glucoseTrend;

        @SerializedName("TimeStamp")
        public final String timeStamp;

        public CurrentValuesDto(int paramInt1, String paramString, int paramInt2) {
            this.currentGlucose = paramInt1;
            this.timeStamp = paramString;
            this.glucoseTrend = paramInt2;
        }
    }

    public final class UserEventDto {
        @SerializedName("EventDate")
        public final String eventDate;

        @SerializedName("EventSubTypeID")
        private final int eventSubTypeId;

        @SerializedName("EventTypeID")
        private final int eventTypeId;

        @SerializedName("Value")
        public final int value;

        public UserEventDto(int paramInt1, int paramInt2, int paramInt3, String paramString) {
            this.eventTypeId = paramInt1;
            this.eventSubTypeId = paramInt2;
            this.value = paramInt3;
            this.eventDate = paramString;
        }
    }

    public final class MemberDto {
        @SerializedName("CurrentGlucose")
        private final int currentGlucose;

        @SerializedName("FirstName")
        private final String firstName;

        @SerializedName("GlucoseTrend")
        private final int glucoseTrend;

        @SerializedName("LastName")
        private final String lastName;

        @SerializedName("ProfileImage")
        private final String profileImage;

        @SerializedName("Status")
        private final int status;

        @SerializedName("CGTime")
        private final String timestamp;

        @SerializedName("UserName")
        private final String userEmail;

        @SerializedName("UserID")
        private final int userId;

        public MemberDto(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4, int paramInt4, String paramString5) {
            this.userId = paramInt1;
            this.userEmail = paramString1;
            this.firstName = paramString2;
            this.lastName = paramString3;
            this.currentGlucose = paramInt2;
            this.glucoseTrend = paramInt3;
            this.profileImage = paramString4;
            this.status = paramInt4;
            this.timestamp = paramString5;
        }
    }

    public final class UserProfileDto {
        @SerializedName("FirstName")
        private final String firstName;

        @SerializedName("LastName")
        private final String lastName;

        @SerializedName("ProfileImage")
        private final String profileImageBase64;

        @SerializedName("UserID")
        public final int userId;

        @SerializedName("UserName")
        private final String username;

        public UserProfileDto(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4) {
            this.userId = paramInt;
            this.username = paramString1;
            this.firstName = paramString2;
            this.lastName = paramString3;
            this.profileImageBase64 = paramString4;
        }
    }


}
