package model;
import datastructures.GenericLinkedList;

public class Area implements Comparable<Area> {
    private String name;
    private GenericLinkedList<Location> locations = new GenericLinkedList<>();
    public Area(String name) { this.name = name; }
    public String getName() { return name; }
    public void addLocation(Location l) { locations.add(l); }
    public GenericLinkedList<Location> getLocations() { return locations; }
    @Override
    public int compareTo(Area o) { return this.name.compareToIgnoreCase(o.name); }
    @Override
    public String toString() { return name; }
}
