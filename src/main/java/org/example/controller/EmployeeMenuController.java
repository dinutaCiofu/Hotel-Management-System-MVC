package org.example.controller;

import lombok.Getter;
import org.example.model.entities.UserType;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.*;
import org.springframework.stereotype.Controller;

@Getter
@Controller
public class EmployeeMenuController {
    private final EmployeeMenuView employeeMenuView;

    public EmployeeMenuController(EmployeeMenuView employeeMenuView){
        this.employeeMenuView = employeeMenuView;
        employeeMenuView.getCRUDRoomsButton().addActionListener(e -> this.CRUDRoomsListener());
        employeeMenuView.getCRUDClientsButton().addActionListener(e -> this.CRUDClientsListener());
        employeeMenuView.getBackButton().addActionListener(e -> this.backBtnListener());
        employeeMenuView.getReservationButton().addActionListener(e -> this.reservationBtnListener());
        employeeMenuView.getStatisticsBtn().addActionListener(e -> this.statisticsBtnListener());
    }

    public void CRUDRoomsListener(){
        RoomsView roomsView = new RoomsView(UserType.EMPLOYEE);
        GUIFrameSinglePointAccess.changePanel(roomsView.getMainJPanel(), "Rooms");
    }

    public void CRUDClientsListener(){
        CrudClientsView crudClientsView = new CrudClientsView();
       GUIFrameSinglePointAccess.changePanel(crudClientsView.getMainJPanel(), "CRUD Clients");
    }

    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }

    public void reservationBtnListener(){
        ReservationView reservationView = new ReservationView();
        GUIFrameSinglePointAccess.changePanel(reservationView.getMainPanel(), "Reservation page");
    }

    public void statisticsBtnListener(){
        RoomStatisticsView roomStatisticsView = new RoomStatisticsView();
        GUIFrameSinglePointAccess.changePanel(roomStatisticsView, "Statistics");
    }
}
