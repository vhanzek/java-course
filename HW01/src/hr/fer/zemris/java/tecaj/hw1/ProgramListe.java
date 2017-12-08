package hr.fer.zemris.java.tecaj.hw1;

/**
 * The class representing list and examples with list.
 * @author Vjeco
 */
public class ProgramListe {
	
	/**
	 * Class which represents one node in a list.
	 */
	static class CvorListe {
		
		/** Next node */
		CvorListe sljedeci;
		
		/** The value of node */
		String podatak;
	}
	
	/**
	 * Method that starts the program.
	 *
	 * @param args Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
		}
	
	/**
	 * Returns size of the list.
	 *
	 * @param cvor head of the list
	 * @return size of the list
	 */
	private static int velicinaListe(CvorListe cvor) {
		if (cvor == null) return 0;
		else return 1 + velicinaListe(cvor.sljedeci);
	}
	
	/**
	 * Method for adding nodes at the end of the list.
	 *
	 * @param prvi head of the list
	 * @param podatak new node's value
	 * @return the head of the list
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe novi = new CvorListe();
		novi.podatak = podatak;
		novi.sljedeci = null;
		
		if(prvi == null){
			prvi = novi;
		} else {
			CvorListe temp = prvi;
			for(; temp.sljedeci != null; temp = temp.sljedeci);
			temp.sljedeci = novi;
		}		
		
		return prvi;
	}
	
	/**
	 * Method for printing node's values in the list on the standard output.
	 *
	 * @param cvor head of the list
	 */
	private static void ispisiListu(CvorListe cvor) {
		for(; cvor != null; cvor = cvor.sljedeci){
			System.out.println(cvor.podatak);
		}
		
	}
	
	/**
	 * Method for sorting the list. Used algorithm is bubble sort.
	 *
	 * @param cvor the head of the list
	 * @return the head of the list (first node)
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		boolean sorted;
		
		if (velicinaListe(cvor) < 2) return cvor;
		do{
			sorted = true;
			for (CvorListe pom = cvor; pom != null; pom = pom.sljedeci){
				if (pom.sljedeci != null && pom.sljedeci.podatak.compareTo(pom.podatak) > 0){
					String temp;
					temp = pom.podatak;
					pom.podatak = pom.sljedeci.podatak;
					pom.sljedeci.podatak = temp;
					
					sorted = false;
				}
			}
		} while (sorted == false);
		
		return cvor;
	}
}
