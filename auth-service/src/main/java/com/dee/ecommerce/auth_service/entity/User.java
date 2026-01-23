package com.dee.ecommerce.auth_service.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id", length = 36, updatable = false, nullable = false)
	private String id;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank(message = "Name is required")
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String name;

	@NotBlank(message = "Password is required")
	@Column(nullable = false) // Note: length should be enough for Bcrypt (usually 60)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role", length = 20)
	private Set<String> roles = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}