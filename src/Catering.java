import java.util.Date;

//Classe che descrive la prenotazione con Catering, che estende la classe Prenotazione

public class Catering extends Prenotazione {
	
	private int numeroBambini;
	
	//Costruttore
	public Catering(String nome, Date data, int numeroBambini) {
		
		//Variabili ereditate dalla superclasse Prenotazione
		super(nome, data);
		//Variabile privata della Classe Catering
		this.numeroBambini = numeroBambini;	
		
	}
	
	public int getNumeroBambini () {
		return this.numeroBambini;
	}
	
	@Override
	public String toString() {
		String stampa = "Prenotazione con catering del " + formatter.format(super.getData()) + " a nome " + super.getNome() + ". \n     Numero bambini: " + numeroBambini ;
		return stampa;  
	}
	
}

