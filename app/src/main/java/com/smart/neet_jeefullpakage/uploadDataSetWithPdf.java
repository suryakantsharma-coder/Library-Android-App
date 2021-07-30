package com.smart.neet_jeefullpakage;

public class uploadDataSetWithPdf {
    String Title, Description, Author, Date,Thumbnail,PdfUrl;

    public uploadDataSetWithPdf() {
    }

    public uploadDataSetWithPdf(String title, String description, String author, String date, String thumbnail, String pdfUrl) {
        Title = title;
        Description = description;
        Author = author;
        Date = date;
        Thumbnail = thumbnail;
        PdfUrl = pdfUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getPdfUrl() {
        return PdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        PdfUrl = pdfUrl;
    }
}
