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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Path;
import org.eurekaclinical.common.resource.AbstractNamedReadOnlyResource;
import org.eurekaclinical.registry.client.comm.ComponentType;
import org.eurekaclinical.registry.service.dao.ComponentTypeDao;
import org.eurekaclinical.registry.service.entity.ComponentTypeEntity;

/**
 * Created by akalsan on 10/5/16.
 */
@Path("/protected/componenttypes")
@Transactional
public class ComponentTypeResource extends AbstractNamedReadOnlyResource<ComponentTypeEntity, ComponentType> {

    @Inject
    public ComponentTypeResource(ComponentTypeDao<ComponentTypeEntity> inComponentTypeDao) {
        super(inComponentTypeDao);
    }

    @Override
    protected ComponentType toComm(ComponentTypeEntity inEntity, HttpServletRequest req) {
        ComponentType comm = new ComponentType();
        comm.setId(inEntity.getId());
        comm.setName(inEntity.getName());
        comm.setDescription(inEntity.getDescription());
        return comm;
    }

    @Override
    protected boolean isAuthorizedEntity(ComponentTypeEntity entity, HttpServletRequest req) {
        return true;
    }

}
