import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;

public class Main {

    public static void main(final String[] args)  {
//        Utilizator admin = new Utilizator();
//        admin.setEmail("iepuredenisa@gmail.com");
//        admin.setParola("angajat");
//        admin.setNume("Denisa Iepure");
//        admin.setTipUtilizator(TipUtilizator.ANGAJAT);
//        UtilizatorRepo utilizatorRepo = new UtilizatorRepo();
//        utilizatorRepo.save(admin);

//        Hotel hotel = new Hotel();
//        hotel.setAdresa("Mykonos, 846 00, Grecia");
//        hotel.setNume("Cavo Tagoo Mykonos");
//        hotel.setNrCamere(100);
//        hotelRepository.save(hotel);
//
//        hotel.setAdresa("Imerovigli 847 00, Grecia");
//        hotel.setNume("Cavo Tagoo Santorini");
//        hotel.setNrCamere(80);
//        hotelRepository.save(hotel);
/*
       HotelRepository hotelRepository = new HotelRepository();
        Hotel hotel = new Hotel();

        hotel = hotelRepository.findByNume("Cavo Tagoo Mykonos");
        if(hotel != null){
            CameraRepository cameraRepository = new CameraRepository();
            Camera camera = new Camera();
            camera.setNumarCamera("705");
            camera.setLocatie(hotel);
            camera.setPozitie(PozitieCamera.ETAJ_3);
            camera.setPret(500.0);
            camera.setEsteDisponibila(false);
            camera.setFacilitati(null);
            hotel.getCamere().add(camera);
            cameraRepository.save(camera);
            hotelRepository.update(hotel);
        }
*/
//        CameraRepository cameraRepository = new CameraRepository();
//        Camera camera = cameraRepository.findById(UUID.fromString("b2dd67eb-88fd-4f10-95b0-cc5f85daecbb"));
//        HotelRepository hotelRepository = new HotelRepository();
//        Hotel hotel = hotelRepository.findByNume("Cavo Tagoo Mykonos");
//        camera.setLocatie(hotel);
//        cameraRepository.update(camera);


        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");

    }
}