package hr.fer.zemris.java.simplecomp;

/**
 * <p>Pomoćni razred sa statičkim metodama za dobavljenje podataka iz
 * opisinika registra.</p>
 * 
 * <p>Moguće je dohvatiti 3 informacije: indeks registra, je li 
 * registar zadan kao indirektna adresa i offset indirektno zadane
 * adrese.</p>
 * 
 * @author Vjeco
 */
public class RegisterUtil {
	
	/**
	 * Metoda za dohvaćanje indeksa registra.
	 * 
	 * @param registerDescriptor opisnik registra
	 * @return indeks registra
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & 0xFF;
	}

	/**
	 * Metoda koja provjerava je li registar zadan kao indirektna adresa.
	 * 
	 * @param registerDescriptor opisnik registra
	 * @return <code>true</code> ako je registar zadan kao indirektna adresa
	 * 		   <code>false</code> inače
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (registerDescriptor >> 24 & 1) == 1;
	}

	/**
	 * Metoda za dohvaćanje offseta indirektno zadane adrese.
	 * 
	 * @param registerDescriptor opisnik registra
	 * @return offset indirektno zadane adrese
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		short offset = (short) ((short) (registerDescriptor >> 8) & 0xFFFF);
		return (int) offset;
	}
}
