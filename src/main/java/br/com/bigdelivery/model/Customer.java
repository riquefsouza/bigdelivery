package br.com.bigdelivery.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@NotBlank
	@NotEmpty
	private String name;

	@NotNull
	@NotBlank
	@NotEmpty
	private String address;

	@NotNull
	@NotBlank
	@NotEmpty
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	@NotNull
	@NotBlank
	@NotEmpty
	//@JsonIgnore
	private String password;

	public Customer() {
		super();
	}

	public Customer(String name, String address, String email, String password) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.creation = new Date();
		this.setPassword(password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		// PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
		// this.password = PASSWORD_ENCODER.encode(password);
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", creation="
				+ creation + ", password=" + password + "]";
	}

}
