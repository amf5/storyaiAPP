package com.storyAi.story_AI.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
@Entity
@Table(name = "roles")
public class Role {
	@Id
	private String roleName;
	@Column(nullable = false)
	private String descriptionRole;
	
	public Role() {}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescriptionRole() {
		return descriptionRole;
	}
	public void setDescriptionRole(String descriptionRole) {
		this.descriptionRole = descriptionRole;
	}
	
	public Role(String roleName, String descriptionRole) {
		super();
		this.roleName = roleName;
		this.descriptionRole = descriptionRole;
	}
	
	
	
	
	

}
