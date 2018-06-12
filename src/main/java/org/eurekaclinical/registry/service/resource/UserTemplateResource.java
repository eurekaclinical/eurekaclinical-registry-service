package org.eurekaclinical.registry.service.resource;

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


import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import org.eurekaclinical.standardapis.dao.RoleDao;
import org.eurekaclinical.common.resource.AbstractUserTemplateResource;
import org.eurekaclinical.registry.service.entity.GroupEntity;
import org.eurekaclinical.registry.service.entity.RoleEntity;
import org.eurekaclinical.registry.service.entity.UserTemplateEntity;
import org.eurekaclinical.standardapis.dao.GroupDao;
import org.eurekaclinical.registry.client.comm.RegistryUserTemplate;
import org.eurekaclinical.standardapis.dao.UserTemplateDao;

/**
 *
 * @author Dileep Gunda
 */
@Path("/protected/usertemplates")
@Transactional
public class UserTemplateResource extends AbstractUserTemplateResource<RegistryUserTemplate, RoleEntity, UserTemplateEntity> {

    private final RoleDao<RoleEntity> roleDao;
    private final GroupDao<GroupEntity> groupDao;

    @Inject
    public UserTemplateResource(UserTemplateDao<UserTemplateEntity> inUserDao, RoleDao<RoleEntity> inRoleDao, GroupDao<GroupEntity> inGroupDao) {
        super(inUserDao);
        this.roleDao = inRoleDao;
        this.groupDao = inGroupDao;
    }

    @Override
    protected RegistryUserTemplate toComm(UserTemplateEntity templateEntity, HttpServletRequest req) {
        RegistryUserTemplate template = new RegistryUserTemplate();
        template.setId(templateEntity.getId());
        template.setName(templateEntity.getName());
        List<Long> roles = new ArrayList<>();
        for (RoleEntity roleEntity : templateEntity.getRoles()) {
            roles.add(roleEntity.getId());
        }
        template.setRoles(roles);
        List<Long> groups = new ArrayList<>();
        for (GroupEntity groupEntity : templateEntity.getGroups()) {
            groups.add(groupEntity.getId());
        }
        template.setGroups(groups);
        template.setAutoAuthorize(templateEntity.isAutoAuthorize());
        template.setCriteria(templateEntity.getCriteria());
        return template;
    }

    @Override
    protected UserTemplateEntity toEntity(RegistryUserTemplate template) {
        UserTemplateEntity templateEntity = new UserTemplateEntity();
        templateEntity.setId(template.getId());
        templateEntity.setName(template.getName());
        List<RoleEntity> roleEntities = this.roleDao.getAll();
        for (Long roleId : template.getRoles()) {
            for (RoleEntity roleEntity : roleEntities) {
                if (roleEntity.getId().equals(roleId)) {
                    templateEntity.addRole(roleEntity);
                }
            }
        }
        List<GroupEntity> groupEntities = this.groupDao.getAll();
        for (Long groupId : template.getGroups()) {
            for (GroupEntity groupEntity : groupEntities) {
                if (groupEntity.getId().equals(groupId)) {
                    templateEntity.addGroup(groupEntity);
                }
            }
        }
        templateEntity.setAutoAuthorize(template.isAutoAuthorize());
        templateEntity.setCriteria(template.getCriteria());
        return templateEntity;
    }

}
