import java.util.Date;

//Classe che descrive la prenotazione con animazione, che estende la classe Catering 
public class Animazione extends Catering {
	
	private String tipoAnimazione;
	
	//Costruttore
	public Animazione(String nome, Date data, int numeroBambini, String tipoAnimazione) {
		//Variabili ereditate dalla classe Prenotazione e Catering
		super(nome, data, numeroBambini);
		
		//Variabile privata della Classe Animazione
		this.tipoAnimazione = tipoAnimazione;
	}
	
	public String getTipoAnimazione () {
		return this.tipoAnimazione;
	}
	
	@Override
	public String toString() {
		String stampa = "Prenotazione con animazione del " + formatter.format(super.getData()) + " a nome " + super.getNome() + ". \n     Numero bambini: " + super.getNumeroBambini() + " \n     Tipo animazione scelto: " + tipoAnimazione;
		return stampa;
	}
}


