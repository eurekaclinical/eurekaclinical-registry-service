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

import java.util.List;
import org.eurekaclinical.registry.service.entity.ComponentEntity;
import org.eurekaclinical.standardapis.dao.DaoWithUniqueName;

/**
 *
 * @author Andrew Post
 * @param <E> a component entity.
 */
public interface ComponentDao<E extends ComponentEntity> extends DaoWithUniqueName<E, Long> {

    /**
     * Filter components by OR'd types.
     * 
     * @param types one or more component type names. Cannot be 
     * <code>null</code>. If you specify zero component type names, this method
     * will return an empty list.
     * 
     * @return the components with the specified types. Guaranteed not 
     * <code>null</code>.
     */
    List<ComponentEntity> getByType(String... types);
    
}
