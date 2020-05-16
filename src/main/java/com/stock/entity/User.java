package com.stock.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;

	@Column(name = "user_name", length = 20)
	@NotNull
	@Size(min = 3, max = 20)
	private String username;

	@Column(name = "email", length = 20, unique = true)
	@NotNull
	@Size(min = 8, max = 20)
	private String email;

	@JsonIgnore
    @Column(name = "password", length = 128)
    @NotNull
    @Size(min = 8, max = 128)
	private String password;

	@Column(name = "mobile", length = 16)
    @Size(min = 0, max = 16)
	private String mobile;

	
	@Column(name = "create_time")
	@CreatedDate
	private Timestamp createTime;

	@Column(name = "last_upd_time")
	@LastModifiedDate
	private Timestamp lastUpdTime;

	@Column(name = "user_type")
    @Enumerated(EnumType.STRING)
	private UserType userType;//role define: ADMIN/USER

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(Timestamp lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
