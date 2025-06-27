package controller;

import config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ReservationService;
import service.WorkSpaceService;

import java.util.Scanner;

public class Main {

    private static final String WELCOME_MESSAGE = "Welcome to the application!";
    private static final int MAIN_MENU_OPTION_AMOUNT = 3;
    private static final int ADMIN_MENU_OPTION_AMOUNT = 6;
    private static final int CUSTOMER_MENU_OPTION_AMOUNT = 5;

    public static void main(String[] args) {
        int option;
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Scanner scanner = new Scanner(System.in);
        MenuSelector selector = context.getBean(MenuSelector.class, scanner);
        DataReader reader = context.getBean(DataReader.class, scanner);
        WorkSpaceService workSpaceService = context.getBean(WorkSpaceService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);
        ActionHandler actionHandler = context.getBean(ActionHandler.class, workSpaceService, reservationService);

        System.out.println(WELCOME_MESSAGE);

        option = selector.chooseMainMenuOperation();

        while (option != MAIN_MENU_OPTION_AMOUNT) {
            if (option == 1) {
                processAdminAction(selector, reader, actionHandler);
            } else if (option == 2) {
                processUserAction(selector, reader, actionHandler);
            }

            option = selector.chooseMainMenuOperation();
        }

        scanner.close();
    }

    private static void processAdminAction(MenuSelector selector, DataReader reader, ActionHandler actionHandler) {
        int option = selector.chooseAdminMenuOperation();

        while (option != ADMIN_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> {
                    if (actionHandler.addNewWorkspace(reader.getNewSpace()) == 0) {
                        System.out.println("Workspace wasn't added!");
                    } else {
                        System.out.println("Workspace was added!");
                    }
                }
                case 2 -> {
                    if (actionHandler.deleteWorkSpace(reader.getWorkSpaceIdForDeletion()) == 0) {
                        System.out.println("Workspace wasn't deleted!");
                    } else {
                        System.out.println("Workspace was deleted!");
                    }
                }
                case 3 -> System.out.print(actionHandler.getAllWorkSpaces());
                case 4 -> {
                    if (actionHandler.updateWorkSpace(reader.getUpdatedSpace()) == 0) {
                        System.out.println("Workspace wasn't updated!");
                    } else {
                        System.out.println("Workspace was updated!");
                    }
                }
                case 5 -> System.out.print(actionHandler.getAllReservations());
                default -> System.out.println("Wrong option!");
            }

            option = selector.chooseAdminMenuOperation();
        }
    }

    private static void processUserAction(MenuSelector selector, DataReader reader, ActionHandler actionHandler) {
        int option = selector.chooseCustomerMenuOperation();

        while (option != CUSTOMER_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> System.out.print(actionHandler.getAvailableWorkSpaces());
                case 2 -> {
                    if (actionHandler.addReservation(reader.getNewReservation()) == 0) {
                        System.out.println("Reservation wasn't added!");
                    } else {
                        System.out.println("Reservation was added!");
                    }
                }
                case 3 -> System.out.print(actionHandler.getAllReservations());
                case 4 -> {
                    if (actionHandler.deleteReservation(reader.getReservationIdForDeletion()) == 0) {
                        System.out.println("Reservation wasn't deleted!");
                    } else {
                        System.out.println("Reservation was deleted!");
                    }
                }
                default -> System.out.println("Wrong option!");
            }

            option = selector.chooseCustomerMenuOperation();
        }
    }
}
