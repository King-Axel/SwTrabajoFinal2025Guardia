package org.example.app.interfaces;

import org.example.domain.AfiliacionObraSocial;

public interface ServicioObraSocial {

    AfiliacionObraSocial obtenerObraSocialPorCodigo(Long idObraSocial);

    boolean estaAfiliado(AfiliacionObraSocial obraSocial, String numeroAfiliado);
}
