package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.entities.TipUtilizator;
import org.example.model.entities.Utilizator;
import org.example.model.repository.UtilizatorRepo;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.ILoginView;
import org.example.view.MeniuAdminView;
import org.example.view.MeniuAngajatView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
// metode fara parametrii
public class LoginController {
    private UtilizatorRepo utilizatorRepo = new UtilizatorRepo();
    private ILoginView iLoginView;
    public LoginController(ILoginView iLoginView){
        this.iLoginView = iLoginView;
    }
    public void login(TipUtilizator userLogged){
        // TODO: METODA CARE OBTINE TIPUL DE UTILIZATOR

        List<Utilizator> utilizatori = utilizatorRepo.readAll();
        String email = iLoginView.getEmail();
        String password = iLoginView.getPassword();

        for(Utilizator utilizator : utilizatori){
            if(utilizator.getEmail().equals(email) && utilizator.getParola().equals(password)){
                if(userLogged == TipUtilizator.ANGAJAT){
                    MeniuAngajatView meniuAngajatView = new MeniuAngajatView();
                    GUIFrameSinglePointAccess.changePanel(meniuAngajatView.getMainPanel(), "Meniu");
                } else if (userLogged == TipUtilizator.ADMINISTRATOR) {
                    MeniuAdminView meniuAdminView = new MeniuAdminView();
                    GUIFrameSinglePointAccess.changePanel(meniuAdminView.getJPanel(), "Meniu");
                }
//                RoomsView roomsView = new RoomsView(userLogged);
//                GUIFrameSinglePointAccess.changePanel(roomsView.getMainPanel(), "Rooms");
            }
        }
    }
}
