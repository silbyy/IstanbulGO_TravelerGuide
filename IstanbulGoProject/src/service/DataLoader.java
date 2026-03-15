package service;

import datastructures.AreaBST;
import model.Area;
import model.Location;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {
    public static void loadData(AreaBST<Area> areaTree, String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            
            String areaBlockPattern = "\\{\\s*\"areaName\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"locations\"\\s*:\\s*\\[(.*?)\\]\\s*\\}";
            Pattern areaPattern = Pattern.compile(areaBlockPattern, Pattern.DOTALL);
            Matcher areaMatcher = areaPattern.matcher(content);

            while (areaMatcher.find()) {
                String areaName = areaMatcher.group(1);
                String locationsContent = areaMatcher.group(2);

                Area area = new Area(areaName);
                areaTree.insert(area);

                String locPatternStr = "\\{\\s*\"name\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"category\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"rating\"\\s*:\\s*(.*?)\\s*,\\s*\"reviews\"\\s*:\\s*(.*?)\\s*,\\s*\"description\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"bestComment\"\\s*:\\s*\"(.*?)\"\\s*\\}";
                Pattern locPattern = Pattern.compile(locPatternStr, Pattern.DOTALL);
                Matcher locMatcher = locPattern.matcher(locationsContent);

                while (locMatcher.find()) {
                    String name = locMatcher.group(1);
                    String cat = locMatcher.group(2);
                    double rate = Double.parseDouble(locMatcher.group(3).trim());
                    int rev = Integer.parseInt(locMatcher.group(4).trim());
                    String desc = locMatcher.group(5);
                    String comm = locMatcher.group(6);

                    area.addLocation(new Location(name, cat, rate, rev, desc, comm));
                }
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Could not load JSON file!");
            e.printStackTrace();
        }
    }
}
