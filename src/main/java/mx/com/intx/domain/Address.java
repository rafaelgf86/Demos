/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/** 
 * Entidad persistida de la tabla Address 
 * @author INTX
 */ 
@Entity
@Table(name="Address_INTX")
public class Address implements Serializable  { 

	private static final long serialVersionUID = 1L;

	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAddress;
	
	@Column(name="street")
	private String street;
	
	@Column(name="exterior_number")
	private String exteriorNumber;
	
	@Column(name="interior_number")
	private String interiorNumber;
	
	@Column(name="home_phone")
	private String homePhone;
	
	@Column(name="office_phone")
	private String officePhone;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="d_codigo", referencedColumnName = "d_codigo"),
        @JoinColumn(name="c_estado", referencedColumnName = "c_estado"),
        @JoinColumn(name="c_mnpio", referencedColumnName = "c_mnpio"),
        @JoinColumn(name="id_asenta_cpcons", referencedColumnName = "id_asenta_cpcons")
	})
	private Sepomex sepomex;

	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	
	public Long getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Long idAddress) {
		this.idAddress = idAddress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getExteriorNumber() {
		return exteriorNumber;
	}

	public void setExteriorNumber(String exteriorNumber) {
		this.exteriorNumber = exteriorNumber;
	}

	public String getInteriorNumber() {
		return interiorNumber;
	}

	public void setInteriorNumber(String interiorNumber) {
		this.interiorNumber = interiorNumber;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public Sepomex getSepomex() {
		return sepomex;
	}

	public void setSepomex(Sepomex sepomex) {
		this.sepomex = sepomex;
	}

	@Override
	public String toString() {
		return "Address [idAddress=" + idAddress + ", street=" + street + ", exteriorNumber=" + exteriorNumber
				+ ", interiorNumber=" + interiorNumber + ", homePhone=" + homePhone + ", officePhone=" + officePhone
				+ ", sepomex=" + sepomex + "]";
	}
}
