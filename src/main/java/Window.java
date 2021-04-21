import java.awt.BorderLayout;



import java.awt.GridLayout;



import java.awt.HeadlessException;



import java.util.concurrent.TimeUnit;



import javax.swing.*;



import java.awt.event.*;







public class Window extends JFrame {







    private static final ImageIcon ICON_X = new ImageIcon("x.png");



    private static final ImageIcon ICON_O = new ImageIcon("o.png");



    private static final ImageIcon ICON_DEFAULT = new ImageIcon("def.png");







    private static JButton [][] buttons = new JButton[3][3];



    private static int NUMBER_STEP;



    private static JButton button1 = new JButton("New game");



    private static JButton button2 = new JButton("Exit");







    public Window() throws HeadlessException {



        NUMBER_STEP = 0;



        setSize(500, 500);



        setLocation(300, 300);



        setDefaultCloseOperation(EXIT_ON_CLOSE);







        JMenuBar menuBar = new JMenuBar();



        JMenu file = new JMenu("Options");



        JMenuItem item1 = new JMenuItem("New game");



        JMenuItem item3 = new JMenuItem("Exit");



        item3.addActionListener(e -> dispose());



        item1.addActionListener(e -> {



            new Window();



            dispose();



        });







        file.add(item1);



        file.add(item3);







        menuBar.add(file);







        add(menuBar);







        JPanel panel = new JPanel(new BorderLayout());



        JPanel fp = new JPanel(new GridLayout(3,3));







        for (int i = 0; i < 3; i++) {



            for (int j = 0; j < 3; j++) {



                buttons[i][j] = new JButton();



                buttons[i][j].setIcon(ICON_DEFAULT);



                buttons[i][j].setDisabledIcon(ICON_DEFAULT);



                final int finalI = i;



                final int finalJ = j;



                buttons[i][j].addActionListener(e -> {



                    System.out.println(finalI + " " + finalJ);



                    buttons[finalI][finalJ].setIcon(ICON_X);



                    buttons[finalI][finalJ].setEnabled(false);



                    buttons[finalI][finalJ].setDisabledIcon(ICON_X);



                    NUMBER_STEP ++;



                    //if (NUMBER_STEP == 9) {



                    checkWin();



                    //}



                    moveAI(buttons);



                    checkWin();



                });



                fp.add(buttons[i][j]);



            }



        }







        panel.add(menuBar, BorderLayout.NORTH);



        panel.add(fp, BorderLayout.CENTER);



        add(panel);



        panel.setVisible(true);



        setResizable(false);



        setVisible(true);



    }







    public static void main(String[] args) {



        new Window();



    }







    private static void moveAI(JButton[][] buttons) {



        if (NUMBER_STEP != 0) {



            NUMBER_STEP++;



            int count = 0;



            for (int j = 0; j < 3; j++) {



                int diagonal1 = 0, diagonal2 = 0;



                for (int i = 0; i < 3; i++) {



                    if (buttons[i][i].getIcon() == ICON_X) {



                        diagonal1++;



                    }



                    if (buttons[i][2 - i].getIcon() == ICON_X) {



                        diagonal2++;



                    }



                }



                if (diagonal1 >= 2 && count == 0) {



                    for (int k = 0; k < 3; k++) {



                        if (buttons[k][k].getIcon() == ICON_DEFAULT && count == 0) {



                            buttons[k][k].setIcon(ICON_O);



                            buttons[k][k].setEnabled(false);



                            buttons[k][k].setDisabledIcon(ICON_O);



                            count++;



                        }



                    }



                } else if (diagonal2 >= 2 && count == 0) {



                    for (int k = 0; k < 3; k++) {



                        if (buttons[k][2 - k].getIcon() == ICON_DEFAULT && count == 0) {



                            buttons[k][2 - k].setIcon(ICON_O);



                            buttons[k][2 - k].setEnabled(false);



                            buttons[k][2 - k].setDisabledIcon(ICON_O);



                            count++;



                        }



                    }



                } else if ((buttons[0][0].getIcon() == ICON_X && buttons[2][2].getIcon() == ICON_X) || (buttons[0][2].getIcon() == ICON_X && buttons[2][0].getIcon() == ICON_X) && count == 0) {



                    if (buttons[1][1].getIcon() == ICON_DEFAULT) {



                        buttons[1][1].setIcon(ICON_O);



                        buttons[1][1].setEnabled(false);



                        buttons[1][1].setDisabledIcon(ICON_O);



                        count++;



                    }



                }



            }



            if (count == 0) {



                int hor1, vert1;



                for (int i = 0; i < 3; i++) {



                    hor1 = 0;



                    vert1 = 0;



                    for (int j = 0; j < 3; j++) {



                        if (buttons[i][j].getIcon() == ICON_X) {



                            hor1++;



                        }



                        if (buttons[j][i].getIcon() == ICON_X) {



                            vert1++;



                        }



                    }



                    if (hor1 >= 2 && count == 0) {



                        for (int k = 0; k < 3; k++) {



                            if (buttons[i][k].getIcon() == ICON_DEFAULT) {



                                buttons[i][k].setIcon(ICON_O);



                                buttons[i][k].setEnabled(false);



                                buttons[i][k].setDisabledIcon(ICON_O);



                                count++;



                            }



                        }



                    }



                    if (vert1 >= 2 && count == 0) {



                        for (int k = 0; k < 3; k++) {



                            if (buttons[k][i].getIcon() == ICON_DEFAULT) {



                                buttons[k][i].setIcon(ICON_O);



                                buttons[k][i].setEnabled(false);



                                buttons[k][i].setDisabledIcon(ICON_O);



                                count++;



                            }



                        }



                    }



                }



            }







            if (count == 0) {



                for (int i = 0; i < 3; i++) {



                    for (int j = 0; j < 3; j++) {



                        if (buttons[i][j].getIcon() == ICON_DEFAULT) {



                            buttons[i][j].setIcon(ICON_O);



                            buttons[i][j].setEnabled(false);



                            buttons[i][j].setDisabledIcon(ICON_O);



                            return;



                        }



                    }



                }



            }



        }



    }







    private static boolean checkDiagonals(ImageIcon ICON) {



        boolean diag1 = true, diag2 = true;



        for (int i = 0; i < 3; i ++) {



            diag1 &= (buttons[i][i].getIcon() == ICON);



            diag2 &= (buttons[2 - i][i].getIcon() == ICON);



        }



        return (diag1 || diag2);



    }







    private static boolean checklines(ImageIcon ICON) {



        boolean hor, vert;



        for (int i = 0; i < 3; i ++) {



            hor = true;



            vert = true;



            for (int j = 0; j < 3; j++) {



                hor &= (buttons[i][j].getIcon() == ICON);



                vert &= (buttons[j][i].getIcon() == ICON);



            }



            if (hor || vert) return true;



        }



        return false;



    }







    private static int isWin(int NUMBER_STEP) {



        if (NUMBER_STEP >= 3) {



            if (checkDiagonals(ICON_X)) return 1;



            if (checklines(ICON_X)) return 1;



            if (checkDiagonals(ICON_O)) return 2;



            if (checklines(ICON_O)) return 2;



        }



        return 0;



    }







    private void checkWin() {



        System.out.println(NUMBER_STEP);



        if (isWin(NUMBER_STEP) == 1 ) {



            JDialog dialog = new AboutDialog(this,"Вы победили!");



            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);



            dialog.setVisible(true);



        }



        else if (NUMBER_STEP == 9 ) {



            JDialog dialog = new AboutDialog(this,"Ничья!");



            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);



            dialog.setVisible(true);



        }



        else if (isWin(NUMBER_STEP) == 2 ) {



            JDialog dialog = new AboutDialog(this,"Вы проиграли");



            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);



            dialog.setVisible(true);



        }



    }



}







class AboutDialog extends JDialog



{



    public AboutDialog(JFrame owner, String title)



    {



        super(owner, "", true);



        JLabel label = new JLabel(title);



        JButton button3 = new JButton("New game");



        button3.addActionListener(e -> {



            owner.dispose();



            dispose();



            new Window();







        });



        JButton button4 = new JButton("Exit");



        button4.addActionListener(new ActionListener() {



            public void actionPerformed(ActionEvent event) {



                //owner.dispose();



                //dispose();



                System.exit(0);



            }



        });



        JPanel panel1 = new JPanel();



        panel1.add(button3);



        panel1.add(button4);



        add(label, BorderLayout.CENTER);



        add(panel1, BorderLayout.SOUTH);



        setSize(260, 160);



        setLocation(300, 300);



    }



}

