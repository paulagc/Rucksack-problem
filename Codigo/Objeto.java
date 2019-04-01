//Clase objeto que contendra los atributos volumen y beneficio de cada uno
public class Objeto {
	//Volumen del objeto
	private int volumen;
	//Beneficio asociado al objeto
	private int beneficio;
	
	public Objeto(int volumen, int beneficio){
		this.volumen = volumen;
		this.beneficio = beneficio;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public int getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(int beneficio) {
		this.beneficio = beneficio;
	}
}
