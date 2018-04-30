package org.eurekaclinical.registry.service.entity;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 *
 * @author Dileep
 */
@Entity
@Table(name = "GROUPS")
public class GroupEntity implements org.eurekaclinical.standardapis.entity.GroupEntity {

	@Id
	@SequenceGenerator(name = "GROUP_SEQ_GENERATOR", sequenceName = "GROUP_SEQ",
			allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "GROUP_SEQ_GENERATOR")
	private Long id;

	private String name;
	private Long owner_id;
	private Boolean read;
	private Boolean write;
	private Boolean execute;
	
	

	@ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "user_group",
			joinColumns = {
				@JoinColumn(name = "group_id")},
			inverseJoinColumns = {
				@JoinColumn(name = "user_id")})
	private List<UserEntity> users;

	@ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "group_component",
			joinColumns = {
				@JoinColumn(name = "group_id")},
			inverseJoinColumns = {
				@JoinColumn(name = "components_id")})
	private List<ComponentEntity> components;

	public GroupEntity() {
		this.users = new ArrayList<>();
		this.components = new ArrayList<>();
	}

	
	
	
	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setId(Long id) {
		this.id = id;	
	}

	@Override
	public void setName(String name) {	
		this.name = name;
	}
	
	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getWrite() {
		return write;
	}


	public void setWrite(Boolean write) {
		this.write = write;
	}

	public Boolean getExecute() {
		return execute;
	}

	public void setExecute(Boolean execute) {
		this.execute = execute;
	}


	public List<UserEntity> getUsers() {
		return new ArrayList<>(users);
	}

	public void setUsers(List<UserEntity> inUsers) {
		if (inUsers == null) {
			this.users = new ArrayList<>();
		} else {
			this.users = new ArrayList<>(inUsers);
		}
	}

	public void addUsers(UserEntity inUser) {
		if (!this.users.contains(inUser)) {
			this.users.add(inUser);
		}
	}

	public void removeI2b2Role(UserEntity inUser) {
		this.users.remove(inUser);
	}

	
	
	public List<ComponentEntity> getComponents() {
		return new ArrayList<>(components);
	}

	public void setComponents(List<ComponentEntity> inComponents) {
		if (inComponents == null) {
			this.components = new ArrayList<>();
		} else {
			this.components = new ArrayList<>(inComponents);
		}
	}

	public void addComponents(ComponentEntity inComponents) {
		if (this.components.contains(inComponents)) {
			this.components.add(inComponents);
		}
	}

	public void removeComponents(ComponentEntity inComponents) {
		this.components.remove(inComponents);
	}

	
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 33 * hash + Objects.hashCode(this.id);
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
		final GroupEntity other = (GroupEntity) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GroupEntity{" + "id=" + id + ", name=" + name + ", users=" + users + ", components=" + components + '}';
	}
}
