package view.labels;

import controller.PersonController;
import models.Person;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.regex.Pattern;

public class MyAccountPage extends JLabel {

    private JPanel transparentPanel;

    private JLabel usernameLabel;
    private JLabel changeUsernameLabel;
    private JLabel changeEmailLabel;
    private JLabel emailLabel;
    private JTextField changeEmailField;
    private JTextField changeUsernameField;
    private JButton changeUsernameButton;
    private JButton changeEmailButton;
    private JButton changePasswordButton;
    private JButton auditPageButton;
    private JLabel gifLabel;
    private int width = 1125;
    private int height = 750;



    public MyAccountPage(){
        this.setBounds(0,1100,width, height);
        initTransparentPanel();
        initUsernameLabel();
        initChangeUsernameLabel();
        initEmailLabel();
        initChangeEmailLabel();
        initChageUsernameButton();
        initChangeEmailButton();
        initChangeUsernameField();
        initChangeEmailField();
        initChangePasswordButton();
        initAuditPageButton();
        initImageLabel();
    }



    public void initTransparentPanel(){
        transparentPanel = new TransparentPanel(100,175,900,420){
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        add(transparentPanel);
    }


    private void initUsernameLabel(){
        usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(20, 50, 100, 20);
        transparentPanel.add(usernameLabel);
    }

    private void initChangeUsernameLabel(){
        changeUsernameLabel = new JLabel();
        changeUsernameLabel.setBounds(150, 50, 250, 20);
        transparentPanel.add(changeUsernameLabel);
    }

    private void initEmailLabel(){
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20,90,100,20);
        transparentPanel.add(emailLabel);
    }

    private void initChangeEmailLabel(){
        changeEmailLabel = new JLabel();
        changeEmailLabel.setBounds(150,90,250,20);
        transparentPanel.add(changeEmailLabel);
    }

    private void initChageUsernameButton() {
        changeUsernameButton = new JButton("CHANGE USERNAME");
        changeUsernameButton.setBounds(20, 200, 170, 25);
        changeUsernameButton.setBackground(Color.CYAN);
        transparentPanel.add(changeUsernameButton);
    }

    private void initChangeEmailButton(){
        changeEmailButton = new JButton("CHANGE EMAIL ADRESS");
        changeEmailButton.setBounds(20,240,170,25);
        changeEmailButton.setBackground(Color.CYAN);
        transparentPanel.add(changeEmailButton);
    }

    private void initChangePasswordButton(){
        changePasswordButton = new JButton("CHANGE PASSWORD");
        changePasswordButton.setBounds(20, 308, 330, 25);
        changePasswordButton.setBackground(Color.CYAN);
        transparentPanel.add(changePasswordButton);
    }

    private void initChangeUsernameField(){
        changeUsernameField = new JTextField();
        changeUsernameField.setBounds(200, 200,150,25);
        transparentPanel.add(changeUsernameField);
    }

    private void initChangeEmailField(){
        changeEmailField = new JTextField();
        changeEmailField.setBounds(200, 240,150,25);
        transparentPanel.add(changeEmailField);
    }

    private void initAuditPageButton(){
        auditPageButton = new JButton("AUDIT PAGE");
        auditPageButton.setBounds(20, 348, 330, 25);
        auditPageButton.setBackground(Color.CYAN);
        transparentPanel.add(auditPageButton);
    }

    private void initImageLabel(){
        String  image = "./src/main/resources/icons/compas1.png";
        gifLabel = new SetSizeAndImage(520,50,410,308, image);
        transparentPanel.add(gifLabel);
    }

    public void updateUsername(){
        boolean updateUsername = PersonController.getInstance()
                    .updateUsername(changeUsernameField.getText(), changeUsernameLabel.getText());

        if(updateUsername){
            changeUsernameLabel.setText( changeUsernameField.getText());
            JOptionPane.showMessageDialog(null,"Username changed");
        }
    }

    public void updateEmailAdress(){
        boolean updateEmailAdress = PersonController.getInstance()
                .updateEmailAdress(changeEmailField.getText(), changeEmailLabel.getText());

        if(updateEmailAdress){

            changeEmailLabel.setText(changeEmailField.getText());
            JOptionPane.showMessageDialog(null, "Email adress has been changed");
        }else{
            showMessage("Error changing email adress!");
        }

    }

    public boolean validUsername(){
       Optional<Person> person = PersonController.getInstance().findByUsername(changeUsernameField.getText());

        if(changeUsernameField.getText().equals("")){
            showMessage("Enter new username");
            return false;
        }

        if(person.isPresent()){
            showMessage("Wrong username");
            return false;
        }
        return true;
    }


    public boolean validEmailAdress(){
        Optional<Person> person = PersonController.getInstance().findByEmaiAdress(changeEmailField.getText());

        if(changeEmailField.getText().equals("")){
            showMessage("Enter new email adress");
            return false;
        }

        if(!validEmailFormat(changeEmailField.getText())){
            showMessage("Email adress is not correct");
            return false;
        }

        if(person.isPresent()){
            showMessage("Wrong email adress");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private boolean validEmailFormat(String emailAdress){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(emailAdress).matches();
    }

    public void resetFields(){
        changeUsernameField.setText("");
        changeEmailField.setText("");
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JLabel getChangeUsernameLabel() {
        return changeUsernameLabel;
    }

    public JLabel getChangeEmailLabel() {
        return changeEmailLabel;
    }

    public JTextField getChangeEmailField() {
        return changeEmailField;
    }

    public JTextField getChangeUsernameField() {
        return changeUsernameField;
    }

    public JButton getAuditPageButton() {
        return auditPageButton;
    }

    public JButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public JButton getChangeEmailButton() {
        return changeEmailButton;
    }


    @Override
    public String toString() {
        return "MY ACCOUNT PAGE";
    }
}
