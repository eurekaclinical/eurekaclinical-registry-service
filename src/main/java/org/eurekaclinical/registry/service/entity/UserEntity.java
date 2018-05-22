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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by akalsan on 10/4/16.
 */
@Entity
@Table(name = "users")
public class UserEntity implements org.eurekaclinical.standardapis.entity.UserEntity<RoleEntity> {

    /**
     * The user's unique identifier.
     */
    @Id
    @SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR")
    private Long id;

    /**
     * The user's email address.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * A list of roles assigned to the user.
     */
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = {
                @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "role_id")})
    private List<RoleEntity> roles = new ArrayList<>();
    
    /**
     * A list of Groups assigned to the user.
     */
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "user_group",
            joinColumns = {
                @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "group_id")})
    private List<GroupEntity> groups = new ArrayList<>();

    /**
     * Create an empty User object.
     */
    public UserEntity() {
        this.roles = new ArrayList<>();
    }

    /**
     * Get the user's unique identifier.
     *
     * @return A {@link Long} representing the user's unique identifier.
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Set the user's unique identifier.
     *
     * @param inId A {@link Long} representing the user's unique identifier.
     */
    @Override
    public void setId(final Long inId) {
        this.id = inId;
    }

    /**
     * Get the user's email address.
     *
     * @return A String containing the user's email address.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the user's email address.
     *
     * @param inUsername A String containing the user's email address.
     */
    @Override
    public void setUsername(final String inUsername) {
        this.username = inUsername;
    }

    /**
     * Get all the roles assigned to the user.
     *
     * @return A list of roles assigned to the user.
     */
    @Override
    public List<RoleEntity> getRoles() {
        return new ArrayList<>(this.roles);
    }

    /**
     * Set the roles assigned to the current user.
     *
     * @param inRoles A list of roles to be assigned to the user.
     */
    @Override
    public void setRoles(final List<RoleEntity> inRoles) {
        if (inRoles == null) {
            this.roles = new ArrayList<>();
        } else {
            this.roles = new ArrayList<>(inRoles);
        }
    }

    public void addRole(RoleEntity inRole) {
        if(!this.roles.contains(inRole))
        this.roles.add(inRole);
    }

    public void removeRole(RoleEntity inRole) {
        this.roles.remove(inRole);
    }

    
    public void addGroup(GroupEntity inGroup) {
        if(!this.groups.contains(inGroup))
        this.groups.add(inGroup);
    }

    public void removeGroup(GroupEntity inGroup) {
        this.groups.remove(inGroup);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", username=" + username + ", roles=" + roles + '}';
    }
}
