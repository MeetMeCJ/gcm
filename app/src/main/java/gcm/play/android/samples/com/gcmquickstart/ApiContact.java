package gcm.play.android.samples.com.gcmquickstart;

import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Admin on 19/05/2016.
 */
//http://192.168.1.34:28914/MeetMe/servlet?op=consulta&accion=tlf&tlf=9851485&token=654654
public interface ApiContact {
    @GET("servlet?op=consulta&accion=token&token={token}")
    Call<Contact> getContactByToken(@Path("token") String token);

    @GET("servlet?op=consulta&accion=token&tlf={tlf}")
    Call<Contact> getContactByTelephone(@Path("tlf") String telephone);


}
