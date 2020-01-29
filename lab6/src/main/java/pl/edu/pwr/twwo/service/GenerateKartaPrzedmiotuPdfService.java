package pl.edu.pwr.twwo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.twwo.domain.*;
import pl.edu.pwr.twwo.repository.*;


import java.io.File;
import java.io.FileOutputStream;
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

    //TODO: rozbic na funkcje ofc
    public File generateKartaPrzedmiotuPdfForPrzedmiot(Optional<Przedmiot> przedmiot) throws NullPointerException {
        if (!przedmiot.isPresent())
            throw new NullPointerException("Przedmiot for PDF creation is not present.");

        KartaPrzedmiotu kartaPrzedmiotu = przedmiot.get().getKartaPrzedmiotu();
        Set<Zajecie> zajeciesSet = zajecieRepository.findAllByPrzedmiotId(przedmiot.get().getId());
        Set<EfektKsztalcenia> efektKsztalceniasSet = przedmiot.get().getEfektKsztalcenias();
        ProgramStudiow programStudiow = przedmiot.get().getProgramStudiow();
        TypStudiow typStudiow = programStudiow.getTypStudiow();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("sampleKartaPrzedmiotu.pdf"));

            document.open();
            BaseFont baseTimesRoman = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, false);
            Font font12normal = new Font(baseTimesRoman, 12, Font.NORMAL, BaseColor.BLACK);
            Font font12bold = new Font(baseTimesRoman, 12, Font.BOLD, BaseColor.BLACK);

            //1
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
            addRowTransparentHorizontalBorder(headerTable, font12bold, String.format("Grupa kursów: %s\n", "//TODO"));
            headerTable.getRow(0).getCells()[0].enableBorderSide(Rectangle.TOP);
            headerTable.getRow(headerTable.size() - 1).getCells()[0].enableBorderSide(Rectangle.BOTTOM);
            document.add(headerTable);

            //2
            PdfPTable formaPrzedmiotuTable = new PdfPTable(6);
            formaPrzedmiotuTable.setSpacingAfter(20f);

            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "Wykład");
            addRow(formaPrzedmiotuTable, font12normal, "Cwiczenia");
            addRow(formaPrzedmiotuTable, font12normal, "Laboratorium");
            addRow(formaPrzedmiotuTable, font12normal, "Projekt");
            addRow(formaPrzedmiotuTable, font12normal, "Seminarium");

            addRow(formaPrzedmiotuTable, font12normal, "Liczba godzin zajęć zorganizowanych w Uczelni (ZZU)");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");


            addRow(formaPrzedmiotuTable, font12normal, "Liczba godzin całkowitego nakładu pracy studenta (CNPS)");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");

            addRow(formaPrzedmiotuTable, font12normal, "Forma zaliczenia");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");

            addRow(formaPrzedmiotuTable, font12normal, "Liczba punktów ECTS");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");
            addRow(formaPrzedmiotuTable, font12normal, "");

            document.add(formaPrzedmiotuTable);


            //3
            PdfPTable pekTable = new PdfPTable(1);
            addRowTransparentHorizontalBorder(pekTable, font12bold, "PRZEDMIOTOWE EFEKTY KSZTALCENIA");

            for (EfektKsztalcenia ek : efektKsztalceniasSet) {
                addRowTransparentHorizontalBorder(pekTable, font12normal, String.format("%s %s", ek.getKodEfektu(), ek.getOpis()));
            }

            pekTable.getRow(0).getCells()[0].enableBorderSide(Rectangle.TOP);
            pekTable.getRow(pekTable.size() - 1).getCells()[0].enableBorderSide(Rectangle.BOTTOM);
            document.add(pekTable);

            document.close();
        } catch (Exception e) {
        }

        return new File("C:\\Users\\Zofia\\Downloads\\wyklad_01.pdf");
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

}
