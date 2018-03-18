package br.com.bigdelivery.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tab_store")
public class Store implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String contactName;

	private String contactPhone;

	private String email;

	private String address;

	private Integer daysWeek;
	
	@JsonIgnore
	@OneToOne
	private Cousine cousine;

	@OneToMany(mappedBy = "store")
	private Set<Product> products = new HashSet<>();

	public Store() {
		super();
	}

	public Store(String name, String contactName, String contactPhone, String email, String address, Integer daysWeek,
			Cousine cousine) {
		super();
		this.name = name;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.email = email;
		this.address = address;
		this.daysWeek = daysWeek;
		this.cousine = cousine;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDaysWeek() {
		return daysWeek;
	}

	public void setDaysWeek(Integer daysWeek) {
		this.daysWeek = daysWeek;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}


	public Cousine getCousine() {
		return cousine;
	}

	public void setCousine(Cousine cousine) {
		this.cousine = cousine;
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
		Store other = (Store) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", contactName=" + contactName + ", contactPhone=" + contactPhone
				+ ", email=" + email + ", address=" + address + ", daysWeek=" + daysWeek + ", cousine=" + cousine
				+ ", products=" + products + "]";
	}



}
