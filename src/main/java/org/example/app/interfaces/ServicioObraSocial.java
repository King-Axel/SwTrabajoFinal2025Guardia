package org.example.app.interfaces;

import org.example.domain.Afiliado;
import java.util.UUID;

public interface ServicioObraSocial {

    Afiliado obtenerObraSocialPorId(UUID idObraSocial);

    boolean estaAfiliado(Afiliado obraSocial);
}
