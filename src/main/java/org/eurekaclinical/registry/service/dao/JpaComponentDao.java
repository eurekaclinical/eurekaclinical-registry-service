package org.eurekaclinical.registry.service.dao;

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
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import org.eurekaclinical.registry.service.entity.ComponentEntity;
import org.eurekaclinical.registry.service.entity.ComponentEntity_;
import org.eurekaclinical.registry.service.entity.ComponentTypeEntity_;
import org.eurekaclinical.standardapis.dao.GenericDao;
import org.eurekaclinical.standardapis.dao.QueryPathProvider;

/**
 * Data access object for registry components.
 * 
 * @author Andrew Post
 */
public class JpaComponentDao extends GenericDao<ComponentEntity, Long> 
        implements ComponentDao<ComponentEntity> {

    @Inject
    protected JpaComponentDao(Provider<EntityManager> inManagerProvider) {
        super(ComponentEntity.class, inManagerProvider);
    }

    /**
     * Gets the component with the given name.
     * 
     * @param name the name. Cannot be <code>null</code>.
     * @return the matching component, or <code>null</code> if there is none.
     */
    @Override
    public ComponentEntity getByName(String name) {
        return getUniqueByAttribute(ComponentEntity_.name, name);
    }

    /**
     * Gets all components with any of the given component types.
     * 
     * @param types the component types.
     * @return the matching components. Guaranteed not <code>null</code>.
     */
    @Override
    public List<ComponentEntity> getByType(String... types) {
        return getListByAttributeIn(COMPONENT_TYPE_PATH_PROVIDER, 
                Arrays.asList(types));
    }

    /**
     * Provides a criteria query path from a component to its component type.
     */
    private static final QueryPathProvider<ComponentEntity, String> COMPONENT_TYPE_PATH_PROVIDER = 
            (Root<ComponentEntity> root, CriteriaBuilder builder) -> 
                    root.get(ComponentEntity_.type).get(ComponentTypeEntity_.name);

}
