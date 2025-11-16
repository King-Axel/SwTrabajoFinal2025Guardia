package org.example.app.interfaces;

import org.example.domain.Afiliado;

public interface ServicioObraSocial {

    Afiliado obtenerObraSocialPorId(Long idObraSocial);

    boolean estaAfiliado(Afiliado obraSocial);
}
