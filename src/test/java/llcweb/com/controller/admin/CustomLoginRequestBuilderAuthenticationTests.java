///*
// * Copyright 2002-2014 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package llcweb.com.controller.admin;
//
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import javax.servlet.Filter;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
////@ContextConfiguration
////@WebAppConfiguration
//public class CustomLoginRequestBuilderAuthenticationTests {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private Filter springSecurityFilterChain;
//
//    private MockMvc mvc;
//
//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .addFilters(springSecurityFilterChain)
//                .build();
//    }
//
//    @Test
//    public void authenticationSuccess() throws Exception {
//        mvc
//            .perform(login())
//            .andExpect(status().isMovedTemporarily())
//            .andExpect(redirectedUrl("/"))
//            .andExpect(authenticated().withUsername("admin"));
//    }
//
//    @Test
//    public void authenticationFailed() throws Exception {
//        mvc
//            .perform(login().user("notfound").password("invalid"))
//            .andExpect(status().isMovedTemporarily())
//            .andExpect(redirectedUrl("/logout?error"))
//            .andExpect(unauthenticated());
//    }
//
//    static FormLoginRequestBuilder login() {
//        return SecurityMockMvcRequestBuilders
//                .formLogin("/login")
//                    .userParameter("user")
//                    .passwordParam("pass");
//    }
//
//    @Configuration
//    @EnableWebMvcSecurity
//    @EnableWebMvc
//    static class Config extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                .authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .usernameParameter("admin")
//                    .passwordParameter("admin")
//                    .loginPage("/login");
//        }
//
//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                .inMemoryAuthentication()
//                    .withUser("admin").password("admin").roles("ADMIN");
//        }
//    }
//}