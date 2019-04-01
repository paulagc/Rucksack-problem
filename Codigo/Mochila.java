import java.util.ArrayList;

public class Mochila {
	//Array que contiene los objetos
	private ArrayList<Objeto> objetos;
	//Numero de objetos
	private int n;
	//Capacidad maxima de la mochila
	private int v;
	//Booleano que indica si hay traza
	private boolean traza;
	//String que contiene la solucion
	private String solucion;
	//String que contiene la traza 
	private String sTraza;
	//Tabla de resolucion del algoritmo
	private int[][] tabla;
	//Array de volumenes de los objetos
	private ArrayList<Integer> vol;
	//Array de beneficios de los objetos
	private ArrayList<Integer> ben;
	
	public Mochila(ArrayList<Objeto> objetos, int n, int v, boolean traza, ArrayList<Integer> vol, ArrayList<Integer> ben){
		this.setObjetos(objetos);
		this.setN(n);
		this.setV(v);
		this.setTraza(traza);
		this.solucion = "\n";
		this.sTraza = "\n";
		this.vol = vol;
		this.ben = ben;
	}
	//Rellena M[i,0] con ceros y M[0,j] con ceros
	public void rellenarCeros(){
		//Da tamaño a la tabla (necesita una fila y columna mas)
		tabla = new int[n+1][v+1];
		//Recorre filas
		for(int i = 0; i< n+1 ; i++){
			tabla[i][0] = 0;	
		}
		//Recorre columnas
		for(int j = 0; j< v+1 ; j++){
			tabla[0][j] = 0;
		}
	}
	
	//Metodo que imprime la tabla
	public String imprimirTabla(){
		String salida = "\n";
		for(int i = 0; i< n+1 ; i++){
			for(int j = 0; j< v+1 ; j++){
				//Imprime el numero y tabula
				salida += tabla[i][j] +"\t";
				//Cuando llega al final de fila cambia de fila
				if (j == (v)){
					salida += "\n";
				}
			}
			
		}
		return salida;
	}
	
	//Metodo auxiliar que calcula el maximo de dos numeros
	public int max(int n, int m){
		int max = 0;
		if(n > m){
			max = n;
		}else{
			max = m;
		}
		return max;
	}
	
	//Metodo que devuelve un array de ceros o unos segun se escoja ese objeto o no para la solucion
	public int[] objetosMochila(){
		
		int[] obj = new int[n];
		//Variable que recoge la capacidad restante de la mochila
		int w = v;
		sTraza += "------Eleccion de los objetos------ \n\n" +"Capacidad de la mochila: " +w +"\n";
		for(int i = n; i >= 1 ; i--){
			//Si el valor de la casilla coincide con el de la fila superior no se incluye el objeto
			if( tabla[i][w] == tabla[i-1][w]){
				obj[i-1] = 0;
			//Si no coinciden se incluye y se actualiza la capacidad restante
			}else{
				obj[i-1] = 1;
				sTraza += "Elegido objeto de volumen " +objetos.get(i-1).getVolumen() 
						+" y beneficio " +objetos.get(i-1).getBeneficio() +"\n";
				w = w - vol.get(i-1);
				sTraza += "Capacidad restante en la mochila: " +w +"\n\n";
			}
		}
		return obj;
	}
	
	//Metodo principal que resuelve el algoritmo
	public String mochila(){
		
		//Rellena la tabla con ceros
		rellenarCeros();
		sTraza += "-------Creacion de la tabla paso a paso-------\n\n" +"Paso 0: " +imprimirTabla() +"\n";
		
		int paso = 1;
		//Rellena la tabla con los valores
		for(int i = 1; i<= n; i++){
			for(int j = 1; j<= v; j++){
				if(vol.get(i-1)> j){
					tabla[i][j] = tabla[i-1][j];
				}else{
					tabla[i][j] = max(tabla[i-1][j], tabla[i-1][j-vol.get(i-1)]+ben.get(i-1));
				}
			}
			sTraza += "Paso " +paso +": "+imprimirTabla() +"\n";
			paso++;
		}
		
		
		//Obtiene el array de ceros y unos con los objetos escogidos
		int[] cerosUnos = objetosMochila();
		
		//Si hay traza agrega el paso a paso
		if(traza){
			solucion += sTraza;
		}
				
		//Recupera que objetos son
		String volumen = "Objetos de volumen: ";
		int beneficio = 0;
		int m = 0;
		while (m < cerosUnos.length){
			//Si el objeto fue elegido
			if(cerosUnos[m] == 1){
				volumen += objetos.get(m).getVolumen() +", ";
				beneficio = beneficio + objetos.get(m).getBeneficio();
			}
			m++;
		}
		
		solucion += volumen;
		solucion += "\nBeneficio : " +beneficio;
		return solucion;
		
	}
	
	
	

	public ArrayList<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(ArrayList<Objeto> objetos) {
		this.objetos = objetos;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public boolean isTraza() {
		return traza;
	}

	public void setTraza(boolean traza) {
		this.traza = traza;
	}
}
