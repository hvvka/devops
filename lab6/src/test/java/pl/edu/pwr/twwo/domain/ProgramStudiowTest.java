package pl.edu.pwr.twwo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.pwr.twwo.web.rest.TestUtil;

public class ProgramStudiowTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramStudiow.class);
        ProgramStudiow programStudiow1 = new ProgramStudiow();
        programStudiow1.setId(1L);
        ProgramStudiow programStudiow2 = new ProgramStudiow();
        programStudiow2.setId(programStudiow1.getId());
        assertThat(programStudiow1).isEqualTo(programStudiow2);
        programStudiow2.setId(2L);
        assertThat(programStudiow1).isNotEqualTo(programStudiow2);
        programStudiow1.setId(null);
        assertThat(programStudiow1).isNotEqualTo(programStudiow2);
    }
}
