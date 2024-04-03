/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class Municipio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cMnpio;
	private String dMnpio;
	
	public String getcMnpio() {
		return cMnpio;
	}
	public void setcMnpio(String cMnpio) {
		this.cMnpio = cMnpio;
	}
	public String getdMnpio() {
		return dMnpio;
	}
	public void setdMnpio(String dMnpio) {
		this.dMnpio = dMnpio;
	}
}
