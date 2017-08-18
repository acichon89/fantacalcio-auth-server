package com.javangarda.fantacalcio.authserver.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.javangarda.fantacalcio.authserver.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.authserver.application.gateway.impl.SimpleQueryFacade;
import com.javangarda.fantacalcio.authserver.application.internal.AccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.AccountService;
import com.javangarda.fantacalcio.authserver.application.internal.UserClient;
import com.javangarda.fantacalcio.authserver.application.internal.impl.SimpleAccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.impl.TransactionalAccountService;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountEventPublisher;
import com.javangarda.fantacalcio.authserver.application.internal.saga.CommandHandler;
import com.javangarda.fantacalcio.authserver.application.internal.saga.impl.EventDrivenCommandHandler;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountStorage;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.http.HttpUserClient;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.http.UserFeignClient;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.messaging.Events;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.messaging.ExternalMessagingAccountEventPublisher;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.persistence.jdbc.JdbcAccountStorage;
import com.javangarda.fantacalcio.commons.validation.RepositoryFieldUniqueValidator;
import feign.RequestInterceptor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

import java.beans.EventHandler;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(Events.class)
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.javangarda.fantacalcio.authserver")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.javangarda.fantacalcio")
@EnableConfigurationProperties
public class FantacalcioAuthServerApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FantacalcioAuthServerApplication.class, args);
	}

	@Bean
	public QueryFacade queryFacade(AccountStorage accountStorage, UserClient userClient){
		return new SimpleQueryFacade(accountStorage, userClient);
	}

	@Bean
	public AccountStorage accountStorage(JdbcTemplate jdbcTemplate) {
		return new JdbcAccountStorage(jdbcTemplate);
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

	@Bean
	public MessageChannel changeEmailCommandChannel() {
		return new PublishSubscribeChannel(getAsyncExecutor());
	}

	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
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
	public AccountService accountService(AccountStorage accountRepository, AccountFactory accountFactory, PasswordEncoder passwordEncoder) {
		return new TransactionalAccountService(accountRepository, accountFactory, passwordEncoder);
	}

	@Bean
	public UserClient userClient(UserFeignClient userFeignClient) {
		return new HttpUserClient(userFeignClient);
	}

	@Bean
	public CommandHandler commandHandler(AccountService accountService, AccountEventPublisher accountEventPublisher) {
		return new EventDrivenCommandHandler(accountService, accountEventPublisher);
	}

	@Bean
	public AccountEventPublisher accountEventPublisher(Events events) {
		return new ExternalMessagingAccountEventPublisher(events);
	}


}