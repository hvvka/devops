package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class KartaPrzedmiotuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KartaPrzedmiotu.class);
        KartaPrzedmiotu kartaPrzedmiotu1 = new KartaPrzedmiotu();
        kartaPrzedmiotu1.setId(1L);
        KartaPrzedmiotu kartaPrzedmiotu2 = new KartaPrzedmiotu();
        kartaPrzedmiotu2.setId(kartaPrzedmiotu1.getId());
        assertThat(kartaPrzedmiotu1).isEqualTo(kartaPrzedmiotu2);
        kartaPrzedmiotu2.setId(2L);
        assertThat(kartaPrzedmiotu1).isNotEqualTo(kartaPrzedmiotu2);
        kartaPrzedmiotu1.setId(null);
        assertThat(kartaPrzedmiotu1).isNotEqualTo(kartaPrzedmiotu2);
    }
}
