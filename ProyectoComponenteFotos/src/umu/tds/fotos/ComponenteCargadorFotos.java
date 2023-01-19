package umu.tds.fotos;

import java.io.File;
import java.util.Vector;

public enum ComponenteCargadorFotos implements IBuscadorFotos {
	INSTANCE;
	private Vector<FotosListener> fListener;	
	private  Fotos fotos;
	
	private ComponenteCargadorFotos() {
		fListener = new Vector<>();
	}
	
	@Override
	public Fotos nuevasFotos() {
		return this.fotos;
	}
	
	public synchronized void addFotoListener(FotosListener fl) {
		this.fListener.add(fl);
	}
	
	public synchronized void removeFotoListener(FotosListener fl) {
		this.fListener.remove(fl);
	}
	
	public void setArchivoFoto(String xmlFile) {
		this.fotos = CargadorFotos.cargadorFotos(xmlFile);
		FotosEvent ev = new FotosEvent(this, fotos);
		notificarEventoFotos(ev);
	}
	
	private void notificarEventoFotos(FotosEvent ev) {
		Vector<FotosListener> v;
		synchronized(this) {
			v = (Vector<FotosListener>)fListener.clone();
		}
		v.stream().forEach(f -> f.notificaNuevasFotos(ev));		
	}
	
	

}
