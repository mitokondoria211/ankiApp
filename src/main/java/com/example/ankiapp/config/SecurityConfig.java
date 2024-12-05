package com.example.ankiapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;
import com.example.ankiapp.constant.UrlConst;

import lombok.RequiredArgsConstructor;

 
/**
 * Spring Security カスタマイズするクラス
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	/**パスワードエンコーダー*/
	private final PasswordEncoder passwordEncoder;
	
	/**ユーザー情報取得Service*/
	private final UserDetailsService userDetailsService;
	
	/**メッセージ取得*/
	private final MessageSource messageSource;
	
	/**ユーザー名のname属性の指定*/
	private final String USERNAME_PARAMETER = "loginId";
	
	/**
     * Spring Securityの各種カスタマイズを行います。
     * 
     * <p>カスタマイズ設定するのは、以下の項目になります。
     * <ul>
     * <li>認証不要URL</li>
     * <li>ログイン画面のURL</li>
     * <li>usernameとして利用するリクエストパラメーター名</li>
     * <li>ログイン成功時のリダイレクト先URL</li>
     * </ul>
     * 
     * @param http セキュリティ設定
     * @return カスタマイズ結果
     * @throws Exception 予期せぬ例外が発生した場合
     */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
	//			//★HTTPリクエストに対するセキュリティ設定
				.authorizeHttpRequests(authz -> authz
				//「/login」へのアクセスは認証を必要としない
				.requestMatchers(UrlConst.NO_AUTHENTICATION).permitAll()
	//			//その他のリクエストは認証が必要
				.anyRequest().authenticated())
	//			
				//★フォームベースのログイン設定
				.formLogin(
						login -> login
						//カスタムログインページへのURLを指定
						.loginPage(UrlConst.LOGIN)
//						.loginProcessingUrl(UrlConst.LOGIN)
						// ユーザー名のname属性を指定
						.usernameParameter(USERNAME_PARAMETER)
						// パスワードのname属性を指定
//						.passwordParameter("password")
						//ログイン成功時のリダイレクト先を指定
						.defaultSuccessUrl(UrlConst.MENU, true))
				.logout(logout -> logout.logoutSuccessUrl(UrlConst.LOGIN))
				.requestCache(cache -> cache
		                .requestCache(new NullRequestCache())  // リクエストキャッシュを無効化
		      );
	//////			 .failureUrl("/login")
	//			
	//			);
			
			return http.build();
	}
	
	/**
     * Providerのカスタマイズを行い、独自Providerを返却します。
     * 
     * <p>カスタマイズ設定するのは、以下のフィールドになります。
     * <ul>
     * <li>UserDetailsService</li>
     * <li>PasswordEncoder</li>
     * <li>MessageSource</li>
     * </ul>
     * 
     * @return カスタマイズしたProvider情報
     */
//	@Bean
	AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		provider.setMessageSource(messageSource);
		
		return provider;
		
	}
}
