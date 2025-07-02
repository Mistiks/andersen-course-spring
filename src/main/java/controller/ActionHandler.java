package controller;

import entity.Reservation;
import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.ReservationService;
import service.WorkSpaceReservationService;
import service.WorkSpaceService;

@Component
public class ActionHandler {

    private final WorkSpaceService workSpaceService;
    private final ReservationService reservationService;
    private final WorkSpaceReservationService workSpaceReservationService;
    private static final int ADMIN_MENU_OPTION_AMOUNT = 6;
    private static final int CUSTOMER_MENU_OPTION_AMOUNT = 5;

    @Autowired
    public ActionHandler(WorkSpaceService workSpaceService, ReservationService reservationService,
                         WorkSpaceReservationService workSpaceReservationService) {
        this.workSpaceService = workSpaceService;
        this.reservationService = reservationService;
        this.workSpaceReservationService = workSpaceReservationService;
    }

    public void processAdminAction(MenuSelector selector, DataReader reader) {
        int option = selector.chooseAdminMenuOperation();

        while (option != ADMIN_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> {
                    if (addNewWorkspace(reader.getNewSpace()) == 0) {
                        System.out.println("Workspace wasn't added!");
                    } else {
                        System.out.println("Workspace was added!");
                    }
                }
                case 2 -> {
                    if (deleteWorkSpace(reader.getWorkSpaceIdForDeletion()) == 0) {
                        System.out.println("Workspace wasn't deleted!");
                    } else {
                        System.out.println("Workspace was deleted!");
                    }
                }
                case 3 -> System.out.print(getAllWorkSpaces());
                case 4 -> {
                    if (updateWorkSpace(reader.getUpdatedSpace()) == 0) {
                        System.out.println("Workspace wasn't updated!");
                    } else {
                        System.out.println("Workspace was updated!");
                    }
                }
                case 5 -> System.out.print(getAllReservations());
                default -> System.out.println("Wrong option!");
            }

            option = selector.chooseAdminMenuOperation();
        }
    }

    public void processUserAction(MenuSelector selector, DataReader reader) {
        int option = selector.chooseCustomerMenuOperation();

        while (option != CUSTOMER_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> System.out.print(getAvailableWorkSpaces());
                case 2 -> {
                    if (addReservation(reader.getNewReservation()) == 0) {
                        System.out.println("Reservation wasn't added!");
                    } else {
                        System.out.println("Reservation was added!");
                    }
                }
                case 3 -> System.out.print(getAllReservations());
                case 4 -> {
                    if (deleteReservation(reader.getReservationIdForDeletion()) == 0) {
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

    private int addNewWorkspace(WorkSpace space) {
        return workSpaceService.addNewWorkspace(space);
    }

    private int addReservation(Reservation reservation) {
        return workSpaceReservationService.addReservation(reservation);
    }

    private int deleteWorkSpace(int spaceId) {
        return workSpaceReservationService.deleteWorkSpace(spaceId);
    }

    private int deleteReservation(int reservationId) {
        return workSpaceReservationService.deleteReservation(reservationId);
    }

    private String getAllWorkSpaces() {
        return workSpaceService.getAllWorkSpaces();
    }

    private String getAvailableWorkSpaces() {
        return workSpaceService.getAllAvailableWorkSpaces();
    }

    private String getAllReservations() {
        return reservationService.getAllReservations();
    }

    private int updateWorkSpace(WorkSpace updatedSpace) {
        return workSpaceService.updateWorkSpace(updatedSpace);
    }
}
