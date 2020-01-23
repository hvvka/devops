package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class DyscyplinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dyscyplina.class);
        Dyscyplina dyscyplina1 = new Dyscyplina();
        dyscyplina1.setId(1L);
        Dyscyplina dyscyplina2 = new Dyscyplina();
        dyscyplina2.setId(dyscyplina1.getId());
        assertThat(dyscyplina1).isEqualTo(dyscyplina2);
        dyscyplina2.setId(2L);
        assertThat(dyscyplina1).isNotEqualTo(dyscyplina2);
        dyscyplina1.setId(null);
        assertThat(dyscyplina1).isNotEqualTo(dyscyplina2);
    }
}
