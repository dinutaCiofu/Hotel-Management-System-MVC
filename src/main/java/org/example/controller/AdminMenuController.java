package org.example.controller;

import lombok.Getter;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.AdminMenuView;
import org.example.view.CrudUsersView;
import org.example.view.FirstPageView;
import org.springframework.stereotype.Controller;

@Getter
@Controller
public class AdminMenuController {
    private final AdminMenuView adminMenuView;

    public AdminMenuController(AdminMenuView adminMenuView){
        this.adminMenuView = adminMenuView;
        adminMenuView.getCRUDUsersButton().addActionListener(e -> this.CRUDUsersListener());
//        adminMenuView.getCRUDRoomsButton().addActionListener(e -> this.CRUDRoomsListener());
        adminMenuView.getBackButton().addActionListener(e -> this.backBtnListener());
    }

//    public void CRUDRoomsListener(){
//        RoomsView roomsView = new RoomsView(UserType.ADMINISTRATOR);
//        GUIFrameSinglePointAccess.changePanel(roomsView.getMainJPanel(), "Rooms");
//    }

    public void CRUDUsersListener(){
        CrudUsersView crudUsersView = new CrudUsersView();
        GUIFrameSinglePointAccess.changePanel(crudUsersView.getMainJPanel(),"CRUD Users");
    }

    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
}
