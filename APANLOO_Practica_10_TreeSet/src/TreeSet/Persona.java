package TreeSet;

import java.util.TreeSet;

public class Persona {
	
	String nombre;
	int edad; 
	double altura;
		
	public Persona() {
		this.nombre=" ";
		this.edad=0;
		this.altura=0;
	}
	
	public Persona(String nombre, int edad, double altura) {
		this.nombre= nombre;
		this.edad= edad;
		this.altura= altura;
	}
	
	public String despliegaInfo() {
		String info= "Nombre: " + nombre + "\nEdad: " + edad + "\nAltura: " + altura;
	
		return info;
	}
	
	public static void despliegaPersonas(TreeSet<Persona> treeSet) {
		
		for(Persona persona: treeSet) {
			
			System.out.println(persona.despliegaInfo());
		}
	}
	
	public static boolean contienePersona(String nombre, TreeSet<Persona> treeSet) {
		
		boolean contiene=false;
		
		 for(Persona persona: treeSet) {
			 
			 if(persona.nombre.compareToIgnoreCase(nombre)== 0) {
				 contiene= true;
			 }
		 }
		 
		 return contiene; 
	}
	
	public static boolean remuevePersona(String nombre, TreeSet<Persona> treeSet) {
		
		boolean remueve= false;
		Persona personaA_Remover= new Persona();
		
		for(Persona persona: treeSet) {
			
			if(persona.nombre.compareToIgnoreCase(nombre)== 0) {
				
				personaA_Remover= persona;
			}
		}
		
		remueve= treeSet.remove(personaA_Remover);
		
		return remueve;
	}
}
