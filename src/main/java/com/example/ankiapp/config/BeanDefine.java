package com.example.ankiapp.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Configuration
public class BeanDefine {

	/**
	 * パスワードエンコーダーのBean定義を行います
	 * 
	 * @return  パスワードエンコーダー(BCrypt形式)
	 */
	@Bean
	PasswordEncoder passwordEndoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
     * マッピングフレームワークのBean定義を行います
     * 
     * @return  マッピングフレームワーク(BCrypt形式)
     */
	@Bean
	Mapper mapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}
}
