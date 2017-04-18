package ch.hsr.afterhour.service.server;

import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.interceptor.request.FoxHttpRequestBodyInterceptor;
import ch.viascom.groundwork.foxhttp.interceptor.request.context.FoxHttpRequestBodyInterceptorContext;

/**
 * Created by Marcel on 18.04.17.
 */

class MyRequestBodyInterceptor implements FoxHttpRequestBodyInterceptor {
    @Override
    public void onIntercept(FoxHttpRequestBodyInterceptorContext context) throws FoxHttpException {
        System.out.println("Body ist: " + context.getRequestBody().getOutputStream().toString());
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
