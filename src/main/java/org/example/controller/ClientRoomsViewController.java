package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.entities.Camera;
import org.example.model.entities.FacilitatiCamera;
import org.example.model.entities.Hotel;
import org.example.model.entities.PozitieCameraMapper;
import org.example.model.repository.CameraRepository;
import org.example.model.repository.HotelRepository;
import org.example.view.IRoomsView;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientRoomsViewController {
    private CameraRepository cameraRepository = new CameraRepository();
    private HotelRepository hotelRepository = new HotelRepository();
    private IRoomsView iRoomsView;

    public ClientRoomsViewController(IRoomsView iRoomsView) {
        this.iRoomsView = iRoomsView;
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = new ArrayList<>();
        model.setRowCount(0);
        columns = List.of("ID", "numarCamera", "pret", "esteDisponibila", "pozitie", "locatie");
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

    private String getLocatieCamera(Camera camera) {

        if (camera.getLocatie() != null) {
            System.out.println("Exista hotel");
            UUID hotelId = camera.getLocatie().getId();
            Hotel hotel = hotelRepository.findById(hotelId);
            if (hotel != null) {
                return hotel.getNume();
            }
        } else {
            System.out.println("nu exista");
            System.out.println(camera.getLocatie());
        }
        return null;
    }

    public void setTableRows(DefaultTableModel model, List<Camera> camere) {
        for (Camera camera : camere) {
            UUID id = camera.getId();
            String numarCamera = camera.getNumarCamera();
            String pret = String.valueOf(camera.getPret());
            String esteDisponibila = camera.getEsteDisponibila() ? "Disponibila" : "Indisponibila";
            String pozitie = PozitieCameraMapper.mapToPozitieString(camera.getPozitie());
            String locatie = getLocatieCamera(camera) != null ? getLocatieCamera(camera) : "Nedefinit";

            model.addRow(new Object[]{id, numarCamera, pret, esteDisponibila, pozitie, locatie});
        }
    }

    public DefaultTableModel populateTableAferSearch() {
        DefaultTableModel model = setTableColumns();
        List<Camera> camere = cameraRepository.readAll();
        // search options
        if (iRoomsView.getSelectedSearchByValue().equals("Numar")) {
            camere.sort(Comparator.comparing(Camera::getNumarCamera));
        } else if (iRoomsView.getSelectedSearchByValue().equals("Locatie")) {
            camere.sort(Comparator.comparing(Camera::getLocatie));
        }
        setTableRows(model, camere);
        iRoomsView.setTable(model);
        return model;
    }

    public DefaultTableModel populateTableAfterFilter(List<FacilitatiCamera> facilities) {
        DefaultTableModel model = setTableColumns();
        List<Camera> camere = cameraRepository.readAll();

        if (!facilities.isEmpty() && iRoomsView.getSelectedFilterByValue().equals("Facilitati")) {
            camere = camere.stream()
                    .filter(camera -> camera.getFacilitati().containsAll(facilities))
                    .collect(Collectors.toList());
        } else if (iRoomsView.getSelectedFilterByValue().equals("Disponibilitate")) {
            camere = camere.stream()
                    .filter(camera -> camera.getEsteDisponibila().equals(true))
                    .collect(Collectors.toList());
        }else if(iRoomsView.getSelectedFilterByValue().equals("Pret")){
            camere.sort(Comparator.comparing(Camera::getPret));
        }else{
            String selectedPosition = iRoomsView.getSelectedFilterByValue();
            camere = camere.stream()
                    .filter(camera -> PozitieCameraMapper.mapToPozitieString(camera.getPozitie()).equalsIgnoreCase(selectedPosition))
                    .collect(Collectors.toList());
        }

        setTableRows(model, camere);
        iRoomsView.setTable(model);
        return model;
    }
}
