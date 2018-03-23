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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.eurekaclinical.common.resource.AbstractNamedReadWriteResource;
import org.eurekaclinical.registry.client.comm.Component;
import org.eurekaclinical.registry.service.dao.ComponentDao;
import org.eurekaclinical.registry.service.dao.ComponentTypeDao;
import org.eurekaclinical.registry.service.entity.ComponentEntity;
import org.eurekaclinical.registry.service.entity.ComponentTypeEntity;

/**
 * Resource for managing registered Eureka! Clinical components.
 * 
 * Registered components are accessible read-only by all authenticated users.
 * They are read-write only for users with the <code>admin</code> role.
 *
 * @author arpost
 */
@Path("/protected/components")
@Transactional
public class ComponentResource extends AbstractNamedReadWriteResource<ComponentEntity, Component> {

    private final ComponentTypeDao<ComponentTypeEntity> componentTypeDao;
    private final ComponentDao<ComponentEntity> componentDao;

    @Inject
    public ComponentResource(ComponentDao<ComponentEntity> inComponentDao, ComponentTypeDao<ComponentTypeEntity> inComponentTypeDao) {
        super(inComponentDao, false);
        this.componentDao = inComponentDao;
        this.componentTypeDao = inComponentTypeDao;
    }

    /**
     * Gets all registered components. Allows specifying an optional 
     * <code>type</code> query parameter with one or more component type names 
     * in order to return only components with the given type(s). Unlike the 
     * implementation that this method overrides, this method may be called by 
     * any user regardless of role.
     *
     * @param req the HTTP servlet request.
     * @return a list of registered components.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Component> getAll(@Context HttpServletRequest req) {
        String[] types = req.getParameterValues("type");
        List<Component> result = new ArrayList<>();
        if (types != null && types.length > 0) {
            for (ComponentEntity ent : this.componentDao.getByType(types)) {
                result.add(toComm(ent, req));
            }
        } else {
            for (ComponentEntity ent : this.componentDao.getAll()) {
                result.add(toComm(ent, req));
            }
        }
        return result;
    }

    /**
     * Copies the field values of a component POJO into a new component entity
     * object and returns the entity.
     * 
     * @param commObj the component POJO.
     * 
     * @return the component entity.
     */
    @Override
    protected ComponentEntity toEntity(Component commObj) {
        ComponentEntity entity = new ComponentEntity();
        entity.setId(commObj.getId());
        entity.setName(commObj.getName());
        entity.setDisplayName(commObj.getDisplayName());
        entity.setDescription(commObj.getDescription());
        entity.setUrl(commObj.getUrl());
        entity.setType(this.componentTypeDao.retrieve(commObj.getId()));
        entity.setSmallIcon(commObj.getSmallIcon());
        entity.setMediumIcon(commObj.getMediumIcon());
        entity.setLargeIcon(commObj.getLargeIcon());
        return entity;
    }

    /**
     * Returns whether the user is authorized to access a component POJO.
     * 
     * @param commObj the component POJO.
     * @param req the HTTP servlet request object.
     * 
     * @return always <code>true</code>.
     */
    @Override
    protected boolean isAuthorizedComm(Component commObj, HttpServletRequest req) {
        return true;
    }

    /**
     * Copies the field values of a component entity into a new component POJO
     * and returns the POJO.
     * 
     * @param entity the component entity.
     * @param req the HTTP servlet request object.
     * 
     * @return the component POJO.
     */
    @Override
    protected Component toComm(ComponentEntity entity, HttpServletRequest req) {
        Component comm = new Component();
        comm.setId(entity.getId());
        comm.setName(entity.getName());
        comm.setDisplayName(entity.getDisplayName());
        comm.setDescription(entity.getDescription());
        comm.setUrl(entity.getUrl());
        comm.setType(entity.getType().getId());
        comm.setSmallIcon(entity.getSmallIcon()); 
        comm.setMediumIcon(entity.getMediumIcon()); 
        comm.setLargeIcon(entity.getLargeIcon()); 
        return comm;
    }

    /**
     * Returns whether the user is authorized to access a component entity.
     * 
     * @param entity the component entity.
     * @param req the HTTP servlet request object.
     * 
     * @return always <code>true</code>.
     */
    @Override
    protected boolean isAuthorizedEntity(ComponentEntity entity, HttpServletRequest req) {
        return true;
    }

}
