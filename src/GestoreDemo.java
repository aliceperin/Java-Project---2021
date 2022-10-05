//Classe contenente il Main e l'interfaccia testuale del menu 

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.lang.InterruptedException;
import java.lang.IndexOutOfBoundsException;

public class GestoreDemo {

	//Liste che contengono tutte le prenotazioni: affitto - catering - animazione
	static ArrayList<Prenotazione> prenotazioniAffitto = new ArrayList<Prenotazione>();
	static ArrayList<Catering> prenotazioniCatering = new ArrayList <Catering> ();
	static ArrayList<Animazione>  prenotazioniAnimazione = new ArrayList <Animazione> ();
	
	//Hashmap per la gestione dell'oggetto data
	static HashMap<Date, Prenotazione> prenotazioneData = new HashMap<Date, Prenotazione>();

	//MAIN
	public static void main(String[] args) throws ParseException, InterruptedException {

		Scanner input = new Scanner(System.in);

		//Interfaccia testuale - Menu
		
		/*Variabili booleane che vengono usate come guardia del while. Servono per attivare o disattivare 
		i menu*/
		boolean primoMenuDisattivo = false;
		boolean menuDisattivo = false;
		
		/*Inizializza il primo menu, in cui viene chiesto il primo passo per procedere.
		Si può iniziare con delle prenotazioni nuove (se è la prima volta che si usa il programma)
		oppure si può caricare una lista di prenotazioni già esistenti*/
		
		while(!primoMenuDisattivo) {
			try {
		
				System.out.println("MENU INIZIALE");
				System.out.println("-----------------");
				System.out.println("Scegli un'operazione con il relativo tasto:");
				System.out.println(" ");
				System.out.println("[1] Inserisci una nuova prenotazione");
				System.out.println("[2] Carica un file prenotazioni esistente");
				System.out.println("[0] Esci");
		
		String scelta = input.nextLine();
		
		switch (scelta) {
		case "1":
			aggiungiPrenotazione();
			primoMenuDisattivo=true;
			break;
		case "2":
			upload();
			primoMenuDisattivo=true;
			break;
			//Termina il programma
		case "0":
			primoMenuDisattivo=true;
			menuDisattivo=true;
			System.out.println("Fine programma.");
			break;
		default:
			throw new InputMismatchException();
		}
		
			} catch (InputMismatchException e) {
				System.out.println("Scelta non valida, premi un tasto per continuare");
				input.nextLine();
			}
		}
		
		
		/*Inizializza il secondo menu, che si attiva dopo la prima operazione del primo menu.
		 Contiene tutte le funzioni per la gestione del locale*/
		while (!menuDisattivo) {
			try {

				System.out.println("MENU PRENOTAZIONI");
				System.out.println("* * * * * * * * * *");
				System.out.println("Scegli un'operazione con il relativo tasto:");
				System.out.println(" ");
				System.out.println("[1] Aggiungi una prenotazione");
				System.out.println("[2] Annulla una prenotazione");
				System.out.println("[3] Visualizza lista prenotazioni");
				System.out.println("[4] Carica/Scarica lista prenotazioni");
				System.out.println("[5] Cerca prenotazione");
				System.out.println("[0] Esci dal programma.");
				
				
				String scelta = input.nextLine();

				switch (scelta) {

				case "1":
					aggiungiPrenotazione();
					break;
				case "2":
					eliminaPrenotazione();
					break;
				case "3":
					visualizzaPrenotazioni(); 
					break;
				case "4":
					salvaScarica();
					break;
				case "5":
					cercaPrenotazione();
					break;
					
					//Termina il programma
				case "0": 
					menuDisattivo = true;
					System.out.println(" ");
					System.out.println("Fine programma.");
					break;
				default:
					System.out.println(" ");
					throw new InputMismatchException();
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Scelta non valida, premi un tasto per continuare");
				input.nextLine();
			} catch (ParseException e) {
				System.out.println("Errore formato data");
			}

		}
	}
	
	
	// *** Metodi relativi ai tasti del Menu principale ***

	//Tasto [1]
	//Visualizza la prima data disponibile dal giorno corrente 
	public static long dataDisp(Date data) {
		Scanner input = new Scanner(System.in);
		
		//Inizializza formato data corretto da inserire
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			data = formatter.parse(formatter.format(data));
		} catch (ParseException e1) {
			System.out.println(" ");
			System.out.println("Errore formato data");
		}
		long start = data.getTime(); 
		long giorni = 0;
		long d = 0;
		Date datanuova = new Date(start + giorni);
			
		/* Ciclo che controlla che il formato della data inserita sia corretto e 
		 	crea una nuova chiave dell'hashmap inserendoci la nuova data */
		do {
		d = start + giorni;
		datanuova = new Date(d);
		try {
			datanuova = formatter.parse(formatter.format(datanuova)); 
		} catch (ParseException e) {
			System.out.println(" ");
			System.out.println("Errore formato data");
		}

		giorni += 1000 * 60 * 60 * 24;
		} while(prenotazioneData.containsKey(datanuova));

		return d;

	}
	/* Metodo che chiede all'utente i dati della prenotazione e li aggiunge nella lista corretta
	   in base al tipo di prenotazione scelta */
	public static void aggiungiPrenotazione() throws ParseException {
		Scanner input = new Scanner(System.in);

		System.out.println("* Aggiungi prenotazione *");
		System.out.println(" ");

		System.out.println("Inserisci nome cliente:");
		String nome = input.nextLine();
		System.out.println(" ");

		//Controlla che il nome venga inserito
		if (nome.isEmpty())
			while (nome.isEmpty()) {
				System.out.println(" ");
				System.out.println("Il nome non è stato inserito, riprova");
				System.out.println(" ");
				nome = input.nextLine();

			}
		Date data = new Date();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		long data1 = 0;
		
		//Millisecondi in un giorno da sottrarre alla data di oggi per avere la data di ieri
		long millisecondi = 1000 * 60 * 60 * 24;
		long ieri = data.getTime() - millisecondi;
		
		do {
			System.out.println("Prima data disponibile = " + formatter.format(new Date(dataDisp(new Date()))));
			System.out.println(" ");
			System.out.println("Inserisci data in cui si desidera affittare il locale in formato gg/mm/aaaa:");
			System.out.println(" ");
			
			data = formatter.parse(input.next());
			System.out.println(" ");
			
			//Controlla che la data inserita sia tra oggi e il 31/12/2022
			try {
				if(data.before(new Date(ieri)) || data.after(formatter.parse("31/12/2022")))
					throw new InputMismatchException();
			} catch (InputMismatchException e) {
				System.out.println("Inserisci una data tra oggi e il 31/12/2022");
			}
		}while(data.before(new Date(ieri)) || data.after(formatter.parse("31/12/2022")));
		
		
		//Se la data e' occupata, mi ripropone la prima data disponibile 
		while (prenotazioneData.containsKey(data)) {
			data1 = dataDisp(data);
			data = new Date(data1);
			System.out.println("La data inserita non e' disponibile. Prima data disponibile dopo quella appena richiesta = " + formatter.format(data));
			System.out.println("Inserisci data in cui si desidera affittare il locale in formato gg/mm/aaaa:");
			data = formatter.parse(input.next());
		}
		
		System.out.println("Scegli il tipo di prenotazione:");
		tipoPrenotazione(nome, data);
		
	}

	//Metodo che permette all'utente di scegliere il tipo di prenotazione desiderata.
	public static void tipoPrenotazione(String nome, Date data) throws ParseException {

		try {

			Scanner input = new Scanner(System.in);

			System.out.println("[1] Affitto semplice");
			System.out.println("[2] Affitto con servizio catering");
			System.out.println("[3] Affitto locale con catering e servizio animazione");

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String scelta = input.nextLine();

			switch (scelta) {

			case "1":
				System.out.println(" ");
				Prenotazione affitto = new Prenotazione(nome, data);
				prenotazioneData.put(data, affitto);
				prenotazioniAffitto.add(affitto);
				System.out.println(" ");
				System.out.println("- Prenotazione solo affitto a nome " + affitto.getNome());
				System.out.println("- Per la data " + formatter.format(affitto.getData()));
				System.out.println("CONFERMATA");
				System.out.println(" ");
				System.out.println(" ");
				break;
			case "2":
				System.out.println(" ");
				System.out.println("Inserire numero di bambini.");
				int numeroBambini = input.nextInt();
				Catering catering = new Catering(nome, data, numeroBambini);
				prenotazioniCatering.add(catering);
				prenotazioneData.put(data, catering);
				System.out.println(" ");
				System.out.println("- Prenotazione con catering a nome " + catering.getNome());
				System.out.println("- Per la data: " + formatter.format(catering.getData()));
				System.out.println("- Numero bambini: " + catering.getNumeroBambini());
				System.out.println("CONFERMATA");
				System.out.println(" ");
				System.out.println(" ");
				break;
			case "3":
				System.out.println("Inserire numero di bambini.");
				int numeroBambiniA = input.nextInt();
				System.out.println(" ");
				System.out.println("Inserire il tipo di animazione:");
				Animazione animazione = new Animazione(nome, data, numeroBambiniA, animazione());
				prenotazioniAnimazione.add(animazione);
				prenotazioneData.put(data, animazione);
				System.out.println(" ");
				System.out.println("- Prenotazione con catering e animazione a nome " + animazione.getNome());
				System.out.println("- Per la data: " + formatter.format(animazione.getData()));
				System.out.println("- Numero bambini: " + animazione.getNumeroBambini());
				System.out.println("- Tipo animazione scelta: " + animazione.getTipoAnimazione());
				System.out.println("CONFERMATA");
				System.out.println(" ");
				System.out.println(" ");
				break;
			default:
				System.out.println(" ");
				throw new InputMismatchException();
			}
			
		} catch (InputMismatchException e) {
			System.out.println("Prenotazione non valida, riprova");
		}
		
	}
	
	/*Se l'utente sceglie il tipo "animazione", si entra in un altro menu che mostra i tipi di
	  animazione disponibile */
	public static String animazione() {

		try {

			Scanner input = new Scanner(System.in);

			System.out.println("Scegli tra i tipi di animazione disponibili:");
			System.out.println("[1] Organizzazione di giochi");
			System.out.println("[2] Spettacolo di magia");
			System.out.println("[3] Spettacolo di burattini");

			String tipoAnim = null;

			String scelta = input.nextLine();
			switch (scelta) {

			case "1":
				tipoAnim = "Organizzazione di giochi";
				break;
			case "2":
				tipoAnim = "Spettacolo di magia";
				break;
			case "3":
				tipoAnim = "Spettacolo di burattini";
				break;
			default:
				System.out.println(" ");
				
				throw new InputMismatchException();
				
			}

			return tipoAnim;
			
		}
		
		catch (InputMismatchException e) {
			System.out.println("Prenotazione del tipo di animazione non valida, riprova");
			
		}
		
			return null;
			
	}
	

	// Tasto [2]
	// Elimina una prenotazione inserendo il numero corrispondente dall'elenco
	public static void eliminaPrenotazione() throws ParseException {
		
		try {
			
		Scanner input = new Scanner(System.in);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("* Annulla una prenotazione *");
		System.out.println(" ");
		
		System.out.println("Scegli il tipo di prenotazione da annullare:");
		System.out.println("[1] Affitti semplici");
		System.out.println("[2] Catering");
		System.out.println("[3] Animazione");
		
		String scelta = input.nextLine();
		
		switch (scelta) {
		//Itera la lista scelta 
		case "1":
			
			if (prenotazioniAffitto.isEmpty()) {
				System.out.println("Nessuna prenotazione di questo tipo è presente a sistema.");
				System.out.println(" ");
			} else {
			//Ordina la lista per data 
			Collections.sort(prenotazioniAffitto);
			int numElenco1 = 1;
			
			//Stampa l'elenco delle prenotazioni della lista
			for (Prenotazione slot: prenotazioniAffitto) {
				System.out.println(" ");
				System.out.println(" [" + numElenco1 + "] " + slot);
				numElenco1++;
			}
			//Inizializza a null il nuovo oggetto Prenotazione che è visibile anche nell'if dove viene richiamato 
			Prenotazione prenotazione = null;
			boolean uguali1 = false;
			int numPrenotazioneDaCancellare1;
			int posizione1 = 0;
			
			System.out.println(" ");
			System.out.println("   Scegli quale prenotazione da annullare. \n Inserisci il corrispettivo numero dell'elenco:");
			System.out.println(" ");
			numPrenotazioneDaCancellare1 = input.nextInt();

			
			
			//Reitera l'array completo 
			for (Prenotazione slot : prenotazioniAffitto) {
				/*Se il numero che inserisce l'utente è uguale a quello della prenotazione (+1)
				passa l'indice della prenotazione scelta ad una variabile "posizione" e attiva il booleano "uguali"
				per passare al prossimo if.*/
				if (numPrenotazioneDaCancellare1 == prenotazioniAffitto.indexOf(slot)+1) {
					posizione1 = prenotazioniAffitto.indexOf(slot); 
					//passo il "contenuto" di slot alla variabile "prenotazione", che può essere vista anche fuori dal blocco
					prenotazione = slot;
					uguali1 = true;
				}
			} 
			
			if (uguali1) {
				//Rimuove la prenotazione dall'ArrayList alla posizione "posizione1"
				prenotazioniAffitto.remove(posizione1);
				//Richiama l'oggetto prenotazione per eliminarlo dall'HashMap che gestisce le date in modo che la data risulti di nuovo disponibile
				prenotazioneData.remove(prenotazione.getData());
				System.out.println(" ");
				System.out.println("Prenotazione annullata.");
				System.out.println(" ");
			}
			}
			break;
			
		case "2":
			if (prenotazioniAffitto.isEmpty()) {
				System.out.println("Nessuna prenotazione di questo tipo è presente a sistema.");
				System.out.println(" ");
			} else {
			Collections.sort(prenotazioniCatering);
			int numElenco2 = 1;
			
			for (Catering slot: prenotazioniCatering) {
				System.out.println(" ");
				System.out.println(" [" + numElenco2 + "] " + slot);
				numElenco2++;
			}
			
			Catering catering = null;
			boolean uguali2 = false;
			int numPrenotazioneDaCancellare2;
			int posizione2 = 0;
			
			System.out.println(" ");
			System.out.println("   Scegli quale prenotazione da annullare. \n Inserisci il corrispettivo numero dell'elenco:");
			System.out.println(" ");
			numPrenotazioneDaCancellare2 = input.nextInt();
			
			for (Catering slot : prenotazioniCatering) {
			
				if (numPrenotazioneDaCancellare2 == prenotazioniCatering.indexOf(slot)+1) {
					posizione2 = prenotazioniCatering.indexOf(slot); 
					//passo il "contenuto" di slot alla variabile "prenotazione", che può essere vista anche fuori dal blocco
					catering = slot;
					uguali2 = true;
				}
			}
			if (uguali2) {
				prenotazioniCatering.remove(posizione2);
				prenotazioneData.remove(catering.getData());
				System.out.println(" ");
				System.out.println("Prenotazione annullata.");
				System.out.println(" ");
			}
			}
			break;
			
		case "3":
			if (prenotazioniAffitto.isEmpty()) {
				System.out.println("Nessuna prenotazione di questo tipo è presente a sistema.");
				System.out.println(" ");
			} else {
			
			Collections.sort(prenotazioniAnimazione);
			int numElenco3 = 1;
			
			for (Catering slot: prenotazioniAnimazione) {
				System.out.println(" ");
				System.out.println(" [" + numElenco3 + "] " + slot);
				numElenco3++;
			}
			
			Animazione animazione = null;
			boolean uguali3 = false;
			int numPrenotazioneDaCancellare3;
			int posizione3 = 0;
			
			System.out.println(" ");
			System.out.println("   Scegli quale prenotazione da annullare. \n Inserisci il corrispettivo numero dell'elenco:");
			System.out.println(" ");
			numPrenotazioneDaCancellare3 = input.nextInt();
			
			for (Animazione slot : prenotazioniAnimazione) {
				
				if (numPrenotazioneDaCancellare3 == prenotazioniAnimazione.indexOf(slot)+1) {
					posizione3 = prenotazioniAnimazione.indexOf(slot); 

					animazione = slot;
					uguali3 = true;
				}
			}
			if (uguali3) {
				prenotazioniAnimazione.remove(posizione3);
				prenotazioneData.remove(animazione.getData());
				System.out.println(" ");
				System.out.println("Prenotazione annullata.");
				System.out.println(" ");
			}
			}
			break;
			
		default: 
			System.out.println(" ");
			
			throw new InputMismatchException ();
		}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(" ");
				System.out.println("Impossibile annullare prenotazione. Riprova.");
				System.out.println(" ");
		
			} catch (InputMismatchException e) {
				System.out.println(" ");
				System.out.println("Scelta non valida.");
				System.out.println(" "); }
		
			}

	// Tasto [3]
	/* Visualizza le prenotazioni di tipo affitto, catering e animazione in ordine
	di data */
	public static void visualizzaPrenotazioni() throws ParseException {

		try {

			Scanner input = new Scanner(System.in);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			System.out.println("* Lista prenotazioni - ordinata per data *");
			System.out.println(" ");
			System.out.println("[1] Lista prenotazioni affitto semplice");
			System.out.println("[2] Lista prenotazioni catering e animazione");
			System.out.println("[3] Lista prenotazioni animazione");
			System.out.println("[4] Lista di tutte le prenotazioni");

			String scelta = input.nextLine();

			switch (scelta) {

			case "1":
				System.out.println(" ");
				System.out.println("Lista Affitto semplice:");
				System.out.println("***");
				System.out.println(" ");
				int numElenco1 = 1;
				
				//Ordina l'Arraylist per data
				Collections.sort(prenotazioniAffitto); 
				
				for (Prenotazione affitto : prenotazioniAffitto) {

					System.out.println(" ");
					System.out.println(numElenco1 + ". " + affitto);
					numElenco1++;
					System.out.println(" ");
				} 
				break;
				
			// Visualizza le prenotazioni in ordine di data, che hanno almeno il catering quindi anche quelle con animazione
			case "2":
				System.out.println(" ");
				System.out.println("Lista Catering e Animazione:");
				System.out.println("***");
				System.out.println(" ");
				int numElenco2 = 1;
				
				//Unisce le due liste di prenotazione catering e animazione in un nuovo ArrayList
				ArrayList <Catering> listaCateringAnimazione = new ArrayList <Catering> ();
				listaCateringAnimazione.addAll(prenotazioniCatering);
				listaCateringAnimazione.addAll(prenotazioniAnimazione);
				
				//Ordina l'ArrayList per data
				Collections.sort(listaCateringAnimazione); 
				
				for (Catering prenotazione : listaCateringAnimazione) {
					System.out.println(" ");
					System.out.println(numElenco2 + ". " + prenotazione);
					numElenco2++;
					System.out.println(" ");
				}
				break;

			case "3":
				System.out.println(" ");
				System.out.println("Lista Animazione:");
				System.out.println("***");
				System.out.println(" ");
				int numElenco3 = 1;
				
				//Ordina l'ArrayList per data
				Collections.sort(prenotazioniAnimazione); 
				
				for (Animazione animazione : prenotazioniAnimazione) {
					System.out.println(" ");
					System.out.println(numElenco3 + ". " + animazione);
					numElenco3++;
					System.out.println(" ");
				}
				break;
				
			case "4":
				System.out.println(" ");
				System.out.println("Lista di tutte le prenotazioni:");
				System.out.println("***");
				System.out.println(" ");
				int numElenco4 = 1;
				
				ArrayList<Prenotazione> tutteLePrenotazioni = new ArrayList<Prenotazione> ();
				tutteLePrenotazioni.addAll(prenotazioniAffitto);
				tutteLePrenotazioni.addAll(prenotazioniCatering);
				tutteLePrenotazioni.addAll(prenotazioniAnimazione);
				
				//Ordina l'ArrayList per data
				Collections.sort(tutteLePrenotazioni);
				
				for (Prenotazione prenotazione : tutteLePrenotazioni) {
					System.out.println(" ");
					System.out.println(numElenco4 + ". " + prenotazione);
					numElenco4++;
					System.out.println(" ");
				}
				break;
				
			default:
				
				System.out.println(" ");
				
				throw new InputMismatchException ();
			}	

		} catch (InputMismatchException e) {
			System.out.println("Scegli una delle liste disponibili.");
			System.out.println(" ");
		}
	}
	
	// TASTO [4]
	/*Gestione della serializzazione del file. 
	 Carica un file esistente o lo scarica allo scopo di salvare le prenotazioni */
	
		public static void salvaScarica () throws InterruptedException {
			Scanner input = new Scanner(System.in);
			System.out.println("* Carica o scarica file. *");
			System.out.println(" ");
			System.out.println("[1] Carica file");
			System.out.println("[2] Scarica file");
			
			try {
			
			String scelta = input.nextLine();
			
			switch (scelta) {
			
			case "1" :
				System.out.println(" ");
				System.out.println("Il file in caricamento sovrascriverà i dati del programma. Continuare?");
				System.out.println("[S] Sì");
				System.out.println("[N] No");
				System.out.println(" ");
					
					String siOno = input.nextLine();
					switch (siOno.toLowerCase()) {
						
					case "s":
						upload();
						break;
					case "n":
						System.out.println(" ");
						break;
					default:
						throw new InputMismatchException();
					}
				break;
				
			case "2":
				download();
				break;
			default:
				throw new InputMismatchException();
				
			}

			} catch (InputMismatchException e) {
			System.out.println(" ");
			System.out.println("Errore, riprova");
			System.out.println(" ");
			}
			
		}
		
		//Metodo che carica un file salvato
		public static void upload () throws InterruptedException {
			
			try {
				
				FileInputStream caricaAffitto = new FileInputStream("fileprenotazioni.txt"); 
				ObjectInputStream s1 = new ObjectInputStream(caricaAffitto);
				prenotazioniAffitto = (ArrayList<Prenotazione>) s1.readObject();
				prenotazioniAnimazione = (ArrayList<Animazione>) s1.readObject();
				prenotazioniCatering = (ArrayList<Catering>) s1.readObject();
				prenotazioneData = (HashMap<Date, Prenotazione>) s1.readObject(); 
				caricaAffitto.close();
				s1.close();
				
				//Puntini di caricamento :-)
				String dots[] = { ".", ".", "."}; 
				for (int i = 0;  i < dots.length; i++) {
				    Thread.sleep(500);
				    System.out.println(dots[i]);
				    
				 }
				    
				System.out.println("File caricato correttamente.");
				System.out.println(" ");
				
			} 
			
			catch (IOException e) {e.printStackTrace(); System.out.println("File non trovato.");}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			
		}
			
		//Metodo che scarica le prenotazioni salvate nel programma in un file binario
		public static void download () throws InterruptedException {
			
			try {
				FileOutputStream salvaAffitto = new FileOutputStream ("fileprenotazioni.txt");
				ObjectOutputStream s1 = new ObjectOutputStream(salvaAffitto);
				s1.writeObject(prenotazioniAffitto);
				s1.writeObject(prenotazioniAnimazione);
				s1.writeObject(prenotazioniCatering);
				s1.writeObject(prenotazioneData);
				
				salvaAffitto.close();
				s1.close(); 
				
				String dots[] = { ".", ".", "."}; 
				for (int i = 0;  i < dots.length; i++) {
				    Thread.sleep(500);
				    System.out.println(dots[i]);
				 }
				System.out.println(" ");
				System.out.println("File salvato correttamente.");
				System.out.println(" ");
				
			} catch (IOException e) {
				System.out.println(" ");
				System.out.println("Download fallito. Riprova.");
				System.out.println(" ");
			}
			
		}	
	
	// TASTO [5]
	// Consente di cercare le prenotazioni sia per data che per nome (o una porzione di esso)
	public static void cercaPrenotazione() throws ParseException {

		try {

			Scanner input = new Scanner(System.in);

			System.out.println("*Cerca le prenotazioni: risalire alle prenotazione per *");
			System.out.println(" ");
			System.out.println("[1] Data ");
			System.out.println("[2] Nome del cliente");
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String scelta = input.nextLine();

			switch (scelta) {

			case "1":
				System.out.println("Inserisci la data in formato gg/mm/aaaa:");
				System.out.println(" ");
				Date data = formatter.parse(input.next());
				System.out.println(" ");
				if (prenotazioneData.get(data) != null) {System.out.println(prenotazioneData.get(data));}
				else {System.out.println("Nessuna prenotazione a questa data");}
				System.out.println(" ");
				break;

			case "2":
				System.out.println("Inserisci il nome del cliente:");
				System.out.println(" ");
				String nome = input.nextLine();
				System.out.println(" ");
				
				//Controlla se viene inserito un nome
				if (nome.isEmpty())
					while (nome.isEmpty()) {
					System.out.println("Il nome non è stato inserito, riprova");
					nome = input.nextLine();
					}
				
				//Cerca in tutte e 3 le liste se ci sono prenotazioni a nome *nome o substringa inserita*
				
					for(Prenotazione p : prenotazioniAffitto) {
						if(p.getNome().toLowerCase().contains(nome.toLowerCase())) { System.out.println(p); System.out.println(" ");} 
					}
					
					for(Catering c : prenotazioniCatering) {
						if(c.getNome().toLowerCase().contains(nome.toLowerCase())) { System.out.println(c); System.out.println(" ");}
					}
				
					for(Animazione a : prenotazioniAnimazione) {
						if(a.getNome().toLowerCase().contains(nome.toLowerCase())) { System.out.println(a); System.out.println(" ");}
					} 
					
				break;
				
			default:
				System.out.println(" ");
				throw new InputMismatchException();
			}

		} catch (InputMismatchException e) {
			System.out.println(" ");
			System.out.println("Inserire metodo valido per la ricerca, riprova");
			System.out.println(" ");
		} catch (ParseException e) {
			System.out.println(" ");
			System.out.println("Formato data non valido");
			System.out.println(" ");
		}

	}
}
	





