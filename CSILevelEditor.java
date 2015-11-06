import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CSILevelEditor extends JFrame {

    public CSILevelEditor() {
        initComponents();
    }                        
    private void initComponents() {
        
        setTitle("Roguelike CSI Level Editor by Nikiforoff.tk (Version from 11/06/15)");
        setResizable(false);
        
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable table = new JTable();
        JButton buildButton = new JButton();
        JButton helpButton = new JButton();
        JLabel posLabel = new JLabel();
        JLabel startLabel = new JLabel();
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(0, 0);
        String header[] = new String[25];
        for(int h = 0; h < header.length; h++){ header[h] = "Position "+h; }
        model.setColumnIdentifiers(header); 
        for (int c = 1; c <= 80; c++) {
        model.addRow(new Object[] { null, null, null,
                null, null, null });
        }
        table.setModel(model);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    posLabel.setText("X: "+col+" Y: "+row);
                }
            }
        });
        jScrollPane1.setViewportView(table);
        
        helpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               JOptionPane.showMessageDialog(null, "Hi. Glad you're using my editor. "
                        + "\nIn short, about the editor: this editor for editing levels for the libjcsi library. "
                        + "\nSo that you can generate: "
                        + "\n0 - walls and the starting position for a player (yet). "
                        + "\nIf you specify a unit, the game will cease to work as it should. "
                        + "\nThe ability to make their traps, barriers and so on will make later. "
                        + "\nDONT DELETE 'main.txt' This file contains the bulk code of the game (character control, for example)"
                        + "\nWatch for updates on my website - http://nikiforoff.tk section \"files\". "
                        + "\nOn my github https://github.com/PurpleHorrorRus", "About", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });
        
        buildButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String mainText = "";
                try {
                    Scanner in = new Scanner(new FileReader("main.txt"));
                    while(in.hasNextLine()){
                        mainText += "\n"+in.nextLine();
                    }
                    List<String> numdata = new ArrayList<>();
                    mainText += "\n\tpublic void buildLevel(){";
                    for(int i = 0; i < table.getModel().getRowCount(); i++){
                        for(int b = 0; b < table.getModel().getColumnCount(); b++){
                            String data = (String) table.getModel().getValueAt(i,b); // col(b) - x, row(i) - y
                            if(data != null){
                               mainText += "\n"
                                    + "\t\tcsi.print("+b+", "+i+", "+"\""+data+"\""+", CSIColor.BLUE);"; 
                            }
                        } 
                    }
                    mainText += "\n\t}\n}";
                    int x = 0, y = 0;
                    try{ x = Integer.parseInt(xField.getText()); y = Integer.parseInt(yField.getText()); if(x > 24 || y > 79) { JOptionPane.showMessageDialog(null, "X should not exceed 24, and Y should not exceed 79!", "Error", JOptionPane.ERROR_MESSAGE); } }
                    catch(NumberFormatException NFE) { JOptionPane.showMessageDialog(null, "In the X and Y need to enter the NUMBERS!", "Error", JOptionPane.ERROR_MESSAGE); }
                    mainText = mainText.replace("private int x = 0, y = 0", "private int x = "+x+", y = "+y);
                    String path = "A.java";
                    File create = new File(path);
                    try {
                        create.createNewFile();
                        try (PrintWriter writer = new PrintWriter(path, "UTF8")) {
                            writer.println(mainText);
                        }
                        ShowCode cod = new ShowCode(mainText);
                        cod.setVisible(true);
                    } catch (IOException ex) { }
                } catch (FileNotFoundException ex) { }
            }
        });
        jScrollPane1.setViewportView(table);

        buildButton.setText("Build");
        buildButton.setToolTipText("");

        helpButton.setText("Help");

        posLabel.setText("Position");

        startLabel.setText("Starting position");

        xField.setText("X");

        yField.setText("Y");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1245, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(posLabel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buildButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buildButton)
                            .addComponent(helpButton)
                            .addComponent(posLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(startLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(xField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(yField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }                     

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CSILevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CSILevelEditor().setVisible(true);
        });
    }
}

class ShowCode extends JFrame {
    JScrollPane ScrollPane = new JScrollPane();
    JTextArea showCode = new JTextArea();
    ShowCode(String code){
        initComponents();
        showCode.append(code);
    }
                          
    private void initComponents() {
        setTitle("Source code");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        showCode.setColumns(20);
        showCode.setRows(5);
        ScrollPane.setViewportView(showCode);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPane, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPane, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }                
}
