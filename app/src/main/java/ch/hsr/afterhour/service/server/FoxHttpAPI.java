package ch.hsr.afterhour.service.server;

import java.net.MalformedURLException;

import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.FoxHttpClient;
import ch.viascom.groundwork.foxhttp.FoxHttpRequest;
import ch.viascom.groundwork.foxhttp.FoxHttpResponse;
import ch.viascom.groundwork.foxhttp.annotation.processor.FoxHttpAnnotationParser;
import ch.viascom.groundwork.foxhttp.builder.FoxHttpClientBuilder;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.interceptor.FoxHttpInterceptorType;
import ch.viascom.groundwork.foxhttp.log.SystemOutFoxHttpLogger;
import ch.viascom.groundwork.foxhttp.parser.GsonParser;
import ch.viascom.groundwork.foxhttp.response.serviceresult.DefaultServiceResultFaultInterceptor;
import ch.viascom.groundwork.foxhttp.type.RequestType;


public class FoxHttpAPI {
    // Todo: fill in google play server address
    private final static String SERVER_PATH = "http://sinv-56049.edu.hsr.ch:40000";
    private final static String LOGGER_NAME = "After-Hour App | Logger";

    private FoxHttpClient httpClient;
    private FoxHttpRequests requests;

    public FoxHttpAPI() throws FoxHttpException {

        // Non-encrypted Client / Requests
        FoxHttpClientBuilder builder = new FoxHttpClientBuilder();
        builder.setFoxHttpResponseParser(new GsonParser())
                .registerFoxHttpInterceptor(FoxHttpInterceptorType.RESPONSE, new DefaultServiceResultFaultInterceptor())
                .addFoxHttpPlaceholderEntry("host", SERVER_PATH)
                .setFoxHttpLogger(new SystemOutFoxHttpLogger(true, LOGGER_NAME));

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

    public User authenticateUser(String qrcode) throws FoxHttpException {
//        return requests.authenticateUser(qrcode);
        FoxHttpRequest foxHttpRequest = new FoxHttpRequest(httpClient);
        try {
            foxHttpRequest.setUrl("{host}/users/" + qrcode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        foxHttpRequest.setRequestType(RequestType.GET);
        FoxHttpResponse foxHttpResponse = foxHttpRequest.execute();
        User user = foxHttpResponse.getParsedBody(User.class);
        return user;
    }
}
