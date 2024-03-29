package ch.hsr.afterhour.service.server;

import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.annotation.types.Field;
import ch.viascom.groundwork.foxhttp.annotation.types.FormUrlEncodedBody;
import ch.viascom.groundwork.foxhttp.annotation.types.POST;
import ch.viascom.groundwork.foxhttp.annotation.types.Path;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

@Path("{host}")
interface FoxHttpRequests {

    @POST("/users/login")
    @FormUrlEncodedBody
    User login(@Field("email") String email, @Field("password") String password) throws FoxHttpException;
}
