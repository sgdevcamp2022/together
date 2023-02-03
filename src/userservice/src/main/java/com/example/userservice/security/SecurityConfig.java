package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;
    private RedisTemplate<String,Object> redisTemplate;

    public SecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env, RedisTemplate<String,Object> redisTemplate){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
                .permitAll()
                .and()
                .addFilter(getAuthenticationFilter());


//        h2-console을 사용하기 위한 옵션
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(),userService,env,redisTemplate);
// 로그인에서 사용할 비교하는 쿼리를 구현하지 않고 시큐리티에서 제공하는 로그인을 사용하기 위해 매니저 등록
        return authenticationFilter;
    }

    //    select pwd from users where email=?
    //    db_pwd(encrypted) == input_pwd(encrypted)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
