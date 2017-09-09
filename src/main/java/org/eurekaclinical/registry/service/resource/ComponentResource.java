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
import org.eurekaclinical.common.resource.AbstractNamedReadWriteResource;
import org.eurekaclinical.registry.client.comm.Component;
import org.eurekaclinical.registry.service.dao.ComponentDao;
import org.eurekaclinical.registry.service.dao.ComponentTypeDao;
import org.eurekaclinical.registry.service.entity.ComponentEntity;
import org.eurekaclinical.registry.service.entity.ComponentTypeEntity;

/**
 * Created by akalsan on 10/5/16.
 */


@Path("/protected/components")
@Transactional
public class ComponentResource extends AbstractNamedReadWriteResource<ComponentEntity, Component> {

    private final ComponentTypeDao<ComponentTypeEntity> componentTypeDao;

    @Inject
    public ComponentResource(ComponentDao<ComponentEntity> inComponentDao, ComponentTypeDao<ComponentTypeEntity> inComponentTypeDao) {
        super(inComponentDao, true);
        this.componentTypeDao = inComponentTypeDao;
    }

    @Override
    protected ComponentEntity toEntity(Component commObj) {
        ComponentEntity entity = new ComponentEntity();
        entity.setId(commObj.getId());
        entity.setName(commObj.getName());
        entity.setDescription(commObj.getDescription());
        entity.setUrl(commObj.getUrl());
        entity.setType(this.componentTypeDao.retrieve(commObj.getId()));
        return entity;
    }

    @Override
    protected boolean isAuthorizedComm(Component commObj, HttpServletRequest req) {
        return true;
    }

    @Override
    protected Component toComm(ComponentEntity entity, HttpServletRequest req) {
        Component comm = new Component();
        comm.setId(entity.getId());
        comm.setName(entity.getName());
        comm.setDescription(entity.getDescription());
        comm.setUrl(entity.getUrl());
        comm.setType(entity.getType().getId());
        return comm;
    }

    @Override
    protected boolean isAuthorizedEntity(ComponentEntity entity, HttpServletRequest req) {
        return true;
    }

}
