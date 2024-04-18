package org.example.controller;

import org.example.model.entities.TipUtilizator;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.CrudUtilizatoriView;
import org.example.view.FirstPageView;
import org.example.view.IMeniuAdminView;
import org.example.view.RoomsView;

public class MeniuAdminViewController {
    private IMeniuAdminView iMeniuAdminView;
    public MeniuAdminViewController(IMeniuAdminView iMeniuAdminView){
        this.iMeniuAdminView = iMeniuAdminView;
    }
    public void CRUDCamereListener(){
        RoomsView roomsView = new RoomsView(TipUtilizator.ADMINISTRATOR);
        GUIFrameSinglePointAccess.changePanel(roomsView.getMainPanel(), "Rooms");
    }
    public void CRUDUtilizatoriListener(){
        CrudUtilizatoriView crudUtilizatoriView = new CrudUtilizatoriView();
        GUIFrameSinglePointAccess.changePanel(crudUtilizatoriView.getPanel(),"CRUD Utilizatori");
    }
    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
}
