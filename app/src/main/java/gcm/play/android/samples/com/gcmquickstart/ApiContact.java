package gcm.play.android.samples.com.gcmquickstart;

import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import retrofit.Call;
import retrofit.http.GET;
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




}
