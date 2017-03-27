package ch.hsr.afterhour.service.server;

import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.annotation.types.Field;
import ch.viascom.groundwork.foxhttp.annotation.types.FormUrlEncodedBody;
import ch.viascom.groundwork.foxhttp.annotation.types.POST;
import ch.viascom.groundwork.foxhttp.annotation.types.Path;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.response.serviceresult.ServiceResult;

@Path("{host}")
public interface FoxHttpRequests {

    @POST("/login")
    @FormUrlEncodedBody
    @ServiceResult
    User authenticateUser(@Field("qrcode") String qrcode) throws FoxHttpException;
}
