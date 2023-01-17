package com.epam.facultative.utils.pdf_creator;

import com.epam.facultative.dto.CourseDTO;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static java.util.ResourceBundle.getBundle;

public class PdfCreator {
    private static final Logger logger = LoggerFactory.getLogger(PdfCreator.class);

    private static final String COURSE_TITLE = "all.course";
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final String[] COURSE_CELLS =
            new String[]{"title", "duration", "start.date", "amount.students", "category", "status", "teacher"};

    public ByteArrayOutputStream createCoursesPdf(List<CourseDTO> courses, String locale) {
        ResourceBundle currentResourceBundle = getCurrentResourceBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getPdfDocument(output);
        document.add(getTableTitle(currentResourceBundle.getString(COURSE_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getCoursesTable(courses, currentResourceBundle));
        document.close();
        return output;
    }

    private Table getCoursesTable(List<CourseDTO> courses, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{7, 3, 3, 2, 2, 2, 5});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, COURSE_CELLS, resourceBundle);
        addCoursesTableRows(table, courses);
        return table;
    }

    private void addTableHeader(Table table, String[] cells, ResourceBundle resourceBundle) {
        Stream.of(cells)
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GREY);
                    header.setBorder(new SolidBorder(1));
                    header.add(new Paragraph(resourceBundle.getString(columnTitle)));
                    table.addCell(header);
                });
    }

    private void addCoursesTableRows(Table table, List<CourseDTO> courses) {
        courses.forEach(course ->
                {
                    table.addCell(course.getTitle());
                    table.addCell(String.valueOf(course.getDuration()));
                    table.addCell(String.valueOf(course.getStartDate()));
                    table.addCell(String.valueOf(course.getAmountStudents()));
                    table.addCell(course.getCategory().getTitle());
                    table.addCell(String.valueOf(course.getStatus()));
                    if (course.getTeacher() != null) {
                        table.addCell(course.getTeacher().getName() + " " + course.getTeacher().getSurname());
                    } else {
                        table.addCell("No teacher");
                    }
                }
        );
    }

    private static Paragraph getTableTitle(String tableTitle) {
        return new Paragraph(new Text(tableTitle))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Document getPdfDocument(ByteArrayOutputStream output) {
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont font = getPdfFont();
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private PdfFont getPdfFont() {
        try {
            return PdfFontFactory.createFont("C:\\epam\\facultative\\pdf\\arial.ttf");
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private ResourceBundle getCurrentResourceBundle(String locale) {
        String resources = "resources";
//        if (locale == null) {
//            return getBundle(resources, new Locale("en"));
//        }
        if (locale.contains("_")) {
            String[] splitLocale = locale.split("_");
            return getBundle(resources, new Locale(splitLocale[0], splitLocale[1]));
        } else {
            return getBundle(resources, new Locale(locale));
        }
    }
}
