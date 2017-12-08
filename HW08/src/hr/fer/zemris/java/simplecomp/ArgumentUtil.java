package hr.fer.zemris.java.simplecomp;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * <p>Pomoćni razred sa statičkim metodama koje služe za provjeravanje
 * argumenata određenih instrukcija.</p>
 * 
 * <p>Metoda {@linkplain #checkArguments(List, int)} provjeravanja je li broj
 * aragumenata u instrukciji ispravan, ako nije baca se {@link IllegalArgumentException}.
 * Metoda {@linkplain #checkRegisters(List, int)} provjerava je li argument registar
 * ili je li zadan kao indirektna adresa, ako jedno od tih uvjeta nije ispunjeno, baca
 * se {@link IllegalArgumentException}.</p>
 *  
 * @author Vjeco
 */
public class ArgumentUtil {
	
	/**
	 * Metoda za provjeravanje ispravnosti broja argumenata u određenoj instrukciji.
	 * 
	 * @param arguments argumenti instrukcije
	 * @param n očekivan broj argumenata
	 * @throws IllegalArgumentException ako broj argumenata nije ispravan
	 */
	public static void checkArguments(List<InstructionArgument> arguments, int n){
		if (arguments.size() != n) {
			throw new IllegalArgumentException(
				"Expected " + n + " argument" + (n == 1 ? "." : "s." )
			);
		}
	}
	
	/**
	 * Metoda za provjeravanje registara. Ako argument nije registar ili je zadan
	 * kao indikretna adresa, a nije trebao biti baca se iznimka.
	 * 
	 * @param arguments argumenti instrukcije
	 * @param n broj aragumenta
	 * @throws IllegalArgumentException 
	 * 				ako argument nije registar ili je zadan kao indirektna adresa,
	 * 				a nije trebao biti 					
	 */
	public static void checkRegisters(List<InstructionArgument> arguments, int n){
		for (int i = 0; i < n; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
					"Type mismatch for argument " + i + "!"
				);
			}
		}
	}
}
