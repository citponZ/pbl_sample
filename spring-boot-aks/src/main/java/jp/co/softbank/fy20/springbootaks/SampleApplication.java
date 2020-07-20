package jp.co.softbank.fy20.springbootaks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot アプリケーションを起動するクラスです。
 */
@SpringBootApplication
public class SampleApplication {

  /**
   * Spring Boot アプリケーションを起動するためのメインメソッドです。
   *
   * @param args メソッドの引数
   */
  public static void main(String[] args) {
    System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
    SpringApplication.run(SampleApplication.class, args);
  }

}
