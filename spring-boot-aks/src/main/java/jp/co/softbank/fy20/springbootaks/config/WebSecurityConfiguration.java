package jp.co.softbank.fy20.springbootaks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import jp.co.softbank.fy20.springbootaks.service.UserDetailsServiceImpl;

/**
 * Web セキュリティの設定を構成するクラスです。
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * HttpSecurity の設定を上書きします。
   */
  /*
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
  }*/
  
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  //フォームの値と比較するDBから取得したパスワードは暗号化されているのでフォームの値も暗号化するために利用
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
  }

  
  @Override
  public void configure(WebSecurity web) throws Exception {
      web.ignoring().mvcMatchers("/css/**");
      web.ignoring().mvcMatchers("/js/**");
      web.ignoring().mvcMatchers("/images/**");
      web.ignoring().mvcMatchers("/webjars/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.formLogin()
                .permitAll();
      http.authorizeRequests()
              .mvcMatchers("/").permitAll()
              .mvcMatchers("/mypage/deleterequest").hasRole("ADMIN")
              .mvcMatchers("/mypage/**").permitAll()
              .mvcMatchers("/words/deleterequest**").permitAll()
              .mvcMatchers("/words/delete**").hasRole("ADMIN")
              .mvcMatchers("/words/**").permitAll()
              //.mvcMatchers("/datasource").authenticated()
              //.mvcMatchers("/datasource").hasRole("ADMIN")
              //.anyRequest().authenticated();
              .anyRequest().permitAll();
      http.logout()
              .logoutSuccessUrl("/");
  }  

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
  
}
