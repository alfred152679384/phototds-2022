package umu.tds.fotos;

import java.util.Vector;

public enum ComponenteCargadorFotos {
	INSTANCE;
	private Vector<FotosListener> fListener;	
	private  Fotos fotos;
	
	private ComponenteCargadorFotos() {
		fListener = new Vector<>();
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
