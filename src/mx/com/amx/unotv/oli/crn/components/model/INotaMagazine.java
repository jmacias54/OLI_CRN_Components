package mx.com.amx.unotv.oli.crn.components.model;

import java.io.Serializable;

public class INotaMagazine implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fcIdMagazine;
	private String fcIdContenido;
	private String fcUrlExterna;
	private int fiOrden;

	public INotaMagazine() {
	}

	public String getFcIdMagazine() {
		return fcIdMagazine;
	}

	public void setFcIdMagazine(String fcIdMagazine) {
		this.fcIdMagazine = fcIdMagazine;
	}

	public String getFcIdContenido() {
		return fcIdContenido;
	}

	public void setFcIdContenido(String fcIdContenido) {
		this.fcIdContenido = fcIdContenido;
	}

	public String getFcUrlExterna() {
		return this.fcUrlExterna;
	}

	public void setFcUrlExterna(String fcUrlExterna) {
		this.fcUrlExterna = fcUrlExterna;
	}

	public int getFiOrden() {
		return this.fiOrden;
	}

	public void setFiOrden(int fiOrden) {
		this.fiOrden = fiOrden;
	}

}