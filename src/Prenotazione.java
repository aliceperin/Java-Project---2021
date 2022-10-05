import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Comparable;

//Classe che descrive la prenotazione basica che comprende solo l'affitto

public class Prenotazione implements Serializable, Comparable <Prenotazione> {
	
	//Nome della persona che prenota
	private String nome; 
	//Data scelta per l'affitto del locale
	private Date data; 
	
	//Scelta del formato data
	public DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	//Costruttore
	public Prenotazione (String nome, Date data) {
		this.nome = nome; 
		this.data = data;
	}
	//Metodi "getter" che ritornano il nome e la data
	public String getNome () {
		return this.nome;
	}
	
	public Date getData() {
		return this.data;
	}
	
	/* Override del metodo toString, per convertire l'indirizzo di memoria della prenotazione, in una stringa .
	Viene usato nel tasto cerca prenotazione per data*/
	@Override
	public String toString() {
		String stampa = "Prenotazione solo affitto del " + formatter.format(data) + " a nome " + nome + ". "; 
		return stampa; 
	}
	
	//Override del metodo compareTo, permette di comparare le date nelle strutture dati per metterle in ordine cronologico
	@Override
	public int compareTo(Prenotazione t) {
            return this.data.compareTo(t.getData());
        }
	
}
