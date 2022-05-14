package TreeSet;

import java.util.Comparator;

public class NameComparator implements Comparator<Persona>{

	@Override
	public int compare(Persona p1, Persona p2) {
		
		if(p1.nombre.compareTo(p2.nombre)< 0){
			return -1;
		}
		
		else if(p1.nombre.compareTo(p2.nombre)> 0){
			return 1;
		}
		
		else{
			return 0;
		}
	}
}
