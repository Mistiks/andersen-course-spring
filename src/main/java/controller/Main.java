package controller;

import config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final String WELCOME_MESSAGE = "Welcome to the application!";
    private static final int MAIN_MENU_OPTION_AMOUNT = 3;

    public static void main(String[] args) {
        int option;
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MenuSelector selector = context.getBean(MenuSelector.class);
        DataReader reader = context.getBean(DataReader.class);
        ActionHandler actionHandler = context.getBean(ActionHandler.class);

        System.out.println(WELCOME_MESSAGE);

        option = selector.chooseMainMenuOperation();

        while (option != MAIN_MENU_OPTION_AMOUNT) {
            if (option == 1) {
                actionHandler.processAdminAction(selector, reader);
            } else if (option == 2) {
                actionHandler.processUserAction(selector, reader);
            }

            option = selector.chooseMainMenuOperation();
        }
    }
}
