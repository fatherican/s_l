package cn.njcit.common.util.network;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * HttpClient4.0的封装类
 */
public class HttpClient4Util {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient4Util.class);

    private static HttpParams httpParams;

    /**
     * 最大连接数
     */
    private final static int MAX_TOTAL_CONNECTIONS = 800;

    /**
     * 获取连接的最大等待时间
     */
    private final static int WAIT_TIMEOUT = 10000;

    /**
     * 每个路由最大连接数
     */
    private final static int MAX_ROUTE_CONNECTIONS = 400;

    /**
     * 连接超时时间
     */
    private final static int CONNECT_TIMEOUT = 10000;

    /**
     * 读取超时时间
     */
    private final static int READ_TIMEOUT = 30000;

    /**
     * UTF-8编码
     */
    private final static String ENCODING_UTF_8 = "UTF-8";
    static {
        httpParams = new BasicHttpParams();
        // 设置最大连接数
        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
        // 设置获取连接的最大等待时间
        ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
        // 设置每个路由最大连接数
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
        // 设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
        // 设置读取超时时间
        HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    }
    public static HttpClient getHttpClient() {
        return new DefaultHttpClient(httpParams);
    }
	/**
	 * HttpClient直接连接接口，直接返回数据
	 * 
	 * @param 接口URL
	 * @param NameValuePair参数
	 * @param 编码
	 * @return
	 * @throws Exception
	 */
	public static String invokePostHttp(String url,
			Map<String, String> requestMap, String coding) throws Exception {

		String returnMsg = "";
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 先迭代HashMap
			Iterator<String> it = requestMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				nvps.add(new BasicNameValuePair(key, requestMap.get(key)));
			}

			httpClient.getParams().setIntParameter("http.socket.timeout", 5000);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=" + coding);
			httpPost.addHeader("Accept-Language", "zh-cn");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				returnMsg = EntityUtils.toString(httpEntity);
				EntityUtils.consume(httpEntity);
			}
			httpPost.abort();
		} catch (Exception e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return returnMsg;
	}
    /**
     * HttpClient运用连接池连接接口，直接返回数据
     *
     * @param url 接口URL
     * @param requestMap NameValuePair参数
     * @param coding 编码
     * @return
     * @throws Exception
     */
    public static String Post(String url, HashMap<String, String> requestMap, String coding) throws Exception {
        Long startTimeMills = System.currentTimeMillis();
        String returnMsg = "";
        HttpClient httpClient = getHttpClient();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entrys = requestMap.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader("Accept-Language", "zh-cn");

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                returnMsg = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }

            httpPost.abort();
        } finally {
            httpClient.getConnectionManager().shutdown();

            Long endTimeMills = System.currentTimeMillis();
            Long dotimes = endTimeMills-startTimeMills;
            if (dotimes > 1000) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(dotimes)
                        .append(" Mills! httpclient call times more than 1000Millis, url = ")
                        .append(url)
                        .append(", param = ")
                        .append(requestMap.toString())
                ;
                logger.warn(buffer.toString());
            }
        }
        return returnMsg;
    }

	 public static String get(String url) throws Exception {
		 
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpGet request = new HttpGet(url);
		 HttpResponse httpResponse=httpClient.execute(request);
		 String res=EntityUtils.toString(httpResponse.getEntity());
		 httpClient.getConnectionManager().shutdown();
		 return res;

		  }

    public static String Get(String url, HashMap<String, String> requestMap, String coding) throws Exception {
        Long startTimeMills = System.currentTimeMillis();
        String returnMsg = "";
        HttpClient httpClient = getHttpClient();
        try{
            Set<Map.Entry<String, String>> entrys = requestMap.entrySet();
            String params = "";
            boolean first = true;
            for (Map.Entry<String, String> entry : entrys) {
                String key = entry.getKey();
                String value = entry.getValue();
                if(value == null) {
                    value="";
                }
                if(first) {
                    params = params + key + "=" + value ;
                    first = false;
                } else {
                    params = params + "&" + key + "=" + value ;
                }
            }
            url = url + "?" + params;
            System.out.println("URL : " + url);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                returnMsg = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            httpGet.abort();
        } finally {
            httpClient.getConnectionManager().shutdown();
            Long endTimeMills = System.currentTimeMillis();
            Long dotimes = endTimeMills-startTimeMills;
            if (dotimes > 1000) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(dotimes)
                        .append(" Mills! httpclient call times more than 1000Millis, url = ")
                        .append(url)
                        .append(", param = ")
                        .append(requestMap.toString())
                ;
                logger.warn(buffer.toString());
            }
        }
        return returnMsg;
    }


}