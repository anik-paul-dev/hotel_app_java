package com.example.hotel_booking_app.utils;

import android.content.Context;
import android.os.Environment;
import com.example.hotel_booking_app.models.Booking;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;

public class PdfGenerator {

    public static File generateBookingPdf(Context context, Booking booking) {
        try {
            String fileName = "Booking_" + booking.getBookingId() + ".pdf";
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Booking Confirmation"));
            document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
            document.add(new Paragraph("User ID: " + booking.getUserId()));
            document.add(new Paragraph("Room ID: " + booking.getRoomId()));
            document.add(new Paragraph("Check-In: " + booking.getCheckIn()));
            document.add(new Paragraph("Check-Out: " + booking.getCheckOut()));
            document.add(new Paragraph("Adults: " + booking.getAdults()));
            document.add(new Paragraph("Children: " + booking.getChildren()));
            document.add(new Paragraph("Total Price: $" + booking.getTotalPrice()));
            document.add(new Paragraph("Status: " + booking.getStatus()));
            document.add(new Paragraph("Payment Status: " + booking.getPaymentStatus()));

            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}