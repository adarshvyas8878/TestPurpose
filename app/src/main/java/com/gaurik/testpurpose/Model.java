package com.gaurik.testpurpose;

public class Model {
    String EntryDate,SenderName,SenderImage,SubjectHeader,Message;

    public Model(String entryDate, String senderName, String senderImage, String subjectHeader, String message) {
        EntryDate = entryDate;
        SenderName = senderName;
        SenderImage = senderImage;
        SubjectHeader = subjectHeader;
        Message = message;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderImage() {
        return SenderImage;
    }

    public void setSenderImage(String senderImage) {
        SenderImage = senderImage;
    }

    public String getSubjectHeader() {
        return SubjectHeader;
    }

    public void setSubjectHeader(String subjectHeader) {
        SubjectHeader = subjectHeader;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
