package org.eurekaclinical.registry.service.props;

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

import org.eurekaclinical.standardapis.props.CasJerseyEurekaClinicalProperties;

/**
 * @author Andrew Post
 */
public class ServiceProperties extends CasJerseyEurekaClinicalProperties {

    public ServiceProperties() {
        super("/etc/ec-registry");
    }

    @Override
    public String getProxyCallbackServer() {
        String result = getValue("eurekaclinical.registry.service.callbackserver");
        if (result != null) {
            return result;
        } else {
            return getValue("eurekaclinical.registryservice.callbackserver");
        }
    }

    @Override
    public String getUrl() {
        String result = getValue("eurekaclinical.registry.service.url");
        if (result != null) {
            return result;
        } else {
            return getValue("eurekaclinical.registryservice.url");
        }
    }

}
