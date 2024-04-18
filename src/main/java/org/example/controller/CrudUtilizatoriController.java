package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.entities.TipUtilizator;
import org.example.model.entities.TipUtilizatorMapper;
import org.example.model.entities.Utilizator;
import org.example.model.repository.UtilizatorRepo;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;
import org.example.view.ICrudUtilizatoriView;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CrudUtilizatoriController {
    private UtilizatorRepo utilizatorRepo = new UtilizatorRepo();
    private ICrudUtilizatoriView iCrudUtilizatoriView ;
    public CrudUtilizatoriController(ICrudUtilizatoriView iCrudUtilizatoriView){
        this.iCrudUtilizatoriView = iCrudUtilizatoriView;
            populateTable();
    }
    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = new ArrayList<>();
        model.setRowCount(0);
        columns = List.of("ID", "nume", "email", "parola", "tip utilizator");
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }
    public void setTableRows(DefaultTableModel model, List<Utilizator> utilizatori) {
        for (Utilizator utilizator : utilizatori) {
            UUID id = utilizator.getId();
            String nume = utilizator.getNume();
            String email = utilizator.getEmail();
            String parola = utilizator.getParola();
            String tipUtilizator = TipUtilizatorMapper.mapToTipUtilizatorString(utilizator.getTipUtilizator());
            model.addRow(new Object[]{id, nume, email, parola,tipUtilizator});
        }
    }
    public DefaultTableModel populateTable() {
        DefaultTableModel model = setTableColumns();
        List<Utilizator> utilizators = utilizatorRepo.readAll().stream()
                .filter(utilizator -> utilizator.getTipUtilizator() == TipUtilizator.ANGAJAT || utilizator.getTipUtilizator() == TipUtilizator.ADMINISTRATOR)
                .collect(Collectors.toList());
        setTableRows(model, utilizators);
        iCrudUtilizatoriView.setTable(model);
        return model;
    }

    public void addUtilizator() {
        String nume = iCrudUtilizatoriView.getNume();
        String email = iCrudUtilizatoriView.getEmail();
        String parola = iCrudUtilizatoriView.getParola();
        TipUtilizator tipUtilizator = TipUtilizatorMapper.mapToTipUtilizator(iCrudUtilizatoriView.getTipUtilizator());
        Utilizator user = utilizatorRepo.findByEmail(email);
        if(user == null){
            user = new Utilizator();
            user.setTipUtilizator(TipUtilizator.CLIENT);
            user.setNume(nume);
            user.setEmail(email);
            user.setParola(parola);
            user.setTipUtilizator(tipUtilizator);
            utilizatorRepo.save(user);
            iCrudUtilizatoriView.setEmail("");
            iCrudUtilizatoriView.setNume("");
            iCrudUtilizatoriView.setParola("");
            iCrudUtilizatoriView.setTipUtilizator("");
            populateTable();
        }else{
            System.out.println("Utilizatorul exista deja!");
        }
    }
    public void updateUtilizator(){
        DefaultTableModel model = (DefaultTableModel) iCrudUtilizatoriView.getTable().getModel();
        int selectedRow = iCrudUtilizatoriView.getTable().getSelectedRow();
        if(selectedRow != -1){
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            String nume = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String parola = (String) model.getValueAt(selectedRow, 3);
            String tipUtilizator = (String) model.getValueAt(selectedRow, 4);
            Utilizator user = utilizatorRepo.findById(id);
            if(user != null){
                user.setNume(nume);
                user.setEmail(email);
                user.setParola(parola);
                user.setTipUtilizator(TipUtilizatorMapper.mapToTipUtilizator(tipUtilizator));
                utilizatorRepo.update(user);
            }else{
                System.out.println("Utilizatorul nu a fost gasit.");
            }
        }
    }
    public void deleteUtilizator(){
        DefaultTableModel model = (DefaultTableModel) iCrudUtilizatoriView.getTable().getModel();
        int selectedRow = iCrudUtilizatoriView.getTable().getSelectedRow();
        if(selectedRow != -1){
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            Utilizator utilizator = utilizatorRepo.findById(id);
            if(utilizator != null){
                utilizatorRepo.delete(utilizator);
                model.removeRow(selectedRow);
            }else{
                System.out.println("Utilizatorul nu a fost gasit.");
            }
        }
    }
}
