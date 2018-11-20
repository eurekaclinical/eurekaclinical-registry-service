package org.eurekaclinical.registry.service.resource;

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

import com.google.inject.persist.Transactional;
import org.eurekaclinical.common.comm.User;
import org.eurekaclinical.common.resource.AbstractUserResource;
import org.eurekaclinical.registry.service.dao.RegistryServiceRoleDao;
import org.eurekaclinical.registry.service.entity.AuthorizedRoleEntity;
import org.eurekaclinical.registry.service.entity.AuthorizedUserEntity;
import org.eurekaclinical.standardapis.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akalsan on 10/5/16.
 */
@Path("/protected/users")
@Transactional
public class UserResource extends AbstractUserResource<User, AuthorizedUserEntity, AuthorizedRoleEntity> {

    private final RegistryServiceRoleDao roleDao;

    @Inject
    public UserResource(UserDao<AuthorizedRoleEntity, AuthorizedUserEntity> inUserDao, RegistryServiceRoleDao inRoleDao) {
        super(inUserDao);
        this.roleDao = inRoleDao;
    }

    @Override
    protected User toComm(AuthorizedUserEntity userEntity, HttpServletRequest req) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        List<Long> roles = new ArrayList<>();
        for (AuthorizedRoleEntity roleEntity : userEntity.getRoles()) {
            roles.add(roleEntity.getId());
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    protected AuthorizedUserEntity toEntity(User user) {
        List<AuthorizedRoleEntity> roleEntities = this.roleDao.getAll();
        AuthorizedUserEntity userEntity = new AuthorizedUserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        List<AuthorizedRoleEntity> userRoleEntities = new ArrayList<>();
        for (Long roleId : user.getRoles()) {
            for (AuthorizedRoleEntity roleEntity : roleEntities) {
                if (roleEntity.getId().equals(roleId)) {
                    userRoleEntities.add(roleEntity);
                }
            }
        }
        userEntity.setRoles(userRoleEntities);
        return userEntity;
    }
}
