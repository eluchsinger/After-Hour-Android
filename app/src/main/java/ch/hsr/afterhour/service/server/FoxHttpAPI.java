package ch.hsr.afterhour.service.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.MalformedURLException;

import ch.hsr.afterhour.model.Event;
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
    private final static String SERVER_PATH = "http://152.96.238.242:9000";
    private final static String LOGGER_NAME = "After-Hour App | Logger";

    private FoxHttpClient httpClient;
    private FoxHttpRequests requests;

    public FoxHttpAPI() throws FoxHttpException {

        // Non-encrypted Client / Requests
        FoxHttpClientBuilder builder = new FoxHttpClientBuilder();
        builder.setFoxHttpResponseParser(new GsonParser())
                .setFoxHttpRequestParser(new GsonParser())
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

    public User authenticateUser(String qrcode) throws FoxHttpException, MalformedURLException {
        return makeRequest("{host}/users/" + qrcode, RequestType.GET).getParsedBody(User.class);
    }

    public User login(String email, String password) throws FoxHttpException, MalformedURLException {
        //Todo: Delete Temporary Login and Return requests.login
        //return requests.login(email, password);
        return new User("Schwyter","Fabian","fab.schwyter@gmail.com","0799163565",null);
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
}
