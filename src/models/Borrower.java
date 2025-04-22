package models;

public class Borrower {
    private String id;
    private String name;
    private String contactDetails;

    public Borrower(String id, String name, String contactDetails) {
        this.id = id;
        this.name = name;
        this.contactDetails = contactDetails;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactDetails() { return contactDetails; }
    public void setContactDetails(String contactDetails) { this.contactDetails = contactDetails; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + contactDetails + ")";
    }
}