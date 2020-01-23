package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class ZajecieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zajecie.class);
        Zajecie zajecie1 = new Zajecie();
        zajecie1.setId(1L);
        Zajecie zajecie2 = new Zajecie();
        zajecie2.setId(zajecie1.getId());
        assertThat(zajecie1).isEqualTo(zajecie2);
        zajecie2.setId(2L);
        assertThat(zajecie1).isNotEqualTo(zajecie2);
        zajecie1.setId(null);
        assertThat(zajecie1).isNotEqualTo(zajecie2);
    }
}
