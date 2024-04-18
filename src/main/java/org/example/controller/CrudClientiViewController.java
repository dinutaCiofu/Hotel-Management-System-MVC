package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.entities.TipUtilizator;
import org.example.model.entities.Utilizator;
import org.example.model.repository.UtilizatorRepo;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;
import org.example.view.ICrudClienti;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CrudClientiViewController {
    private UtilizatorRepo utilizatorRepo = new UtilizatorRepo();
    private ICrudClienti iCrudClienti;

    public CrudClientiViewController(ICrudClienti iCrudClienti) {
        this.iCrudClienti = iCrudClienti;
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = new ArrayList<>();
        model.setRowCount(0);
        columns = List.of("ID", "nume", "email", "parola");
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
            model.addRow(new Object[]{id, nume, email, parola});
        }
    }

    public DefaultTableModel populateTable() {
        DefaultTableModel model = setTableColumns();
        List<Utilizator> clienti = utilizatorRepo.readAll().stream()
                .filter(utilizator -> utilizator.getTipUtilizator() == TipUtilizator.CLIENT)
                .collect(Collectors.toList());
        setTableRows(model, clienti);
        iCrudClienti.setTable(model);
        return model;
    }

    public void addClient() {
        String nume = iCrudClienti.getNume();
        String email = iCrudClienti.getEmail();
        String parola = iCrudClienti.getParola();
        Utilizator client = utilizatorRepo.findByEmail(email);
        if(client == null){
            client = new Utilizator();
            client.setTipUtilizator(TipUtilizator.CLIENT);
            client.setNume(nume);
            client.setEmail(email);
            client.setParola(parola);
            utilizatorRepo.save(client);
            iCrudClienti.setEmail("");
            iCrudClienti.setNume("");
            iCrudClienti.setParola("");
            populateTable();
        }else{
            System.out.println("Clientul exista deja!");
        }
    }

    public void updateClient(){
        DefaultTableModel model = (DefaultTableModel) iCrudClienti.getTable().getModel();
        int selectedRow = iCrudClienti.getTable().getSelectedRow();
        if(selectedRow != -1){
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            String nume = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String parola = (String) model.getValueAt(selectedRow, 3);
            Utilizator client = utilizatorRepo.findById(id);
            if(client != null){
                client.setNume(nume);
                client.setEmail(email);
                client.setParola(parola);
                utilizatorRepo.update(client);
            }else{
                System.out.println("Clientul nu a fost gasit.");
            }
        }
    }

    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }

    public void deleteClient(){
        DefaultTableModel model = (DefaultTableModel) iCrudClienti.getTable().getModel();
        int selectedRow = iCrudClienti.getTable().getSelectedRow();
        if(selectedRow != -1){
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            Utilizator client = utilizatorRepo.findById(id);
            if(client != null){
                utilizatorRepo.delete(client);
                model.removeRow(selectedRow);
            }else{
                System.out.println("Clientul nu a fost gasit.");
            }
        }
    }
}
