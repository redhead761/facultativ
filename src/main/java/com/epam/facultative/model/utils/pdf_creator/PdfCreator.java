package com.epam.facultative.model.utils.pdf_creator;

import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static java.util.ResourceBundle.getBundle;

/**
 * Create required pdf docs with itext pdf library
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Slf4j
public class PdfCreator {
    private static final String COURSE_TITLE = "all.course";
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final String[] COURSE_CELLS =
            new String[]{"title", "duration", "start.date", "amount.students", "category", "status", "teacher"};
    private static final String PATH_FONT = "C:\\epam\\facultative\\pdf\\arial.ttf";

    /**
     * Creates pdf document with Courses' info. Creates resourceBundle to localize table fields
     *
     * @param courses - list of courses to be placed in the document
     * @param locale  - for localization purpose
     * @return outputStream to place in response
     */
    public ByteArrayOutputStream createCoursesPdf(List<CourseDTO> courses, String locale) {
        ResourceBundle currentResourceBundle = getCurrentResourceBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(output);
        Document document = getPdfDocument(writer);
        document.add(getTableTitle(currentResourceBundle.getString(COURSE_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getCoursesTable(courses, currentResourceBundle));
        document.close();
        return output;
    }

    /**
     * Creates pdf document with certificate info.
     *
     * @param studentDTO,course,grade - parameter for create certificate
     * @return outputStream to place in response
     */
    public ByteArrayOutputStream createCertificateForDownload(StudentDTO studentDTO, Course course, int grade) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(output);
        Document document = getPdfDocument(writer);
        setCertificate(document, studentDTO, course, grade);
        return output;
    }

    /**
     * Creates pdf document with certificate info.
     *
     * @param studentDTO,course,grade - parameter for create certificate
     * @return outputStream to place in response
     */
    public String createCertificateForSend(StudentDTO studentDTO, Course course, int grade) {
        String dest = "C:\\epam\\facultative\\pdf\\certificate_" + studentDTO.getLogin() + course.getTitle() + ".pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            Document document = getPdfDocument(writer);
            setCertificate(document, studentDTO, course, grade);
            return dest;
        } catch (FileNotFoundException e) {
            log.error(String.format("Couldn't create certificate. Cause: %s", e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    /**
     * Will create document with album orientation and font that supports cyrillic
     *
     * @param writer - to create Document based on writer
     * @return Document
     */
    private Document getPdfDocument(PdfWriter writer) {
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont font = getPdfFont();
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private void setCertificate(Document document, StudentDTO studentDTO, Course course, int grade) {
        document.add(new Paragraph(new Text("Certificate"))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(LINE_SEPARATOR);
        document.add(new Paragraph(new Text("Congratulation, " + studentDTO.getName() + " " + studentDTO.getSurname() + "!")));
        document.add(LINE_SEPARATOR);
        document.add(new Paragraph(new Text("You have successfully completed the course: " + course.getTitle())));
        document.add(LINE_SEPARATOR);
        document.add(new Paragraph(new Text("Your grade: " + grade)));
        document.add(LINE_SEPARATOR);
        document.add(new Paragraph(new Text("Hours listened: " + course.getDuration())));
        document.close();
    }

    private Table getCoursesTable(List<CourseDTO> courses, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{7, 3, 3, 2, 2, 2, 5});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, resourceBundle);
        addCoursesTableRows(table, courses);
        return table;
    }

    private void addTableHeader(Table table, ResourceBundle resourceBundle) {
        Stream.of(COURSE_CELLS)
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
                    table.addCell(course.getTeacher().getName() + " " + course.getTeacher().getSurname());
                }
        );
    }

    private Paragraph getTableTitle(String tableTitle) {
        return new Paragraph(new Text(tableTitle))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private PdfFont getPdfFont() {
        try {
            return PdfFontFactory.createFont(PATH_FONT);
        } catch (IOException e) {
            log.error(String.format("Couldn't get pdf font. Cause: %s", e.getMessage()));
            return null;
        }
    }

    private ResourceBundle getCurrentResourceBundle(String locale) {
        String resources = "resources";
        if (locale.contains("_")) {
            String[] splitLocale = locale.split("_");
            return getBundle(resources, new Locale(splitLocale[0], splitLocale[1]));
        } else {
            return getBundle(resources, new Locale(locale));
        }
    }
}
