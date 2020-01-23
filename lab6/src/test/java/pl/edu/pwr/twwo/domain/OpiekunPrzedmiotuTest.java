package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class OpiekunPrzedmiotuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpiekunPrzedmiotu.class);
        OpiekunPrzedmiotu opiekunPrzedmiotu1 = new OpiekunPrzedmiotu();
        opiekunPrzedmiotu1.setId(1L);
        OpiekunPrzedmiotu opiekunPrzedmiotu2 = new OpiekunPrzedmiotu();
        opiekunPrzedmiotu2.setId(opiekunPrzedmiotu1.getId());
        assertThat(opiekunPrzedmiotu1).isEqualTo(opiekunPrzedmiotu2);
        opiekunPrzedmiotu2.setId(2L);
        assertThat(opiekunPrzedmiotu1).isNotEqualTo(opiekunPrzedmiotu2);
        opiekunPrzedmiotu1.setId(null);
        assertThat(opiekunPrzedmiotu1).isNotEqualTo(opiekunPrzedmiotu2);
    }
}
