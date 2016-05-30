package gcm.play.android.samples.com.gcmquickstart;

import android.support.annotation.Nullable;

import com.j256.ormlite.stmt.NullArgHolder;
import com.squareup.okhttp.RequestBody;

import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import retrofit.Call;
import retrofit.http.Body;
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

    @POST("servlet?op=alta&action=actualizar")
    Call<Contact> updateContact(@Query("tlf") String telephone,
                                @Query("token") String token,
                                @Query("nick") String nick,
                                @Query("description") String description,
                                @Query("lastconnection") String lastconnection,
                                @Query("seeconnection") String seeconnection,
                                @Query("facebook") String facebook,
                                @Query("twitter") String twitter,
                                @Query("email") String email,
                                @Query("birth") String birth,
                                @Query("nationality") String nationality,
                                @Query("privacy") String privacy);


}
