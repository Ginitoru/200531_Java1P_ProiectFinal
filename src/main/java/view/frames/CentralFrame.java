package view.frames;

import AppPackage.AnimationClass;
import controller.AuditController;
import media.Picture;
import util.LogOutFunction;
import view.buttons.MiniButtons;
import view.labels.*;
import view.menubar.MenuBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    //TODO sa vedem daca mai e nevoie sa fie CentralFrame singleton class

public class CentralFrame extends JFrame {


    private BackgroundLabel backgroundLabel;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private MainPage mainPage;
    private AddFlightsPage addFlightsPage;
    private MyAccountPage myAccountPage;
    private ChangePasswordPage changePasswordPage;
    private AuditPage auditPage;
    private AuditController log = AuditController.getInstance();


    private JPanel panel;
    private MenuBar menuBar;
    private JLabel menuBarLabel;
    private Picture picture;
    private List<JLabel> pages;
    private List<JButton> minimizeButtons;
    private JButton miniButton;
    private int count;
    private int count2;
    private Timer timer5;
    private int width = 1125;
    private int height = 750;
    private int posX =0, posY = 0;
    private  Path filePath = Paths.get("./src/main/resources/images");
    private AnimationClass slideEfect = new AnimationClass();
    private Random random = new Random();
    private List<JLabel> labelsBackButton = new ArrayList<>();



    private CentralFrame(){
        initFrame();
        initBackgroundLabel();
        initMenuBar();
        initLoginPage();
        initRegisterPage();
        initMainPage();
        initAddFlightsPage();
        initMyAccountPage();
        initChangePasswordPage();
        initAuditPage();
        mouseListener();
        initCloseMinimizeBackButton();
        setVisible(true);
    }

    private void initFrame(){
        setSize(width, height);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel  = new JPanel();
        panel.setSize(width, height);
        panel.setLayout(null);
        add(panel);
    }

    private String getRandomBacgroundPicture(){
        picture = new Picture();
        java.util.List<String> strings = picture.getPictures(filePath);
        return  strings.get(random.nextInt(strings.size()));
    }

    private void initBackgroundLabel(){
        backgroundLabel = new BackgroundLabel(getRandomBacgroundPicture(),width, height);
        panel.add(backgroundLabel);
    }


    private void initCloseMinimizeBackButton(){
       List<String> miniGifs = picture.getMiniGifs();

        minimizeButtons = new ArrayList<>();

        for(int i = 0; i < 3 ; i++){
            miniButton = new MiniButtons(1041 + i*27,0,miniGifs.get(i));
            minimizeButtons.add(miniButton);
            backgroundLabel.add(miniButton);
        }

        minimizeButtons.get(0).addActionListener(e -> letsGoBack() );
        minimizeButtons.get(1).addActionListener(e -> setExtendedState(JFrame.ICONIFIED) );
        minimizeButtons.get(2).addActionListener(e -> dispose() );

    }



    private void initMenuBar(){
        menuBar = new MenuBar();
        menuBarLabel = new JLabel();
        menuBarLabel.setBounds(0,-27,1041,27);
        menuBarLabel.add(menuBar);
        backgroundLabel.add(menuBarLabel);

        menuBar.getHomePage().addActionListener( e -> menuBarHomePage());
        menuBar.getMyAccount().addActionListener(e -> menuBarMyAccountPage());
        menuBar.getLogOut().addActionListener( e-> menuBarLogOut());
    }

    private void initLoginPage(){
        loginPage = new LoginPage();
        backgroundLabel.add(loginPage);

        loginPage.getLoginButton().addActionListener(e-> loginPageLoginButton());
        loginPage.getRegisterButton().addActionListener(e -> moveLoginRegisterPage(loginPage));
    }

    private void initRegisterPage(){
        registerPage = new RegisterPage();
        backgroundLabel.add(registerPage);

        registerPage.getRegisterButton().addActionListener(e -> registerPageRegisterButton());
        registerPage.getLoginButton().addActionListener(e -> moveLoginRegisterPage(registerPage));
    }

    private void initMainPage(){
        mainPage = MainPage.getInstance();
        backgroundLabel.add(mainPage);

        mainPage.getAdaugaZbor().addActionListener(e->addFlightButton());
    }

    private void initMyAccountPage(){
        myAccountPage = MyAccountPage.getInstance();
        backgroundLabel.add(myAccountPage);

        myAccountPage.getChangePasswordButton().addActionListener( e-> myAccoutPageChangePassButton());

        myAccountPage.getAuditPageButton().addActionListener(e-> myAccountPageAuditPageButton());

        myAccountPage.getChangeEmailButton().addActionListener(e ->  myAccountPageChangeEmailButton());

        myAccountPage.getChangeUsernameButton().addActionListener(e->  myAccountPageChangeUsernameButton());
    }


    private void initChangePasswordPage(){
        changePasswordPage = new ChangePasswordPage();
        backgroundLabel.add(changePasswordPage);
        changePasswordPage.getChangePassword().addActionListener(e -> changePasswordPageChangePasswordButton());
    }

    private void initAuditPage(){
        auditPage = new AuditPage();
        backgroundLabel.add(auditPage);
    }

    private void initAddFlightsPage(){
        addFlightsPage = new AddFlightsPage();
        backgroundLabel.add(addFlightsPage);
        addFlightsPage.getAnulateButton().addActionListener(e-> flightPageAnulateButton());
        addFlightsPage.getAddFlightButton().addActionListener(e -> flightPageAddFlightButton());
    }

    private void mouseListener() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt) {

                setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);

            }
        });
    }

    //todo de rezolvat bug backbutton cand dau back si foward si iar back
    // nu o ia de la ultima pagina afisata ci de unde a ramas contorul
    public void letsGoBack(){

        int lastIndex = labelsBackButton.size() - count -1;
        int beforeLastIndex = labelsBackButton.size()  - count - 2;

        if(loginPage.getY() !=0 && labelsBackButton.get(lastIndex).getY() == 0){  //anti spam button:D
            count++;
     //       System.out.println(count + "  : " + labelsBackButton.size());

            if(lastIndex  > 1){
                oneLabelUpOneLabelDown(labelsBackButton.get(beforeLastIndex));
            }

            if(lastIndex == 1){
                count2 = 0;
                moveTwoLabelsDown(loginPage);
            }

            if((lastIndex > 1) && (labelsBackButton.get(beforeLastIndex) == loginPage)){

                moveTwoLabelsDown(loginPage);

            }
        }

//        if(loginPage.getY() == 0){
//            count = 0;
//            labelsBackButton.clear();
//        }
    }

    public void moveLoginRegisterPage(JLabel up){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getY() == -1100) && (page != up)){
                slideEfect.jLabelYDown(-1100,0,10,4, page);
                slideEfect.jLabelYUp(0,-1100,10,4, up);
            }
        }
    }


    public Timer getTimer5(){
        timer5 = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(count2 == 0){
                    slideEfect.jLabelYDown(0,40,20,2,loginPage);
                }
                if(count2 == 22){
                    slideEfect.jLabelYUp(40,-1100,10,4, loginPage);
                    slideEfect.jLabelYUp(1100,0,10,4, mainPage);
                }

                if( count2 == 150){
                    slideEfect.jLabelYDown(-27,0,1,1,menuBarLabel);
                    timer5.stop();
                }
                count2++;
            }
        });
        return timer5;
    }

    public void moveTwoLabelsDown(JLabel down){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getX() == 0) && (page != down)){
                slideEfect.jLabelYDown(0,1100,10,4, page);
                slideEfect.jLabelYDown(-1100,0,10,4, down);
                slideEfect.jLabelYUp(0,-27,1,1,menuBarLabel);
            }
        }
    }

    public void oneLabelUpOneLabelDown(JLabel up){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getY() == 0) && (page != up)){
                slideEfect.jLabelYDown(0,1100,10,4, page);
                slideEfect.jLabelYUp(1100,0,10,4, up);
            }
        }
    }

    public List<JLabel> getPages(){
        pages = new ArrayList<>();
        pages.add(loginPage);
        pages.add(registerPage);
        pages.add(mainPage);
        pages.add(addFlightsPage);
        pages.add(myAccountPage);
        pages.add(changePasswordPage);
        pages.add(auditPage);
        return pages;
    }

    private void menuBarHomePage(){
        if(mainPage.getY() == 1100){
            log.createAuditLog("accesed Main Page on:");
 //           labelsBackButton.add(mainPage);
            oneLabelUpOneLabelDown(mainPage);
            addPageToBackButton(mainPage);
        }
    }

    private void menuBarMyAccountPage(){
        if(myAccountPage.getY() == 1100){
            oneLabelUpOneLabelDown(myAccountPage);
            log.createAuditLog("accessed My Account page on:"); //todo de verificat daca acum e logul ok
//            labelsBackButton.add(myAccountPage);
            addPageToBackButton(myAccountPage);
        }
    }

    private void menuBarLogOut(){
        if(loginPage.getY() == -1100){
            moveTwoLabelsDown(loginPage);
            log.createAuditLog("logged out on:");
            count2 = 0;
        }
    }

    private void loginPageLoginButton(){
        if(loginPage.validCredential()){
            count = 0;
            labelsBackButton.clear();
            log.createAuditLog("logged in on");
            log.createAuditLog("accessed Main Page on:");
            getTimer5().start();
            new LogOutFunction().getLogOutTimer().start();
//            labelsBackButton.add(loginPage);
//            labelsBackButton.add(mainPage);
            addPageToBackButton(loginPage);
            addPageToBackButton(mainPage);

        }
    }

    private void registerPageRegisterButton(){
        if(registerPage.validRegisterFields()){
            registerPage.addUsername();
            registerPage.createAuditLog();
            moveLoginRegisterPage(registerPage);
        }
    }

    private void addFlightButton(){
        oneLabelUpOneLabelDown(addFlightsPage);
        log.createAuditLog("ADD FLIGHT PAGE");
 //       labelsBackButton.add(addFlightsPage);
        addPageToBackButton(addFlightsPage);
    }

    private void myAccoutPageChangePassButton(){
        oneLabelUpOneLabelDown(changePasswordPage);
        log.createAuditLog("PASSWORD CHANGE PAGE ");
//        labelsBackButton.add(changePasswordPage);
        addPageToBackButton(changePasswordPage);
    }

    private void myAccountPageAuditPageButton(){
        oneLabelUpOneLabelDown(auditPage);
        auditPage.initAuditTableData();
        log.createAuditLog("AUDIT PAGE");
//        labelsBackButton.add(auditPage);
        addPageToBackButton(auditPage);
    }

    private void myAccountPageChangeEmailButton(){
        if(myAccountPage.validEmailAdress()){
            myAccountPage.updateEmailAdress();
        }
    }

    private void myAccountPageChangeUsernameButton(){
        if(myAccountPage.validUsername()){
            myAccountPage.updateUsername();
            log.createAuditLog("CHANGED USERNAME");
        }
    }

    private void changePasswordPageChangePasswordButton(){
        if(changePasswordPage.validPassword()){
            changePasswordPage.updatePassword();
            moveTwoLabelsDown(loginPage);
            log.createAuditLog("CHANGED PASSWORD");
            count2 = 0;
        }
    }

    private void flightPageAnulateButton(){
        oneLabelUpOneLabelDown(mainPage);
        log.createAuditLog("EXIT ADD FLIGHT PAGE");
        log.createAuditLog("MAIN PAGE");
//        labelsBackButton.add(mainPage);
        addPageToBackButton(mainPage);
    }

    private void flightPageAddFlightButton(){
        if(addFlightsPage.valid()) {
            addFlightsPage.addFlight();
            mainPage.tableData();
            log.createAuditLog("ADDED A FLIGHT");
        }
    }

    private void addPageToBackButton(JLabel page){
        labelsBackButton.add(page);
        count = 0;
    }

    private static final class SingletonHolder{
       public static final CentralFrame INSTANCE = new CentralFrame();
    }

    public static CentralFrame getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

}
