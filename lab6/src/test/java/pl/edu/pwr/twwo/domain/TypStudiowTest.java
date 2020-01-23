package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class TypStudiowTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypStudiow.class);
        TypStudiow typStudiow1 = new TypStudiow();
        typStudiow1.setId(1L);
        TypStudiow typStudiow2 = new TypStudiow();
        typStudiow2.setId(typStudiow1.getId());
        assertThat(typStudiow1).isEqualTo(typStudiow2);
        typStudiow2.setId(2L);
        assertThat(typStudiow1).isNotEqualTo(typStudiow2);
        typStudiow1.setId(null);
        assertThat(typStudiow1).isNotEqualTo(typStudiow2);
    }
}
