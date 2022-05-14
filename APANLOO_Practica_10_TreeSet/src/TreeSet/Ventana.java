package TreeSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements ActionListener, AdjustmentListener, FocusListener {

	static Ventana treeSetGrafico;
	
	JButton masB;
	JButton menosB;
	JButton buscarB;
	
	JTextField nombreT;
	JTextField edadT;
	JTextField alturaT;
	
	JPanel panelInferior;
	
	JScrollPane scroll;
	
	JLabel label;
	
	Dimension labelDimension;
	
	TreeSet<Persona> personas;
	
	ArrayList<Integer> particiones;
	
	String nombrePersona;
	
	int sizePersonas, maxTam, tamParticiones, scrollValue;
	
	Timer timer;
	
	public Ventana() {
		
		super("TreeSet Grafico");
		this.setSize(315, 390);
		
		timer= new Timer(5000, this);
		
		/*------Paneles------*/
		JPanel panelSuperior= new JPanel();
		JPanel panelCentral= new JPanel();
		panelInferior= new JPanel();
		
		scroll= new JScrollPane(panelInferior, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.getHorizontalScrollBar().addAdjustmentListener(this);
		scroll.setPreferredSize(new Dimension(315, 20));
		scroll.setBorder(null);
		
		this.add(panelSuperior, BorderLayout.NORTH);
		this.add(panelCentral, BorderLayout.CENTER);
		this.add(scroll, BorderLayout.SOUTH);
		
		/*------PanelSuperior------*/
		panelSuperior.setPreferredSize(new Dimension(315, 200));
		panelSuperior.setLayout(new GridLayout(3, 2));
		panelSuperior.setBackground(Color.DARK_GRAY);
		
		//Labels
		JLabel nombreL= new JLabel("     Nombre");
		JLabel edadL= new JLabel("     Edad");
		JLabel alturaL= new JLabel("     Altura");
		
		nombreL.setForeground(Color.white);
		edadL.setForeground(Color.white);
		alturaL.setForeground(Color.white);
		
		//Textfields
		JPanel nombrePT= new JPanel();
		JPanel edadPT= new JPanel();
		JPanel alturaPT= new JPanel();
		
		nombrePT.setLayout(null);
		edadPT.setLayout(null);
		alturaPT.setLayout(null);
		
		nombreT= new JTextField();
		edadT= new JTextField();
		alturaT= new JTextField();
		
		nombreT.setBounds(10, 20, 130, 30);
		edadT.setBounds(10, 20, 130, 30);
		alturaT.setBounds(10, 20, 130, 30);
		
		nombreT.addFocusListener(this);
		edadT.addFocusListener(this);
		alturaT.addFocusListener(this);
		
		//add
		nombrePT.add(nombreT);
		edadPT.add(edadT);
		alturaPT.add(alturaT);
		
		panelSuperior.add(nombreL);
		panelSuperior.add(nombrePT);
		panelSuperior.add(edadL);
		panelSuperior.add(edadPT);
		panelSuperior.add(alturaL);
		panelSuperior.add(alturaPT);
		
		/*------PanelCentral------*/
		panelCentral.setLayout(null);
		
		masB= new JButton("+");
		menosB= new JButton("-");
		buscarB= new JButton("Buscar");
		
		masB.setBounds(60, 20, 70, 30);
		menosB.setBounds(170, 20, 70, 30);
		buscarB.setBounds(95, 70, 110, 30);
		
		Font fontBotones= new Font("Arial", Font.PLAIN, 18);
		masB.setFont(fontBotones);
		menosB.setFont(fontBotones);
		
		Border borderBotones= BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		
		masB.setBorder(borderBotones);
		menosB.setBorder(borderBotones);
		buscarB.setBorder(borderBotones);
		
		masB.setBackground(Color.white);
		menosB.setBackground(Color.white);
		buscarB.setBackground(Color.white);
		
		panelCentral.add(masB);
		panelCentral.add(menosB);
		panelCentral.add(buscarB);
		
		masB.addActionListener(this);
		buscarB.addActionListener(this);
		menosB.addActionListener(this);
		
		/*------Panel Inferior------*/
		//JLabel label= new JLabel(" ");
		labelDimension= new Dimension(320, 10);
		//label.setPreferredSize(labelDimension);
		
		//panelInferior.add(label);
		
		personas= new TreeSet<Persona>(new NameComparator());
		
		particiones= new ArrayList<Integer>();
		//System.out.println("particiones.size()= " + particiones.size());
		
		/*------Visibilidad del frame------*/
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		treeSetGrafico= new Ventana();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()== masB) {
			
			try {
				
				nombrePersona= nombreT.getText();

				//se agrega a una persona a la coleccion con los datos ingresados en los textfields correspondientes
				personas.add(new Persona(nombrePersona, Integer.parseInt(edadT.getText()), Double.parseDouble(alturaT.getText())));
				
				//se agrega un JLabel vacio al panelInferior para activar el scroll bar horizontal
				addLabel();
				
				//una vez presionado, se borra la info de los textfields
				resetTextfields();	
				
				//Se lanza una ventana diciendo que la persona ha sido agregada con exito	
				JOptionPane.showMessageDialog(null, "La persona se ha agregado.", "Exito", JOptionPane.INFORMATION_MESSAGE);
			
				calculaParticiones();
				
				//Se revalida el JFrame principal, para que se termine de agregar el label
				treeSetGrafico.revalidate();
			} 
			catch (Exception e1) {
				
				JOptionPane.showMessageDialog(null, 
						"Faltan casillas por llenar.", 
						"Falta Informacion", 
						JOptionPane.WARNING_MESSAGE);
			}	
		}
		
		//si se quiere eliminar y/o buscar una persona, se debe llenar el campo "nombre" antes de presionar el 
		//boton correspondiente
		if(e.getSource()== menosB) {
			
				//se verifica si se removio a la persona ingresadad. 
				if (Persona.remuevePersona(nombreT.getText(), personas) == true) {
					//En el caso en que si, se lanza una ventana diciendo que la persona ha sido removida con exito
					//y se elimina el ultimo label agregado al panelInferior para actualizar el scrollbar
					panelInferior.remove(label);
					
					JOptionPane.showMessageDialog(null, "La persona ha sido eliminada.", "Eliminada", JOptionPane.INFORMATION_MESSAGE);
					
					treeSetGrafico.revalidate();
				}
				else {
					
					JOptionPane.showMessageDialog(null, 
						"No se encontro a la persona a eliminar.", 
						"Falta Informacion", 
						JOptionPane.WARNING_MESSAGE);
				} 
		}
		
		if(e.getSource()== buscarB) {
			
			//se verifica si se encontro a la persona buscada
			if (Persona.contienePersona(nombreT.getText(), personas) == true) {
				
				//En el caso en que si, se lanza una ventana diciendo que la persona ha sido encontrada
				JOptionPane.showMessageDialog(null, "La persona se encontro.", "Exito", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				
				JOptionPane.showMessageDialog(null, 
					"No se encontro a la persona.", 
					"Fracaso", 
					JOptionPane.WARNING_MESSAGE);
			} 
		}
		
		if(e.getSource()== timer && scroll.getHorizontalScrollBar().getValueIsAdjusting()==false) {
			
			//Borra la info de los textfields
			nombreT.setText("");
			edadT.setText("");
			alturaT.setText("");
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		
		//el scroll muestran en forma ascendente (por nombre) las personas que se encuentran en el TreeSet
		//la info de cada persona se muestra en los textfields
		//Adicionalmente, si no se mueve el scroll por 5 segundos, la info en los textfields se borra
		
		if(e.getSource()== scroll.getHorizontalScrollBar()) {
			
			if(personas.size()==0) {
				//System.out.println("Persona size= 0. No se han agregado personas.");
			}
			else {
				timer.start();
				comparaValorScroll(e.getValue());
			}			
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		timer.stop();
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
	
	public void comparaValorScroll(int scrollValue) {
		
		//System.out.println("ComparaValorScroll");
		
		//cada vez que se mueva el scrollBar comparamos su valor con los valores en el arrayList
		for(int i=0; i<personas.size(); i++) {
			
			//System.out.println("For loop");
				
			//int j= i+1;
				
			//solo si es la ultima casilla 
			if(i==particiones.size()-1 && scrollValue>= particiones.get(i)) {
				
				//obtenemos la info de la ultima persona en el treeSet
				
				//System.out.println("Personas: " + personas.size() + "Particiones: " + particiones.size() + "Particion actual: " + j );
				//System.out.println(personas.last().despliegaInfo());
				
				nombreT.setText(personas.last().nombre);
				edadT.setText(Integer.toString(personas.last().edad));
				alturaT.setText(Double.toString(personas.last().altura));
			}
			
			//cualquier otra casilla
			else {
				
				if(scrollValue>= particiones.get(i) && scrollValue<particiones.get(i+1)) {
						
					//obtenemos la info de la persona numero i+1 en el treeSet
						
					//System.out.println("Personas: " + sizePersonas + "Particiones: " + particiones.size() + "Particion actual: " + j);
					
					ArrayList<Persona> personasArray= new ArrayList<>(personas);
					//System.out.println(personasArray.get(i).despliegaInfo());
					
					nombreT.setText(personasArray.get(i).nombre);
					edadT.setText(Integer.toString(personasArray.get(i).edad));
					alturaT.setText(String.valueOf(personasArray.get(i).altura));
				}
			}
		}
	}
	
	public void calculaParticiones() {
		
		/*	0 - 0	
		*  1 - 31
		*  2 - 356
		*  3 - 681
		*  4 - 1006
		*  n - (n-1)*325
		*/
		
		//System.out.println("Personas size= " + personas.size());
		
		//Calculamos el tamano maximo de scroll bar que es visible
		maxTam= ((personas.size()-1)*325)+31;
		//System.out.println("maxTam= " + maxTam);
		
		//dividimos maxTam entre el tamanio del treeSet para crear las particiones para cada persona
		tamParticiones= maxTam/personas.size();
		//System.out.println("tamParticiones: " + tamParticiones);
		
		//reseteamos el arraylist de particiones para que no se acumulen
		particiones.clear();
		//System.out.println("particiones.size()= " + particiones.size());
		
		//Agregamos el valor de cada particion al arrayList de particiones
		for(int i=0; i<personas.size(); i++) {
			particiones.add(i*tamParticiones);
		}
			
		//System.out.println("particiones.size()= " + particiones.size());
		//System.out.println("Particiones: " + sizePersonas + "Tam de Part: " + tamParticiones);
	}
	
	public void resetTextfields() {
		nombreT.setText("");
		edadT.setText("");
		alturaT.setText("");
	}
	
	public void addLabel() {
		label= new JLabel(" ");
		label.setPreferredSize(labelDimension);
		panelInferior.add(label);
	}
}
