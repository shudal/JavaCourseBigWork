package fun.heing.cal.frame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.*;
import java.awt.*;

import fun.heing.cal.util.Util;
public class MainFrame {
    JFrame mainFrame;
    Box mainPanel;
    JTextField text = new JTextField("0");
    JPanel downPanel;
    private String invalidInput;
    Util util = new Util();
    public MainFrame(int width, int height) {
        invalidInput = "invalid input";
        mainFrame = new JFrame("calculator");
        mainFrame.setSize(width, height);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMainPanel();
        mainFrame.setContentPane(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    private void initMainPanel() {
        mainPanel = Box.createVerticalBox();
        text.setBounds(20,10,200,30);
        text.setBackground(Color.yellow);
        text.setHorizontalAlignment(JTextField.RIGHT);
        mainPanel.add(text);

        downPanel = new JPanel();
        GridLayout grid = new GridLayout(5, 4);
        downPanel.setLayout(grid);
        downPanel.setBounds(20, 45, 200, 200);
        String s = "789*456/123+0.=-()";
        String[] s2= {"<-", "Clear"};
        for (int i=0; i<s.length(); ++i) {
            JButton btn = new JButton(s.charAt(i) + "");
            btn.setSize(20, 20);
            btn.setMargin(new Insets(2, 2, 2, 2));
            btn.addActionListener((e)->{
                delBtnClick(btn.getText());
            });
            downPanel.add(btn);
        }
        for (int i=0; i<s2.length; ++i) {
            JButton btn = new JButton(s2[i]);
            btn.setSize(20, 20);
            btn.setMargin(new Insets(2, 2, 2, 2));
            btn.addActionListener((e)->{
                delBtnClick(btn.getText());
            });
            downPanel.add(btn);

        }
        mainPanel.add(downPanel);
    }
    private void delBtnClick(String s) {
        System.out.print(s);
        String oriS = text.getText();
        if (oriS.equals(invalidInput)) {
            text.setText("0");
            oriS = "0";
        }
        if (oriS.equals("0")) {
            boolean flag = false;
            String s2 = "+-*/.";
            for (int i=0; i<s2.length(); ++i) {
                if (s.equals(s2.charAt(i) + "")) {
                    flag = true;
                }
            }
            if (!flag) {
                oriS = "";
            }
        }

        if (s.equals("<-")) {
            StringBuilder sb = new StringBuilder();
            if (!oriS.equals("")) {
                for (int i=0; i<=oriS.length()-2; ++i) {
                    sb.append(oriS.charAt(i));
                }
                text.setText(sb.toString());
            }
            return;
        }
        if (s.equals("=")) {
            delEqual();
            return;
        }
        if (s.equals("Clear")) {
            text.setText("0");
            return;
        }
        if (!s.equals("=")) {
            oriS += s;
            text.setText(oriS);
            return;
        }

    }
    private void delEqual() {
        String s = text.getText();
        s += "=";
        if (!util.validInput(s)) {
            text.setText(invalidInput);
            return;
        }
        String result = util.cal(s);
        text.setText(result);
    }
}
