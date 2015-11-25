package com.org.coop.society.data.customer.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the user_credential_otp database table.
 * 
 */
@Entity
@Table(name="user_credential_otp")
@NamedQuery(name="UserCredentialOtp.findAll", query="SELECT u FROM UserCredentialOtp u")
public class UserCredentialOtp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_name")
	private String userName;

	@Column(name="end_date")
	private Timestamp endDate;

	private String otp;

	@Column(name="start_date")
	private Timestamp startDate;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="user_name", insertable=false, updatable=false)
	private User user;

	public UserCredentialOtp() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getOtp() {
		return this.otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}