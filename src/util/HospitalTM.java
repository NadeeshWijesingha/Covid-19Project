package util;

public class HospitalTM {

    private String hosID;
    private String hosName;
    private String hosCity;
    private String hosDistrict;
    private int hosCapacity;
    private String directorName;
    private String directorContact;
    private String hosContact;
    private String hosEmail;

    public HospitalTM() {
    }

    public HospitalTM(String hosID, String hosName, String hosCity, String hosDistrict, int hosCapacity, String directorName, String directorContact, String hosContact, String hosEmail) {
        this.hosID = hosID;
        this.hosName = hosName;
        this.hosCity = hosCity;
        this.hosDistrict = hosDistrict;
        this.hosCapacity = hosCapacity;
        this.directorName = directorName;
        this.directorContact = directorContact;
        this.hosContact = hosContact;
        this.hosEmail = hosEmail;
    }

    public String getHosID() {
        return hosID;
    }

    public void setHosID(String hosID) {
        this.hosID = hosID;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getHosCity() {
        return hosCity;
    }

    public void setHosCity(String hosCity) {
        this.hosCity = hosCity;
    }

    public String getHosDistrict() {
        return hosDistrict;
    }

    public void setHosDistrict(String hosDistrict) {
        this.hosDistrict = hosDistrict;
    }

    public int getHosCapacity() {
        return hosCapacity;
    }

    public void setHosCapacity(int hosCapacity) {
        this.hosCapacity = hosCapacity;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDirectorContact() {
        return directorContact;
    }

    public void setDirectorContact(String directorContact) {
        this.directorContact = directorContact;
    }

    public String getHosContact() {
        return hosContact;
    }

    public void setHosContact(String hosContact) {
        this.hosContact = hosContact;
    }

    public String getHosEmail() {
        return hosEmail;
    }

    public void setHosEmail(String hosEmail) {
        this.hosEmail = hosEmail;
    }

    @Override
    public String toString() {
        return hosName;
    }
}
