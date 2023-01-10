package um.tds.phototds.dominio;

public class DescuentoMeGustas implements Descuento {
	//Constantes
	private static final int ME_GUSTAS_DESCUENTO = 20;
	private static final double DESCUENTO = 0.3;

	//Atributos
	private Usuario usuario;
	
	//Constructor
	public DescuentoMeGustas(Usuario u) {
		usuario = u;
	}

	//Función de la interfaz
	@Override
	public double getDescuento() {
		int meGustasUser = usuario.getPublicaciones().stream()
				.mapToInt(p -> p.getMeGustas())
				.sum();
		if (meGustasUser > ME_GUSTAS_DESCUENTO) {
			return DESCUENTO;
		}
		
		return 0;
	}

}
