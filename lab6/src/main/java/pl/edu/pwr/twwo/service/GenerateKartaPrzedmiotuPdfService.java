package pl.edu.pwr.twwo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.twwo.domain.*;
import pl.edu.pwr.twwo.domain.enumeration.FormaPrzedmiotu;
import pl.edu.pwr.twwo.repository.*;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenerateKartaPrzedmiotuPdfService {
    private final Logger log = LoggerFactory.getLogger(GenerateKartaPrzedmiotuPdfService.class);

    private final ZajecieRepository zajecieRepository;

    private Font font12normal = null;
    private Font font12bold = null;

    public GenerateKartaPrzedmiotuPdfService(ZajecieRepository zajecieRepository) {
        this.zajecieRepository = zajecieRepository;
        createFonts();
    }

    public File generateKartaPrzedmiotuPdfForPrzedmiot(Optional<Przedmiot> przedmiot) throws NullPointerException {
        if (!przedmiot.isPresent())
            throw new NullPointerException("Przedmiot for PDF creation is not present.");
        File file = null;

        KartaPrzedmiotu kartaPrzedmiotu = przedmiot.get().getKartaPrzedmiotu();
        Set<Zajecie> zajeciesSet = zajecieRepository.findAllByPrzedmiotId(przedmiot.get().getId());
        Set<EfektKsztalcenia> efektKsztalceniasSet = przedmiot.get().getEfektKsztalcenias();
        ProgramStudiow programStudiow = przedmiot.get().getProgramStudiow();
        TypStudiow typStudiow = programStudiow.getTypStudiow();

        try {
            Document document = new Document();
            file = File.createTempFile(przedmiot.get().getNazwa(), ".tmp");
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            PdfPTable headerTable = createHeaderTable(programStudiow, kartaPrzedmiotu, typStudiow, zajeciesSet);
            document.add(headerTable);

            PdfPTable formaPrzedmiotuTable = createFormaPrzedmiotuTable(zajeciesSet);
            document.add(formaPrzedmiotuTable);

            PdfPTable pekTable = createPEKTable(efektKsztalceniasSet);
            document.add(pekTable);

            document.close();
        } catch (Exception e) {
            log.error("Failed creating PDF {}", e.toString());
        }

        return file;
    }


    private static void addRow(PdfPTable table, Font font, String text) {
        table.addCell(new PdfPCell(new Phrase(text, font)));
    }

    private static void addRowTransparentHorizontalBorder(PdfPTable table, Font font, String text) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(text, font));
        pdfPCell.disableBorderSide(Rectangle.TOP);
        pdfPCell.disableBorderSide(Rectangle.BOTTOM);
        table.addCell(pdfPCell);
    }

    private PdfPTable createHeaderTable(ProgramStudiow programStudiow, KartaPrzedmiotu kartaPrzedmiotu, TypStudiow typStudiow, Set<Zajecie> zajeciesSet) {
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setSpacingAfter(20f);

        addRowTransparentHorizontalBorder(headerTable, font12normal, String.format("WYDZIAŁ %s\n", programStudiow.getWydzial()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("KARTA PRZEDMIOTU %s\n", ""));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Nazwa w jezyku polskim: %s\n", kartaPrzedmiotu.getNazwa()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Nazwa w jezyku angielskim: %s\n", kartaPrzedmiotu.getNazwaAng()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Kierunek studiów (jeśli dotyczy): %s\n", programStudiow.getKierunek()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Specjalność (jeśli dotyczy): %s\n", programStudiow.getSpecjalnosc()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Stopień studiów i forma: %s\n", String.format("%s, %s", typStudiow.getStopienStudiow(), programStudiow.getFormaStudiow())));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Rodzaj przedmiotu: %s\n", kartaPrzedmiotu.getRodzajPrzedmiotu()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Kod przedmiotu: %s\n", kartaPrzedmiotu.getKodPrzedmiotu()));
        addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Grupa kursów: %s\n", zajeciesSet.size() <= 1 ? "NIE" : "TAK"));

        headerTable.getRow(0).getCells()[0].enableBorderSide(Rectangle.TOP);
        headerTable.getRow(headerTable.size() - 1).getCells()[0].enableBorderSide(Rectangle.BOTTOM);
        return headerTable;
    }

    private PdfPTable createFormaPrzedmiotuTable(Set<Zajecie> zajecieSet) {
        PdfPTable formaPrzedmiotuTable = new PdfPTable(6);
        formaPrzedmiotuTable.setSpacingAfter(20f);

        addRow(formaPrzedmiotuTable, font12normal, "");
        addRow(formaPrzedmiotuTable, font12normal, "Wykład");
        addRow(formaPrzedmiotuTable, font12normal, "Cwiczenia");
        addRow(formaPrzedmiotuTable, font12normal, "Laboratorium");
        addRow(formaPrzedmiotuTable, font12normal, "Projekt");
        addRow(formaPrzedmiotuTable, font12normal, "Seminarium");

        List<Zajecie> zajecieWyklad = zajecieSet.stream().filter(z -> z.getForma() == FormaPrzedmiotu.WYKLAD).collect(Collectors.toList());
        List<Zajecie> zajecieCwiczenia = zajecieSet.stream().filter(z -> z.getForma() == FormaPrzedmiotu.CWICZENIA).collect(Collectors.toList());
        List<Zajecie> zajecieLaboratorium = zajecieSet.stream().filter(z -> z.getForma() == FormaPrzedmiotu.LABORATORIUM).collect(Collectors.toList());
        List<Zajecie> zajecieProjekt = zajecieSet.stream().filter(z -> z.getForma() == FormaPrzedmiotu.PROJEKT).collect(Collectors.toList());
        List<Zajecie> zajecieSeminarium = zajecieSet.stream().filter(z -> z.getForma() == FormaPrzedmiotu.SEMINARIUM).collect(Collectors.toList());

        addRow(formaPrzedmiotuTable, font12normal, "Liczba godzin zajęć zorganizowanych w Uczelni (ZZU)");
        addRow(formaPrzedmiotuTable, font12normal, zajecieWyklad.stream().findFirst().isPresent() ? zajecieWyklad.stream().findFirst().get().getzZU().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieCwiczenia.stream().findFirst().isPresent() ? zajecieCwiczenia.stream().findFirst().get().getzZU().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieLaboratorium.stream().findFirst().isPresent() ? zajecieLaboratorium.stream().findFirst().get().getzZU().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieProjekt.stream().findFirst().isPresent() ? zajecieProjekt.stream().findFirst().get().getzZU().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieSeminarium.stream().findFirst().isPresent() ? zajecieSeminarium.stream().findFirst().get().getzZU().toString() : "");


        addRow(formaPrzedmiotuTable, font12normal, "Liczba godzin całkowitego nakładu pracy studenta (CNPS)");
        addRow(formaPrzedmiotuTable, font12normal, zajecieWyklad.stream().findFirst().isPresent() ? zajecieWyklad.stream().findFirst().get().getcNPS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieCwiczenia.stream().findFirst().isPresent() ? zajecieCwiczenia.stream().findFirst().get().getcNPS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieLaboratorium.stream().findFirst().isPresent() ? zajecieLaboratorium.stream().findFirst().get().getcNPS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieProjekt.stream().findFirst().isPresent() ? zajecieProjekt.stream().findFirst().get().getcNPS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieSeminarium.stream().findFirst().isPresent() ? zajecieSeminarium.stream().findFirst().get().getcNPS().toString() : "");

        addRow(formaPrzedmiotuTable, font12normal, "Forma zaliczenia");
        addRow(formaPrzedmiotuTable, font12normal, zajecieWyklad.stream().findFirst().isPresent() ? zajecieWyklad.stream().findFirst().get().getFormaZaliczenia().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieCwiczenia.stream().findFirst().isPresent() ? zajecieCwiczenia.stream().findFirst().get().getFormaZaliczenia().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieLaboratorium.stream().findFirst().isPresent() ? zajecieLaboratorium.stream().findFirst().get().getFormaZaliczenia().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieProjekt.stream().findFirst().isPresent() ? zajecieProjekt.stream().findFirst().get().getFormaZaliczenia().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieSeminarium.stream().findFirst().isPresent() ? zajecieSeminarium.stream().findFirst().get().getFormaZaliczenia().toString() : "");

        addRow(formaPrzedmiotuTable, font12normal, "Liczba punktów ECTS");
        addRow(formaPrzedmiotuTable, font12normal, zajecieWyklad.stream().findFirst().isPresent() ? zajecieWyklad.stream().findFirst().get().geteCTS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieCwiczenia.stream().findFirst().isPresent() ? zajecieCwiczenia.stream().findFirst().get().geteCTS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieLaboratorium.stream().findFirst().isPresent() ? zajecieLaboratorium.stream().findFirst().get().geteCTS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieProjekt.stream().findFirst().isPresent() ? zajecieProjekt.stream().findFirst().get().geteCTS().toString() : "");
        addRow(formaPrzedmiotuTable, font12normal, zajecieSeminarium.stream().findFirst().isPresent() ? zajecieSeminarium.stream().findFirst().get().geteCTS().toString() : "");

        return formaPrzedmiotuTable;
    }

    private PdfPTable createPEKTable(Set<EfektKsztalcenia> efektKsztalceniasSet) {
        PdfPTable pekTable = new PdfPTable(1);
        addRowTransparentHorizontalBorder(pekTable, font12bold, "PRZEDMIOTOWE EFEKTY KSZTAŁCENIA");

        for (EfektKsztalcenia ek : efektKsztalceniasSet) {
            addRowTransparentHorizontalBorder(pekTable, font12normal, String.format("%s %s", ek.getKodEfektu(), ek.getOpis()));
        }
        pekTable.getRow(0).getCells()[0].enableBorderSide(Rectangle.TOP);
        pekTable.getRow(pekTable.size() - 1).getCells()[0].enableBorderSide(Rectangle.BOTTOM);

        return pekTable;
    }

    private void createFonts() {
        try {
            BaseFont baseTimesRoman = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, false);
            font12normal = new Font(baseTimesRoman, 12, Font.NORMAL, BaseColor.BLACK);
            font12bold = new Font(baseTimesRoman, 12, Font.BOLD, BaseColor.BLACK);
        } catch (Exception e) {
            log.error("Failed creating fonts", e);
        }
    }
}
