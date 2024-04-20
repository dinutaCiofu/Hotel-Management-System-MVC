package org.example.controller;

import org.example.model.entities.UserType;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.*;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeMenuController {
    private final EmployeeMenuView employeeMenuView;

    public EmployeeMenuController(EmployeeMenuView employeeMenuView){
        this.employeeMenuView = employeeMenuView;
    }

    public void CRUDRoomsListener(){
        RoomsView roomsView = new RoomsView(UserType.EMPLOYEE);
        GUIFrameSinglePointAccess.changePanel(roomsView.getMainPanel(), "Rooms");
    }

    public void CRUDClientsListener(){
        CrudClientsView crudClientsView = new CrudClientsView();
       GUIFrameSinglePointAccess.changePanel(crudClientsView.getJPanel(), "CRUD Clients");
    }

    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
}
