package um.tds.phototds.dao;

/** 
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO() {	}
	
	@Override //Implementa la clase de su padre
	public TDSUsuarioDAO getUsuarioDAO() {	
		return new TDSUsuarioDAO(); 
	}

	@Override
	public TDSPublicacionDAO getPublicacionDAO() {
		return new TDSPublicacionDAO();
	}

	

	
}
