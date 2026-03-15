package model;

public class Location {
    private String name;
    private String activity;
    private double rating;      
    private int reviewCount;    
    private String description;
    private String bestComment;

    public Location(String name, String activity, double rating, int reviewCount, String description, String bestComment) {
        this.name = name;
        this.activity = activity;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.description = description;
        this.bestComment = bestComment;
    }

    // Getters
    public String getName() { return name; }
    public String getActivity() { return activity; }
    public double getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public String getDescription() { return description; }
    public String getBestComment() { return bestComment; }
}
