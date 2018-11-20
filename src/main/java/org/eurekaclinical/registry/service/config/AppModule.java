package org.eurekaclinical.registry.service.config;

/*-
 * #%L
 * Eureka! Clinical Registry Service
 * %%
 * Copyright (C) 2017 Emory University
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

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.eurekaclinical.common.config.AbstractAppModule;
import org.eurekaclinical.registry.service.dao.ComponentDao;
import org.eurekaclinical.registry.service.dao.ComponentTypeDao;
import org.eurekaclinical.registry.service.dao.JpaComponentDao;
import org.eurekaclinical.registry.service.dao.JpaComponentTypeDao;
import org.eurekaclinical.registry.service.dao.JpaGroupDao;
import org.eurekaclinical.registry.service.dao.JpaRoleDao;
import org.eurekaclinical.registry.service.dao.JpaUserDao;
import org.eurekaclinical.registry.service.dao.JpaUserTemplateDao;
import org.eurekaclinical.registry.service.dao.RegistryServiceRoleDao;
import org.eurekaclinical.registry.service.entity.ComponentEntity;
import org.eurekaclinical.registry.service.entity.ComponentTypeEntity;
import org.eurekaclinical.registry.service.entity.AuthorizedRoleEntity;
import org.eurekaclinical.registry.service.entity.AuthorizedUserEntity;
import org.eurekaclinical.registry.service.entity.UserTemplateEntity;
import org.eurekaclinical.registry.service.entity.GroupEntity;
import org.eurekaclinical.standardapis.dao.GroupDao;
import org.eurekaclinical.standardapis.dao.RoleDao;
import org.eurekaclinical.standardapis.dao.UserDao;
import org.eurekaclinical.standardapis.dao.UserTemplateDao;

/**
 * Created by akalsan on 10/4/16.
 */
public class AppModule extends AbstractAppModule {

    public AppModule() {
        super(JpaUserDao.class, JpaUserTemplateDao.class);
    }
    
    @Override
    protected void configure() {
        super.configure();
        bind(RegistryServiceRoleDao.class).to(JpaRoleDao.class);
        bind(new TypeLiteral<UserTemplateDao<AuthorizedRoleEntity,UserTemplateEntity>>() {}).to(JpaUserTemplateDao.class);
        bind(new TypeLiteral<UserDao<AuthorizedRoleEntity, AuthorizedUserEntity>>() {}).to(JpaUserDao.class);
        bind(new TypeLiteral<RoleDao<AuthorizedRoleEntity>>() {}).to(JpaRoleDao.class);
        bind(new TypeLiteral<ComponentDao<ComponentEntity>>() {}).to(JpaComponentDao.class);
        bind(new TypeLiteral<ComponentTypeDao<ComponentTypeEntity>>() {}).to(JpaComponentTypeDao.class);
        bind(new TypeLiteral<GroupDao<GroupEntity>>() {}).to(JpaGroupDao.class);
    }
}
