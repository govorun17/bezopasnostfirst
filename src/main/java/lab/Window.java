package lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class Window extends JFrame {
    private final JButton register, code, decode;
    private final JLabel strLabel, keyLabelFirst, keyLabelSecond, resLabel;
    private final JTextField strField, keyFieldFirst, keyFieldSecond, resField;
    private final aHandler handler = new aHandler();

    public Window() {
        super("Lab 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(100, 100);
        setSize(1000, 400);

        JPanel grid = new JPanel();
        GridLayout layout = new GridLayout(0, 4, 12, 12);
        grid.setLayout(layout);

        register = new JButton("Зарегистрировать текст");
        code = new JButton("Закодировать");
        decode = new JButton("Декодировать");
        strLabel = new JLabel("Исходный текст:");
        keyLabelFirst = new JLabel("Ключ 1 длины N:");
        keyLabelSecond = new JLabel("Ключ 2 длины N:");
        resLabel = new JLabel("Результат");
        strField = new JTextField(10);
        keyFieldFirst = new JTextField("1-2-3-4-...", 10);
        keyFieldSecond = new JTextField("1-2-3-4-...", 10);
        resField = new JTextField();

        grid.add(strLabel);
        grid.add(keyLabelFirst);
        grid.add(keyLabelSecond);
        grid.add(resLabel);

        grid.add(strField);
        grid.add(keyFieldFirst);
        grid.add(keyFieldSecond);
        grid.add(resField);

        grid.add(register);
        grid.add(code);
        grid.add(decode);

        getContentPane().add(grid);

        code.addActionListener(handler);
        decode.addActionListener(handler);
        register.addActionListener(handler);

        setVisible(true);
    }

    public class aHandler implements ActionListener {
        Algorithm algorithm = new Algorithm();
        Map<String, Integer> keys;
        String result = null;

        public void actionPerformed(ActionEvent e) {

            try {

                if (register.equals(e.getSource())) {
                    keys = algorithm.setStr(strField.getText());
                    keyLabelFirst.setText("Ключ 1 длины " + keys.get("first") + ":");
                    keyLabelSecond.setText("Ключ 2 длины " + keys.get("second") + ":");
                } else if (code.equals(e.getSource())) {
                    algorithm.setKeys(keyFieldFirst.getText(), keyFieldSecond.getText());
                    result = algorithm.code();
                } else if (decode.equals(e.getSource())) {
                    algorithm.setKeys(keyFieldFirst.getText(), keyFieldSecond.getText());
                    result = algorithm.decode();
                } else {
                    throw new IOException("Произошла неизвестная ошибка");
                }

                if (result != null) {
                    resLabel.setText("Результат:");
                    resField.setText(result);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Зарегистрируйте сообщение и попробуйте снова");
            }
        }
    }
}
