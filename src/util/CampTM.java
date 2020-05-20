package util;

public class CampTM {

    private String campID;
    private String campName;
    private String campCity;
    private String campDistrict;
    private int campCapacity;
    private String campHeadName;
    private String headContact;
    private String campContact;
    private String campEmail;

    public CampTM() {
    }

    public CampTM(String campID, String campName, String campCity, String campDistrict, int campCapacity, String campHeadName, String headContact, String campContact, String campEmail) {
        this.campID = campID;
        this.campName = campName;
        this.campCity = campCity;
        this.campDistrict = campDistrict;
        this.campCapacity = campCapacity;
        this.campHeadName = campHeadName;
        this.headContact = headContact;
        this.campContact = campContact;
        this.campEmail = campEmail;
    }

    public String getCampID() {
        return campID;
    }

    public void setCampID(String campID) {
        this.campID = campID;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampCity() {
        return campCity;
    }

    public void setCampCity(String campCity) {
        this.campCity = campCity;
    }

    public String getCampDistrict() {
        return campDistrict;
    }

    public void setCampDistrict(String campDistrict) {
        this.campDistrict = campDistrict;
    }

    public int getCampCapacity() {
        return campCapacity;
    }

    public void setCampCapacity(int campCapacity) {
        this.campCapacity = campCapacity;
    }

    public String getCampHeadName() {
        return campHeadName;
    }

    public void setCampHeadName(String campHeadName) {
        this.campHeadName = campHeadName;
    }

    public String getHeadContact() {
        return headContact;
    }

    public void setHeadContact(String headContact) {
        this.headContact = headContact;
    }

    public String getCampContact() {
        return campContact;
    }

    public void setCampContact(String campContact) {
        this.campContact = campContact;
    }

    public String getCampEmail() {
        return campEmail;
    }

    public void setCampEmail(String campEmail) {
        this.campEmail = campEmail;
    }

    @Override
    public String toString() {
        return campName;
    }
}
