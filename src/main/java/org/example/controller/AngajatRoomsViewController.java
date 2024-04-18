package org.example.controller;

import org.example.model.entities.Camera;
import org.example.model.entities.FacilitatiCamera;
import org.example.model.entities.Hotel;
import org.example.model.entities.PozitieCameraMapper;
import org.example.model.repository.CameraRepository;
import org.example.model.repository.HotelRepository;
import org.example.view.IRoomsView;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.UUID;

@Service
public class AngajatRoomsViewController extends ClientRoomsViewController {
    private CameraRepository cameraRepository = new CameraRepository();
    private HotelRepository hotelRepository = new HotelRepository();
    private IRoomsView iRoomsView;
    public AngajatRoomsViewController(IRoomsView iRoomsView){
        super(iRoomsView);
        this.iRoomsView = iRoomsView;
    }

    private Camera cameraFromStrings(String numarCamera, String pret, String disonibilitate, String pozitie, Hotel hotel){
        Camera camera = new Camera();
        camera.setNumarCamera(numarCamera);
        camera.setPret(Double.valueOf(pret));
        camera.setEsteDisponibila(disonibilitate.equals("Disponibila") ? true : false);
        camera.setFacilitati(List.of(FacilitatiCamera.values()));
        camera.setLocatie(hotel);
        camera.setPozitie(PozitieCameraMapper.mapToPozitieCamera(pozitie));
        return camera;
    }
    public void addCamera(){
        String numarCamera = iRoomsView.getNumarCamera();
        String pret = iRoomsView.getPretCamera();
        String disonibilitate = iRoomsView.getDisponibiliate();
        String pozitie = iRoomsView.getPozitie();
        String locatie = iRoomsView.getLocatie();
        Hotel hotel = hotelRepository.findByNume(locatie);
        if(hotel != null){
//            Camera camera = new Camera();
//            camera.setNumarCamera(numarCamera);
//            camera.setPret(Double.valueOf(pret));
//            camera.setEsteDisponibila(disonibilitate.equals("Disponibila") ? true : false);
//            camera.setFacilitati(List.of(FacilitatiCamera.values()));
//            camera.setLocatie(hotel);
//            camera.setPozitie(PozitieCameraMapper.mapToPozitieCamera(pozitie));
            Camera camera = cameraFromStrings(numarCamera,pret,disonibilitate,pozitie,hotel);
            hotel.getCamere().add(camera);
            cameraRepository.save(camera);
            hotelRepository.update(hotel);
        }
    }

    public void updateCamera(){
        DefaultTableModel model = (DefaultTableModel) iRoomsView.getTable().getModel();
        //indicele randului selectat
        int selectedRow = iRoomsView.getTable().getSelectedRow();
        if(selectedRow != -1){
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            String numarCamera = (String) model.getValueAt(selectedRow, 1);
            String pret = (String) model.getValueAt(selectedRow, 2);
            String disponibilitate = (String) model.getValueAt(selectedRow, 3);
            String pozitie = (String) model.getValueAt(selectedRow, 4);
            String locatie = (String) model.getValueAt(selectedRow, 5);
            Hotel hotel = hotelRepository.findByNume(locatie);
            if(hotel != null){
                Camera camera = cameraFromStrings(numarCamera,pret,disponibilitate, pozitie, hotel);
                camera.setId(id);
                cameraRepository.update(camera);
            }

        }
    }

   public void deleteCamera(){
       DefaultTableModel model = (DefaultTableModel) iRoomsView.getTable().getModel();
       //indicele randului selectat
       int selectedRow = iRoomsView.getTable().getSelectedRow();
       if(selectedRow != -1){
           UUID id = (UUID) model.getValueAt(selectedRow, 0);
           String locatie = (String) model.getValueAt(selectedRow, 5);
           Hotel hotel = hotelRepository.findByNume(locatie);
           if(hotel != null){
               Camera camera = cameraRepository.findById(id);
               hotel.getCamere().remove(camera);
               hotelRepository.update(hotel);
               cameraRepository.delete(camera);
               model.removeRow(selectedRow);
           }
       }
    }
}
