package com.arms.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	private String email;
	
	@NotNull
	private String passwd;
	
	@NotNull
	private Date createdDate;
	
	@NotNull
	private Date updatedDate;
	
	
}
