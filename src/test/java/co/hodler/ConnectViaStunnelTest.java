/**
* ConnectViaStunnelTest.java
* proxy
*
* Created by Axel Hodler on Aug 18, 2015
* Copyright (c) 
* 2015
* M-Way Solutions GmbH. All rights reserved.
* http://www.mwaysolutions.com
* Redistribution and use in source and binary forms, with or without
* modification, are not permitted.
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
* POSSIBILITY OF SUCH DAMAGE.
*/
package co.hodler;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpHost;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

// Preparations: stunnel is running on 8811 and redirects to the actual proxy 8082
public class ConnectViaStunnelTest {

	@Test(expected = UnirestException.class)
	public void connectionsThroughHttpDontWork() throws Exception {
		Unirest.setProxy(new HttpHost("axel.mwaysolutions.com", 8811));

		getHeiseOverStunnel();
	}

	@Test
	public void canConnectThroughHttps() throws Exception {
		Unirest.setProxy(new HttpHost("axel.mwaysolutions.com", 8811, "https"));

		HttpResponse<String> jsonResponse = getHeiseOverStunnel();

		assertEquals(200, jsonResponse.getStatus());
	}

	private HttpResponse<String> getHeiseOverStunnel() throws UnirestException {
		return Unirest.get("http://www.heise.de")
				.header("Proxy-Authorization",
						"Basic ODBEQ0E2ODktQkJCMS00ODlFLTlDOUEtOUREOTFCMDVERDE3OmQwMjA0MzEyYmU2NTA5MGI4MTM1NDk4YTZhZThhZGVkMTUyYmFkMGEzNjBhMGJhNw==")
				.asString();
	}
}
