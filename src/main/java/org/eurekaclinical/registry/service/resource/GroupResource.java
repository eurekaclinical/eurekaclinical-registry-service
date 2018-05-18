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
import javax.ws.rs.core.Response;
import org.eurekaclinical.common.resource.AbstractGroupResource;
import org.eurekaclinical.registry.service.dao.ComponentDao; 
import org.eurekaclinical.registry.service.entity.GroupEntity;
import org.eurekaclinical.registry.service.entity.ComponentEntity; 
import org.eurekaclinical.registry.client.comm.RegistryGroup;
import org.eurekaclinical.standardapis.dao.GroupDao;
import org.eurekaclinical.standardapis.exception.HttpStatusException;

/**
 *
 * @author Dileep Gunda
 */
@Path("/protected/groups")
@Transactional
public class GroupResource extends AbstractGroupResource<GroupEntity, RegistryGroup > {

    private final ComponentDao<ComponentEntity> componentDao; 

    @Inject
    public GroupResource(GroupDao<GroupEntity> inGroupDao,
    		ComponentDao<ComponentEntity> componentDao) {
        super(inGroupDao);
        this.componentDao = componentDao; 
    }

    @Override
    protected RegistryGroup toComm(GroupEntity groupEntity, HttpServletRequest req) {
    	RegistryGroup group = new RegistryGroup();
        group.setId(groupEntity.getId());
        group.setName(groupEntity.getName());
        List<Long> components = new ArrayList<>();
        for (ComponentEntity component : groupEntity.getComponents()) {
        	components.add(component.getId());
        }
        group.setComponents(components);
        return group;
    }

    @Override
    protected GroupEntity toEntity(RegistryGroup commObj) {
        GroupEntity entity = new GroupEntity();
        entity.setId(commObj.getId());
        entity.setName(commObj.getName());
        for (Long componentsID : commObj.getComponents()) {
            ComponentEntity componentEntity = this.componentDao.retrieve(componentsID);
            if (componentEntity != null) {
                entity.addComponents(componentEntity);
            } else {
                throw new HttpStatusException(Response.Status.BAD_REQUEST);
            }
        }
        return entity;
    }

    @Override
    protected boolean isAuthorizedComm(RegistryGroup commObj, HttpServletRequest req) {
        return true;
    }

    @Override
    protected boolean isAuthorizedEntity(GroupEntity entity, HttpServletRequest req) {
        return true;
    }

}
