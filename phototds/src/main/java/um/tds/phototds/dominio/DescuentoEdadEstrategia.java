package um.tds.phototds.dominio;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DescuentoEdadEstrategia implements DescuentoEstrategia {
	// Constantes
	private static final double DESCUENTO_JOVENES = 0.25;
	private static final double DESCUENTO_JUBILADOS = 0.5;
	private static final int JUBILADO = 65;
	private static final int JOVEN_INF = 15;
	private static final int JOVEN_SUP = 25;

	// Atributos
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy");
	private Usuario usuario;

	// Constructor
	public DescuentoEdadEstrategia(Usuario u) {
		usuario = u;
	}

	// Función de la interfaz
	@Override
	public double aplicarDescuento() {
		int nacimientoUsuario = Integer.parseInt(format.format(usuario.getFechaNacimiento()));
		int edadUsuario = LocalDate.now().getYear() - nacimientoUsuario;
		if(edadUsuario > JOVEN_INF && edadUsuario < JOVEN_SUP)
			return DESCUENTO_JOVENES;
		if(edadUsuario > JUBILADO)
			return DESCUENTO_JUBILADOS;
		return 0;
	}

}
