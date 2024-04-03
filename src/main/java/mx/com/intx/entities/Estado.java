/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cEstado;
	private String dEstado;
	
	public String getcEstado() {
		return cEstado;
	}
	public void setcEstado(String cEstado) {
		this.cEstado = cEstado;
	}
	public String getdEstado() {
		return dEstado;
	}
	public void setdEstado(String dEstado) {
		this.dEstado = dEstado;
	}
}
