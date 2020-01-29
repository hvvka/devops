package pl.edu.pwr.twwo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.twwo.domain.*;
import pl.edu.pwr.twwo.repository.*;

import java.io.File;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class GenerateKartaPrzedmiotuPdfService {

    private final PrzedmiotRepository przedmiotRepository;
    private final KartaPrzedmiotuRepository kartaPrzedmiotuRepository;
    private final ZajecieRepository zajecieRepository;
    private final EfektKsztalceniaRepository efektKsztalceniaRepository;
    private final ProgramStudiowRepository programStudiowRepository;
    private final TypStudiowRepository typStudiowRepository;

    public GenerateKartaPrzedmiotuPdfService(PrzedmiotRepository przedmiotRepository,
                                             KartaPrzedmiotuRepository kartaPrzedmiotuRepository,
                                             ZajecieRepository zajecieRepository,
                                             EfektKsztalceniaRepository efektKsztalceniaRepository,
                                             ProgramStudiowRepository programStudiowRepository,
                                             TypStudiowRepository typStudiowRepository) {
        this.przedmiotRepository = przedmiotRepository;
        this.kartaPrzedmiotuRepository = kartaPrzedmiotuRepository;
        this.zajecieRepository = zajecieRepository;
        this.efektKsztalceniaRepository = efektKsztalceniaRepository;
        this.programStudiowRepository = programStudiowRepository;
        this.typStudiowRepository = typStudiowRepository;
    }

    public File generateKartaPrzedmiotuPdfForPrzedmiot(Optional<Przedmiot> przedmiot) throws NullPointerException {
        if (!przedmiot.isPresent())
            throw new NullPointerException("Przedmiot for PDF creation is not present.");

        KartaPrzedmiotu kartaPrzedmiotu = przedmiot.get().getKartaPrzedmiotu();
        Set<Zajecie> zajeciesSet = zajecieRepository.findAllByPrzedmiotId(przedmiot.get().getId());
        Set<EfektKsztalcenia> efektKsztalceniasSet = przedmiot.get().getEfektKsztalcenias();
        ProgramStudiow programStudiow = przedmiot.get().getProgramStudiow();
        TypStudiow typStudiow = programStudiow.getTypStudiow();

        System.out.println("jebać psy");
        System.out.println(przedmiot);
        System.out.println(kartaPrzedmiotu);
        System.out.println(zajeciesSet);
        System.out.println(efektKsztalceniasSet);
        System.out.println(programStudiow);
        System.out.println(typStudiow);

        return new File("C:\\Users\\Zofia\\Downloads\\wyklad_01.pdf");
    }

}
