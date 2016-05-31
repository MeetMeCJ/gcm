package gcm.play.android.samples.com.gcmquickstart;

import android.support.annotation.Nullable;

import com.j256.ormlite.stmt.NullArgHolder;
import com.squareup.okhttp.RequestBody;

import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Admin on 19/05/2016.
 */
//http://192.168.1.34:28914/MeetMe/servlet?op=consulta&accion=tlf&tlf=9851485&token=654654
public interface ApiContact {
    @GET("servlet?op=consulta&action=token")
    Call<Contact> getContactByToken(@Query("token") String token);

    @GET("servlet?op=consulta&action=tlf")
    Call<Contact> getContactByTelephone(@Query("tlf") String telephone);

    @POST("servlet?op=alta&action=registrar")
    Call<Contact> createContact(@Query("tlf") String telephone, @Query("token") String token);

    @FormUrlEncoded
    @POST("servlet?op=alta&action=actualizar")
    Call<Contact> updateContact(@Field("tlf") String telephone,
                                @Field("token") String token,
                                @Field("nick") String nick,
                                @Field("description") String description,
                                @Field("last") String lastconnection,
                                @Field("see") String seeconnection,
                                @Field("facebook") String facebook,
                                @Field("twitter") String twitter,
                                @Field("email") String email,
                                @Field("birth") String birth,
                                @Field("nationality") String nationality,
                                @Field("privacy") String privacy);

}
