package ui;

import datastructures.AreaBST;
import datastructures.Graph;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Area;
import model.Location;

public class MainFrame extends JFrame {
    private AreaBST<Area> areaTree;
    private Graph travelGraph;
    private JComboBox<Area> startAreaComboBox; 
    private JComboBox<Area> areaComboBox;      
    private JComboBox<String> activityComboBox;
    private JTextArea resultTextArea;


    private final Color c_Background = new Color(0x40262A); 
    private final Color c_Foreground = new Color(0x7B4A52); 
    private final Color c_TextLight  = new Color(245, 235, 230); 
    private final Color c_TextDark   = new Color(255, 255, 255); 

    public MainFrame() {
        initData();   
        setupUI();    
    }

    private void initData() {
        areaTree = new AreaBST<>();
        service.DataLoader.loadData(areaTree, "data.json");
    
        travelGraph = new Graph();
        travelGraph.addRoute("Taksim", "Besiktas", 15, "Bus");
        travelGraph.addRoute("Besiktas", "Uskudar", 10, "Ferry");
        travelGraph.addRoute("Uskudar", "Kadikoy", 15, "Metro");
        travelGraph.addRoute("Taksim", "Kadikoy", 25, "Ferry");
        travelGraph.addRoute("Besiktas", "Kadikoy", 20, "Ferry");
        travelGraph.addRoute("Besiktas", "Fatih", 25, "Bus");
        travelGraph.addRoute("Fatih", "Taksim", 15, "Metro");
    }

    private void setupUI() {
        setTitle("IstanbulGO");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // MAIN PANEL 
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(c_Background);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // HEADER
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(0, 70));
        JLabel headerLabel = new JLabel("ISTANBULGO");
        headerLabel.setForeground(c_TextLight); 
        headerLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headerPanel.add(headerLabel);

        // CONTROL PANEL 
        JPanel controlPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        controlPanel.setBackground(c_Foreground);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(c_TextLight, 1),
            new EmptyBorder(25, 40, 25, 40)
        ));

        List<Area> allAreas = areaTree.getAll();
        startAreaComboBox = new JComboBox<>(allAreas.toArray(new Area[0]));
        areaComboBox = new JComboBox<>(allAreas.toArray(new Area[0]));
        activityComboBox = new JComboBox<>(new String[]{"✨ All", "🏛️ Historical", "🛍️ Shopping", "🥳 Party", "🎨 Cultural"});

        // BUTTONS 
        JButton findButton = new JButton("🔍 Explore Places");
        styleButton(findButton, c_Background, c_TextLight); 

        JButton routeButton = new JButton("🚲 Show Route");
        styleButton(routeButton, c_Background, c_TextLight); 
        
        findButton.addActionListener(e -> displayResults());
        routeButton.addActionListener(e -> showRoute());

        controlPanel.add(createLightLabel("📍 Current Location:"));
        controlPanel.add(startAreaComboBox);
        controlPanel.add(createLightLabel("🎯 Destination:"));
        controlPanel.add(areaComboBox);
        controlPanel.add(createLightLabel("🎭 Activity:"));
        controlPanel.add(activityComboBox);
        controlPanel.add(findButton);
        controlPanel.add(routeButton);

        // RESULTS AREA
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setBackground(c_Foreground);
        resultTextArea.setForeground(c_TextDark);
        resultTextArea.setFont(new Font("Segoe UI", Font.BOLD, 15));
        resultTextArea.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(new LineBorder(c_TextLight), "✨ Discovery List", 0, 0, null, c_TextLight));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(c_Foreground);
        scrollPane.setPreferredSize(new Dimension(0, 300));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JLabel createLightLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(c_TextLight);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return label;
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg); 
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new LineBorder(fg, 1)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void displayResults() {
        Area selectedArea = (Area) areaComboBox.getSelectedItem();
        String selectedActivity = (String) activityComboBox.getSelectedItem();
        String activityTitle = selectedActivity.contains(" ") ? selectedActivity.split(" ")[1] : selectedActivity;

        if (selectedArea == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("🧭 ").append(selectedArea.getName().toUpperCase()).append(" GUIDE\n\n");

        boolean found = false;
        for (Location loc : selectedArea.getLocations()) {
            if (selectedActivity.equals("✨ All") || loc.getActivity().equalsIgnoreCase(activityTitle)) {
                sb.append("✅ ").append(loc.getName()).append(" (⭐ ").append(loc.getRating()).append("/5)\n\n");
                sb.append("   ").append(loc.getDescription()).append("\n");
                sb.append("   💬 \"").append(loc.getBestComment()).append("\"\n\n");
                found = true;
            }
        }
        if (!found) sb.append("❌ No places found.");
        resultTextArea.setText(sb.toString());
        resultTextArea.setCaretPosition(0);
    }

    private void showRoute() {
        Area startNode = (Area) startAreaComboBox.getSelectedItem();
        Area endNode = (Area) areaComboBox.getSelectedItem();
        if (startNode != null && endNode != null) {
            String routeInfo = travelGraph.findShortestPath(startNode.getName(), endNode.getName());
            JOptionPane.showMessageDialog(this, "🧭 Transport Plan:\n" + routeInfo, "Route Details", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
