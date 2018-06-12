package org.eurekaclinical.registry.service.filter;

/*-
 * #%L
 * Eureka! Clinical Registry Service
 * %%
 * Copyright (C) 2017 - 2018 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.standardapis.dao.UserDao;
import org.eurekaclinical.standardapis.dao.UserTemplateDao;
import org.eurekaclinical.standardapis.entity.RoleEntity;
import org.eurekaclinical.standardapis.entity.UserEntity;
import org.eurekaclinical.standardapis.entity.UserTemplateEntity;
import org.eurekaclinical.standardapis.exception.HttpStatusException;
import org.jasig.cas.client.authentication.AttributePrincipal;

public class AutoAuthorizationFilter<R extends RoleEntity,U extends UserEntity<R>, T extends UserTemplateEntity<R>> implements Filter {

    private final UserTemplateDao<T> userTemplateDao;
    private final UserDao<U> userDao;
    
    
    @Inject
    public AutoAuthorizationFilter(UserTemplateDao<T> inUserTemplateDao,
            UserDao<U> inUserDao) {
        this.userTemplateDao = inUserTemplateDao;
        this.userDao = inUserDao;
    }

    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        Principal principal = servletRequest.getUserPrincipal();
        HttpSession session = servletRequest.getSession(false);
        if (principal != null && session != null) {
            String[] roleNames;
            synchronized (session) {
                roleNames = (String[]) session.getAttribute("roles");
                if (roleNames == null) {
                    String remoteUser = servletRequest.getRemoteUser();
                    if (remoteUser != null) {
                        T autoAuthorizationTemplate = this.userTemplateDao.getAutoAuthorizationTemplate();
                        if (this.userDao.getByName(remoteUser) == null) {
                            if (autoAuthorizationTemplate != null) {
                                U user = toUserEntity(autoAuthorizationTemplate, remoteUser);
                                this.userDao.create(user);
                            }
                        }
                    }
                    chain.doFilter(request, response);

                } else if (roleNames.length != 0) {
                    // User and associated roles found
                } else {
                    // User found with No Associated roles
                }
            }
            // HttpServletRequest wrappedRequest = new RolesRequestWrapper(
            // servletRequest, principal, roleNames);
            // chain.doFilter(wrappedRequest, inResponse);
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    protected  U toUserEntity(T userTemplate, String username) {
        return null;
        
    }


  
}
