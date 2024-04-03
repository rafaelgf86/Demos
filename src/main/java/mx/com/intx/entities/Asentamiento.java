/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class Asentamiento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idAsentaCpcons;
	private String dAsenta;
	private String dCodigo;
	
	public String getIdAsentaCpcons() {
		return idAsentaCpcons;
	}
	public void setIdAsentaCpcons(String idAsentaCpcons) {
		this.idAsentaCpcons = idAsentaCpcons;
	}
	public String getdAsenta() {
		return dAsenta;
	}
	public void setdAsenta(String dAsenta) {
		this.dAsenta = dAsenta;
	}
	public String getdCodigo() {
		return dCodigo;
	}
	public void setdCodigo(String dCodigo) {
		this.dCodigo = dCodigo;
	}
}
