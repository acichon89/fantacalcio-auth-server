package com.javangarda.fantacalcio.authserver;

import com.javangarda.fantacalcio.authserver.application.internal.AccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.AccountService;
import com.javangarda.fantacalcio.authserver.application.internal.saga.UserClient;
import com.javangarda.fantacalcio.authserver.application.internal.impl.SimpleAccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.impl.TransactionalAccountService;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountRepository;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.internal.HttpUserClient;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.messaging.Events;
import com.javangarda.fantacalcio.commons.validation.RepositoryFieldUniqueValidator;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(Events.class)
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.javangarda.fantacalcio.authserver")
public class FantacalcioAuthServerApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FantacalcioAuthServerApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Primary
	@Bean
	public RepositoryFieldUniqueValidator repositoryFieldUniqueValidator(JdbcTemplate jdbcTemplate){
		return new RepositoryFieldUniqueValidator(jdbcTemplate);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public MessageChannel changePasswordCommandChannel(){
		return new PublishSubscribeChannel(getAsyncExecutor());
	}

	@Bean
	public MessageChannel createAccountCommandChannel(){
		return new PublishSubscribeChannel(getAsyncExecutor());
	}

	@Bean
	public MessageChannel resetPasswordCommandChannel() {
		return new PublishSubscribeChannel(getAsyncExecutor());
	}
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(30);
		executor.setMaxPoolSize(30);
		executor.setQueueCapacity(30);
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Bean
	public AccountFactory accountFactory(PasswordEncoder passwordEncoder) {
		return new SimpleAccountFactory(passwordEncoder);
	}

	@Bean
	public AccountService accountService(AccountRepository accountRepository, AccountFactory accountFactory, PasswordEncoder passwordEncoder) {
		return new TransactionalAccountService(accountRepository, accountFactory, passwordEncoder);
	}

	@Bean
	public UserClient userClient(RestTemplate restTemplate){
		return new HttpUserClient(restTemplate);
	}
}