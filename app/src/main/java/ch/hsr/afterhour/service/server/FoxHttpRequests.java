package ch.hsr.afterhour.service.server;

import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.annotation.types.GET;
import ch.viascom.groundwork.foxhttp.annotation.types.Path;
import ch.viascom.groundwork.foxhttp.annotation.types.Query;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.response.serviceresult.ServiceResult;

@Path("{host}")
public interface FoxHttpRequests {

    @GET("/users")
    @ServiceResult
    User authenticateUser(@Query("userid") String qrcode) throws FoxHttpException;

    @GET("/events")
    Event[] downloadEvents() throws FoxHttpException;
}
