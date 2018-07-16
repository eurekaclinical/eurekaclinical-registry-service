package org.eurekaclinical.registry.service.dao;

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

import java.util.List;
import org.eurekaclinical.registry.service.entity.UserEntity;
import org.eurekaclinical.standardapis.dao.Dao;

/**
 *
 * @author Dileep Gunda
 */
public interface UserDao <U extends UserEntity> extends Dao<U, Long> {

    U getUsersByName(String name);
    
    List<U> getUsersForGroup(String username);
}
