package com.dee.ecommerce.auth_service.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private String id;
		
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable (
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"))
	@Column (name = "role")
	private Set<String> roles = new HashSet();  
}
