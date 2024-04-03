/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author INTX
 *
 */
@Entity
@Table(name="Sepomex")
public class Sepomex implements Serializable {

	private static final long serialVersionUID = 1L;
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@EmbeddedId
	private SepomexId sepomexId;
	
	@Column(name="d_asenta")
	private String dAsenta;

	@Column(name="d_tipo_asenta")
	private String dTipoAsenta;
	
	@Column(name="d_mnpio")
	private String dMnpio;
	
	@Column(name="d_estado")
	private String dEstado;	
	
	@Column(name="d_ciudad")
	private String dCiudad;
	
	@Column(name="d_cp")
	private String dCP;
	
	@JsonIgnore
	@Column(name="c_oficina")
	private String cOficina;
	
	@JsonIgnore
	@Column(name="c_cp")
	private String cCP;
	
	@JsonIgnore
	@Column(name="c_tipo_asenta")
	private String cTipoAsenta;
	
	@JsonIgnore
	@Column(name="d_zona")
	private String dZona;
	
	@Column(name="c_cve_ciudad")
	private String cCveCiudad;
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	
	public SepomexId getSepomexId() {
		return sepomexId;
	}

	public void setSepomexId(SepomexId sepomexId) {
		this.sepomexId = sepomexId;
	}

	public String getdAsenta() {
		return dAsenta;
	}

	public void setdAsenta(String dAsenta) {
		this.dAsenta = dAsenta;
	}

	public String getdTipoAsenta() {
		return dTipoAsenta;
	}

	public void setdTipoAsenta(String dTipoAsenta) {
		this.dTipoAsenta = dTipoAsenta;
	}

	public String getdMnpio() {
		return dMnpio;
	}

	public void setdMnpio(String dMnpio) {
		this.dMnpio = dMnpio;
	}

	public String getdEstado() {
		return dEstado;
	}

	public void setdEstado(String dEstado) {
		this.dEstado = dEstado;
	}

	public String getdCiudad() {
		return dCiudad;
	}

	public void setdCiudad(String dCiudad) {
		this.dCiudad = dCiudad;
	}

	public String getdCP() {
		return dCP;
	}

	public void setdCP(String dCP) {
		this.dCP = dCP;
	}

	public String getcOficina() {
		return cOficina;
	}

	public void setcOficina(String cOficina) {
		this.cOficina = cOficina;
	}

	public String getcCP() {
		return cCP;
	}

	public void setcCP(String cCP) {
		this.cCP = cCP;
	}

	public String getcTipoAsenta() {
		return cTipoAsenta;
	}

	public void setcTipoAsenta(String cTipoAsenta) {
		this.cTipoAsenta = cTipoAsenta;
	}

	public String getdZona() {
		return dZona;
	}

	public void setdZona(String dZona) {
		this.dZona = dZona;
	}

	public String getcCveCiudad() {
		return cCveCiudad;
	}

	public void setcCveCiudad(String cCveCiudad) {
		this.cCveCiudad = cCveCiudad;
	}

	@Override
	public String toString() {
		return "Sepomex [sepomexId=" + sepomexId + "]";
	}	
}

