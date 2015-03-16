package co.hodler;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ProxyTest {

  @Test
  public void withApacheHttpClient() throws Exception {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      HttpHost target = new HttpHost("www.google.de");
      HttpHost proxy = new HttpHost("localhost", 8888);

      RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
      HttpGet request = new HttpGet();
      request.setConfig(config);

      CloseableHttpResponse response = httpclient.execute(target, request);
      try {
        assertEquals(200, response.getStatusLine().getStatusCode());
        EntityUtils.consume(response.getEntity());
      } finally {
        response.close();
      }
    } finally {
      httpclient.close();
    }
  }

  @Test
  public void withUniRest() throws Exception {
    Unirest.setProxy(new HttpHost("localhost", 8888));

    HttpResponse<String> jsonResponse = Unirest.get("http://www.zeit.de")
        .asString();

    assertEquals(200, jsonResponse.getStatus());
  }

  @Test
  public void withRestAssured() {
    RestAssured.proxy(8888);

    assertEquals(200, RestAssured.get("http://www.ebay.de").statusCode());
  }
}
