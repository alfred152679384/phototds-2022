package umu.tds.fotos;

import java.util.EventListener;

public interface FotosListener extends EventListener {
	public void notificaNuevasFotos(FotosEvent ev);
}
