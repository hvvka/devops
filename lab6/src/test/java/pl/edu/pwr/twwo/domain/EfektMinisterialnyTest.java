package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class EfektMinisterialnyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EfektMinisterialny.class);
        EfektMinisterialny efektMinisterialny1 = new EfektMinisterialny();
        efektMinisterialny1.setId(1L);
        EfektMinisterialny efektMinisterialny2 = new EfektMinisterialny();
        efektMinisterialny2.setId(efektMinisterialny1.getId());
        assertThat(efektMinisterialny1).isEqualTo(efektMinisterialny2);
        efektMinisterialny2.setId(2L);
        assertThat(efektMinisterialny1).isNotEqualTo(efektMinisterialny2);
        efektMinisterialny1.setId(null);
        assertThat(efektMinisterialny1).isNotEqualTo(efektMinisterialny2);
    }
}
