package ch.hsr.afterhour.service.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.Ticket;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.FoxHttpClient;
import ch.viascom.groundwork.foxhttp.FoxHttpRequest;
import ch.viascom.groundwork.foxhttp.FoxHttpResponse;
import ch.viascom.groundwork.foxhttp.annotation.processor.FoxHttpAnnotationParser;
import ch.viascom.groundwork.foxhttp.body.request.FoxHttpRequestBody;
import ch.viascom.groundwork.foxhttp.body.request.RequestObjectBody;
import ch.viascom.groundwork.foxhttp.builder.FoxHttpClientBuilder;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.interceptor.FoxHttpInterceptorType;
import ch.viascom.groundwork.foxhttp.log.SystemOutFoxHttpLogger;
import ch.viascom.groundwork.foxhttp.parser.GsonParser;
import ch.viascom.groundwork.foxhttp.response.serviceresult.DefaultServiceResultFaultInterceptor;
import ch.viascom.groundwork.foxhttp.type.RequestType;


public class FoxHttpAPI {
    private final static String SERVER_PATH = "http://152.96.233.185:9000";
    private final static String LOGGER_NAME = "After-Hour App | Logger";

    private FoxHttpClient httpClient;
    private FoxHttpRequests requests;

    public FoxHttpAPI() throws FoxHttpException {

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        // Non-encrypted Client / Requests
        FoxHttpClientBuilder builder = new FoxHttpClientBuilder();
        builder.setFoxHttpResponseParser(new CustomGsonParser(gson))
                .setFoxHttpRequestParser(new CustomGsonParser(gson))
                .registerFoxHttpInterceptor(FoxHttpInterceptorType.RESPONSE, new DefaultServiceResultFaultInterceptor())
                .addFoxHttpPlaceholderEntry("host", SERVER_PATH)
                .setFoxHttpLogger(new SystemOutFoxHttpLogger(true, LOGGER_NAME))
                .registerFoxHttpInterceptor(FoxHttpInterceptorType.REQUEST_BODY, new MyRequestBodyInterceptor());

        httpClient = builder.build();
        requests = new FoxHttpAnnotationParser().parseInterface(FoxHttpRequests.class, httpClient);
    }

    public FoxHttpClient getClient() {
        return httpClient;
    }

    public void setClient(FoxHttpClient client) {
        httpClient = client;
    }

    public String getUrl() {
        return SERVER_PATH;
    }

    private FoxHttpResponse makeRequest(final String url, final RequestType requestType) throws MalformedURLException, FoxHttpException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(this.httpClient);
        foxHttpRequest.setUrl(url);
        foxHttpRequest.setRequestType(requestType);
        return foxHttpRequest.execute();
    }

    public User authenticateUser(String qrCode) throws FoxHttpException, MalformedURLException {
        return makeRequest("{host}/users/" + qrCode, RequestType.GET).getParsedBody(User.class);
    }

    public User login(String email, String password) throws FoxHttpException, MalformedURLException {
        return requests.login(email, password);
    }

    public Event[] downloadEvents() throws FoxHttpException , MalformedURLException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        foxHttpRequest.setUrl("{host}/events");
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        return foxHttpResponse.getParsedBody(Event[].class);
    }

    public User registerUser(User user) throws FoxHttpException, MalformedURLException{
        FoxHttpRequestBody requestBody = new RequestObjectBody(user);
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        foxHttpRequest.setUrl(SERVER_PATH + "/users/register");
        foxHttpRequest.setRequestType(RequestType.POST);
        foxHttpRequest.setFollowRedirect(true);
        foxHttpRequest.setRequestBody(requestBody);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        return foxHttpResponse.getParsedBody(User.class);
    }

    public Bitmap getEventImage(int eventId) throws FoxHttpException, MalformedURLException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/events/" + eventId + "/image";
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        final byte[] bytes = foxHttpResponse.getResponseBody().getBody().toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void buyTicket(int userId, int ticketCategoryId) throws MalformedURLException, FoxHttpException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/ticket/" + ticketCategoryId + "/user/" + userId;
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        foxHttpRequest.execute();
    }

    public CoatCheck handOverJacket(String email, int coatHangerNumber, String placeId) throws FoxHttpException, MalformedURLException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/handOverJacket/" + email + "/" + coatHangerNumber + "/" + placeId;
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        return foxHttpResponse.getParsedBody(CoatCheck.class);
    }

    /**
     * Verify, if the user has authorization to enter an event.
     * @param userId The user whose authorization is being verified.
     * @param eventId The event into which the users wants to enter.
     * @return Returns the ticket the user bought or an error.
     */
    public Ticket verifyUserAuthorization(final int userId, final int eventId) throws MalformedURLException, FoxHttpException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/users/" + userId + "/tickets/" + eventId;
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        return foxHttpResponse.getParsedBody(Ticket.class);
    }

    public Bitmap getProfileImage(final int userId) throws MalformedURLException, FoxHttpException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/users/" + userId + "/image";
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        final byte[] bytes = foxHttpResponse.getResponseBody().getBody().toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public List<Event> getUpcomingEvents(final int userId) throws MalformedURLException, FoxHttpException {
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        String urlParameters = "/users/" + userId + "/events";
        foxHttpRequest.setUrl("{host}" + urlParameters);
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        final Event[] events = foxHttpResponse.getParsedBody(Event[].class);
        return new ArrayList<>(Arrays.asList(events));

    }
}
