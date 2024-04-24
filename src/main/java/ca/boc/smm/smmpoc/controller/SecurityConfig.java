package ca.boc.smm.smmpoc.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Security.TrustStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .anyRequest()
        .authenticated());
    http.oauth2ResourceServer((oauth2) -> oauth2
        .jwt(Customizer.withDefaults()));
    http.headers().frameOptions().sameOrigin();
    return http.build();
  }

  @Bean
  public JwtDecoder nimbus()
  {
    return NimbusJwtDecoder.withJwkSetUri("https://localhost/realms/smm-poc-approach/protocol/openid-connect/certs").build();
  }

  //@Bean
  public SecurityFilterChain open(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .anyRequest()
        .permitAll());
    return http.build();
  }

  // bad things
  @Bean
  public RestTemplate getRestTemplate() throws NoSuchAlgorithmException, KeyManagementException {

    // Create SSL context to trust all certificates
    SSLContext sslContext = SSLContext.getInstance("TLS");

    // Define trust managers to accept all certificates
    TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
      // Method to check client's trust - accepting all certificates
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
      }

      // Method to check server's trust - accepting all certificates
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
      }

      @Override
      public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
          throws CertificateException {

      }

      @Override
      public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
          throws CertificateException {

      }

      // Method to get accepted issuers - returning an empty array
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
      }
    }};

    // Initialize SSL context with the defined trust managers
    sslContext.init(null, trustManagers, null);

    // Disable SSL verification for RestTemplate

    // Set the default SSL socket factory to use the custom SSL context
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

    // Set the default hostname verifier to allow all hostnames
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

    // Create a RestTemplate with a custom request factory

    // Build RestTemplate with SimpleClientHttpRequestFactory
    RestTemplate restTemplate = new RestTemplateBuilder().requestFactory(
            SimpleClientHttpRequestFactory.class)
        .build();

    return restTemplate; // Return the configured RestTemplate
  }

}
