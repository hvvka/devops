package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class EfektKsztalceniaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EfektKsztalcenia.class);
        EfektKsztalcenia efektKsztalcenia1 = new EfektKsztalcenia();
        efektKsztalcenia1.setId(1L);
        EfektKsztalcenia efektKsztalcenia2 = new EfektKsztalcenia();
        efektKsztalcenia2.setId(efektKsztalcenia1.getId());
        assertThat(efektKsztalcenia1).isEqualTo(efektKsztalcenia2);
        efektKsztalcenia2.setId(2L);
        assertThat(efektKsztalcenia1).isNotEqualTo(efektKsztalcenia2);
        efektKsztalcenia1.setId(null);
        assertThat(efektKsztalcenia1).isNotEqualTo(efektKsztalcenia2);
    }
}
