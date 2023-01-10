package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DescuentoEdad implements Descuento {
	// Constantes
	private static final double DESCUENTO_JOVENES = 0.25;
	private static final double DESCUENTO_JUBILADOS = 0.5;
	private static final int JUBILADO = 65;
	private static final int JOVEN_INF = 15;
	private static final int JOVEN_SUP = 25;

	// Atributos
	private Usuario usuario;

	// Constructor
	public DescuentoEdad(Usuario u) {
		usuario = u;
	}

	// Función de la interfaz
	@Override
	public double getDescuento() {
		LocalDate fechNacUsuario = usuario.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long edadUsuario = ChronoUnit.YEARS.between(fechNacUsuario, LocalDate.now());
		if(edadUsuario > JOVEN_INF && edadUsuario < JOVEN_SUP)
			return DESCUENTO_JOVENES;
		if(edadUsuario > JUBILADO)
			return DESCUENTO_JUBILADOS;
		
		return 0;
	}

}
