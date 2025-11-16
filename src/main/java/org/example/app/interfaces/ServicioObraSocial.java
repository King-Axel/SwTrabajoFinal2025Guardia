package org.example.app.interfaces;

import org.example.domain.Afiliado;
import org.example.domain.ObraSocial;

public interface ServicioObraSocial {

    ObraSocial obtenerObraSocialPorNombre(String nombreObraSocial);

    boolean estaAfiliado(Afiliado afiliado);
}
