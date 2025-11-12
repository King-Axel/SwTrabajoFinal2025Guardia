package org.example.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TesteableTest {
    @Test
    public void testearTest() {
        // Preparacion del test
        Float valor = 90f;

        // Ejecucion y Validacion del test
        assertThatCode(() -> new NoNegativoTestear(valor))
                .doesNotThrowAnyException();
    }

    class NoNegativoTestear {
        private Float valorNoNegativo;

        public NoNegativoTestear(Float valorNoNegativo) {
            if (valorNoNegativo == null || valorNoNegativo < 0f) throw new IllegalArgumentException();

            this.valorNoNegativo = valorNoNegativo;
        }
    }
}
