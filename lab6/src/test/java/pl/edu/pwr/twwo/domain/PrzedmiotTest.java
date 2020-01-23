package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class PrzedmiotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Przedmiot.class);
        Przedmiot przedmiot1 = new Przedmiot();
        przedmiot1.setId(1L);
        Przedmiot przedmiot2 = new Przedmiot();
        przedmiot2.setId(przedmiot1.getId());
        assertThat(przedmiot1).isEqualTo(przedmiot2);
        przedmiot2.setId(2L);
        assertThat(przedmiot1).isNotEqualTo(przedmiot2);
        przedmiot1.setId(null);
        assertThat(przedmiot1).isNotEqualTo(przedmiot2);
    }
}
