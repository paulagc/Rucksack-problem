import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {

	
	public static void main(String [] args) throws IOException{
		long time_start, time_end;
		//Variable que contara tiempo de ejecucion
		time_start = System.currentTimeMillis();
//-------------------------------------------------------------------------------------------------------------------------	    
//		PREPARACION DE DATOS DE ENTRADA
//-------------------------------------------------------------------------------------------------------------------------		
		//Variable para indicar si se pide traza
		boolean traza = false;
		//Ruta relativa de los ficheros de prueba
		String entrada = null;
		String salida = null;
		//String que contiene la ayuda a mostrar
		String ayuda = "SINTAXIS:  \n"
				+ "robot [-t][-h][fichero_entrada] [fichero_salida] \n "
				+ "-t  Traza la aplicacion del algoritmo a los datos \n"
				+ "-h  Muestra esta ayuda  \n"
				+ "fichero_entrada  Nombre del fichero de entrada \n"
				+ "fichero_salida  Nombre del fichero de salida \n";
		
		//Controla varios errores en la entrada por consola
		if ((args.length > 4)|| (args.length == 0)){
			System.out.println("Numero de argumentos incorrecto");
			System.exit(0);
		}
		if(args.length > 1){
			if(args[1].equals("-t")){
				System.out.println("Argumentos incorrectos");
				System.out.println(ayuda);
				System.exit(0);
			}
		}else if(args.length > 3){
			if(args[1].equals("-t")||args[2].equals("-t")||args[3].equals("-t")){
				System.out.println("Argumentos incorrectos");
				System.out.println(ayuda);
				System.exit(0);	
			}else if(args[2].equals("-h")||args[3].equals("-h")){
				System.out.println("Argumentos incorrectos");
				System.out.println(ayuda);
				System.exit(0);
			}
		}
		
		//Indica si hay fichero de entrada y salida
		boolean hayEntrada = false;
		boolean haySalida = false;
		
		//Bucle para recorrer los argumentos
		for (int i = 0; i< args.length; i++){
			if(args[i].equals("-h")){
				//Si la ayuda es el segundo argumento tiene que haber un -t delante o estará erroneo
				if(i==1){
					if(traza){
						System.out.println(ayuda);
					}else{
						System.out.println("Argumentos incorrectos!");
						System.out.println(ayuda);
						System.exit(0);	
					}
				}else{
					System.out.println(ayuda);
					
				}
			//Si hay -t activa la traza
			}else if(args[i].equals("-t")){
				traza = true;
			//Guarda el nombre del archivo de entrada
			}else if (!hayEntrada){
				entrada = args[i];
				hayEntrada = true;
			//Guarda el nombre del archivo de salida
			}else{
				salida = args[i];
				haySalida = true;
			}
		}
		
		//Si no hay fichero de entrada es un error
		if(!hayEntrada){
			System.out.println("No hay fichero de entrada");
			System.exit(0);
		}
		
		BufferedReader in = null;
		//Lectura del fichero de operaciones
		try{
		    in = new BufferedReader(new InputStreamReader(new FileInputStream(entrada), "UTF-8"));
		}catch(FileNotFoundException ex){
			System.out.println("El fichero de entrada no existe");
			System.exit(0);
		}
//-------------------------------------------------------------------------------------------------------------------------	    
//						PREPARACION DE LOS DATOS
//-------------------------------------------------------------------------------------------------------------------------			
		String line;
		int i = 0;
		
		//Numero de objetos
		int n = 0;
		//Capacidad mochila
		int v = 0;
		
		//Lista de objetos
		ArrayList<Objeto> objetos = new ArrayList<Objeto>();
		ArrayList<Integer> vol = new ArrayList<Integer>();
		ArrayList<Integer> ben = new ArrayList<Integer>();
		
		while ((line = in.readLine())!=null) {
			//Primera linea numero de objetos
			if(i == 0){
				try{
					n = Integer.parseInt(line);
					if(n == 0){
						System.out.println("El numero de objetos no puede ser 0");
						System.exit(0);
					}
				}catch(Exception e){
					System.out.println("Caracteres no validos");
					System.exit(0);
				}
				i++;
			//Si es la ultima linea del archivo recoge la capacidad de la mochila
			}else if(i == n+1){
				try{
					v = Integer.parseInt(line);
					if(v == 0){
						System.out.println("Error la capacidad no puede ser 0");
						System.exit(0);
					}
				}catch(Exception e){
					System.out.println("No se indica capacidad/ no coincide el numero de objetos/ caracter no valido");
					System.exit(0);
				}	
				i++;
			//Si es una linea de objeto, crea el nuevo objeto con su volumen y beneficio y lo añade a la lista
			}else{
				
				String[] aux = line.split(" ");
				if(aux.length < 2 || aux.length > 2){
					System.out.println("Error en objetos");
					System.exit(0);
				}else{
					try{
						int volumen = Integer.parseInt(aux[0]);
						int beneficio = Integer.parseInt(aux[1]);
						if(volumen == 0 || beneficio == 0){
							System.out.println("Error volumen y beneficio no pueden ser 0");
							System.exit(0);
						}
						Objeto obj = new Objeto(volumen, beneficio);
						objetos.add(obj);
						vol.add(volumen);
						ben.add(beneficio);
					}catch(Exception e){
						System.out.println("Caracteres no validos");
						System.exit(0);
					}
				}
				i++;
			}
		}
	    
		//Si el numero de objetos no coincide con lo especificado es error
		if(objetos.size() != n){
			System.out.println("El numero de objetos no coincide");
			System.exit(0);
		}
    	
    	//Cierra la lectura de archivo
	    in.close();
//-------------------------------------------------------------------------------------------------------------------------	    
//					MOCHILA
//-------------------------------------------------------------------------------------------------------------------------	    
	    //Llama a la clase que resuelve el problema con la lista de objetos, numero de ellos, volumen maximo, si hay traza o no, 
	    //lista de volumenes y de beneficios
	    Mochila mochila = new Mochila(objetos, n, v, traza, vol, ben);
	    String solucion = mochila.mochila();
	    
	    //Tiempo que tardo la ejecucion
	    time_end = System.currentTimeMillis();
	    solucion += "\nTiempo: " +(time_end -time_start) +" milisegundos.";
	    
	    //Si hay salida escribe el archivo, si no por pantalla
	    if(haySalida){
	    	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(salida), "UTF-8"));
	    	System.out.println("Se escribió el archivo");
	    	out.write(solucion);
	    	out.flush();
	    	out.close();
	    }else{
	    	System.out.println(solucion);
	    }    
	}
}

