package org.eurekaclinical.registry.service.entity;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Andrew Post
 */
@Entity
@Table(name = "components")
public class ComponentEntity implements org.eurekaclinical.standardapis.entity.Entity<Long> {
    @Id
    @SequenceGenerator(name = "COMP_SEQ_GENERATOR", sequenceName = "COMP_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "COMP_SEQ_GENERATOR")
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String displayName;
    
    private String description;

    private String smallIcon;
    private String mediumIcon;
    private String largeIcon;
    
    @Column(nullable = false)
    private String url;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private ComponentTypeEntity type;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ComponentTypeEntity getType() {
        return type;
    }

    public void setType(ComponentTypeEntity type) {
        this.type = type;
    }
    
    public String getSmallIcon(){
        return smallIcon;
    }
    
    public void setSmallIcon(String icon){
        this.smallIcon = icon;
    }
    
    public String getMediumIcon(){
        return mediumIcon;
    }
    
    public void setMediumIcon(String icon){
        this.mediumIcon = icon;
    }
    
    public String getLargeIcon(){
        return largeIcon;
    }
    
    public void setLargeIcon(String icon){
        this.largeIcon = icon;
    }
    
}
