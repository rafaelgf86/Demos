/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * @author INTX
 *
 */
@Embeddable
public class SepomexId  implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Column(name="d_codigo")
	private String dCodigo;
	
	@Column(name="c_estado")
	private String cEstado;
	
	@Column(name="c_mnpio")
	private String cMnpio;
	
	@Column(name="id_asenta_cpcons")
	private String idAsentaCpcons;
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public String getdCodigo() {
		return dCodigo;
	}
	public void setdCodigo(String dCodigo) {
		this.dCodigo = dCodigo;
	}
	public String getcEstado() {
		return cEstado;
	}
	public void setcEstado(String cEstado) {
		this.cEstado = cEstado;
	}
	public String getcMnpio() {
		return cMnpio;
	}
	public void setcMnpio(String cMnpio) {
		this.cMnpio = cMnpio;
	}
	public String getIdAsentaCpcons() {
		return idAsentaCpcons;
	}
	public void setIdAsentaCpcons(String idAsentaCpcons) {
		this.idAsentaCpcons = idAsentaCpcons;
	}
	
	@Override
	public String toString() {
		return "SepomexId [dCodigo=" + dCodigo + ", cEstado=" + cEstado + ", cMnpio=" + cMnpio + ", idAsentaCpcons="
				+ idAsentaCpcons + "]";
	}
	
	

}