package org.example.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.model.entities.Reservation;
import org.example.model.entities.Room;
import org.example.model.entities.User;
import org.example.model.entities.UserType;
import org.example.model.repository.ReservationRepository;
import org.example.model.repository.RoomRepository;
import org.example.model.repository.UserRepository;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;
import org.example.view.ReservationView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationView reservationView;

    public ReservationController(ReservationView reservationView) {
        this.reservationRepository = new ReservationRepository();
        this.userRepository = new UserRepository();
        this.roomRepository = new RoomRepository();
        this.reservationView = reservationView;

        reservationView.getBackButton().addActionListener(e -> this.backBtnListener());
        reservationView.getReservationButton().addActionListener(e -> this.createReservation());
        reservationView.getDownloadBtn().addActionListener(e -> this.downloadReservations());
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = List.of("ID", "roomNumber", "customerName", "customerEmail", "startDate", "endDate", "price");
        model.setRowCount(0);

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    public void setTableRows(DefaultTableModel model, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            Long id = reservation.getId();
            String roomNumber = reservation.getRoom().getNrRoom();
            String customerName = reservation.getUser().getName();
            String customerEmail = reservation.getUser().getEmail();
            Date startDate = reservation.getStartDate();
            Date endDate = reservation.getEndDate();
            Double price = reservation.getRoom().getPrice();
            model.addRow(new Object[]{id, roomNumber, customerName, customerEmail, startDate, endDate, price});
        }
    }

    public DefaultTableModel populateTable() {
        DefaultTableModel model = setTableColumns();
        List<Reservation> reservations = reservationRepository.readAll();
        setTableRows(model, reservations);
        reservationView.getTableReservations().setModel(model);
        return model;
    }

    public List<String> generateClientList() {
        return userRepository.readAll().stream().filter(user -> user.getUserType().equals(UserType.CLIENT))
                .map(User::getEmail).collect(Collectors.toList());
    }

    public void populateClientsComboBox() {
        List<String> clientEmails = generateClientList();
        reservationView.updateClientsComboBox(clientEmails);
    }

    public void populateRoomsComboBox() {
        List<String> rooms = roomRepository.readAll().stream().filter(room -> room.getIsAvailable().equals(true)).map(Room::getNrRoom).collect(Collectors.toList());
        reservationView.updateRoomsComboBox(rooms);
    }

    public void backBtnListener() {
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }

    public void createReservation() {
        String email = Objects.requireNonNull(reservationView.getClientsComboBox().getSelectedItem()).toString();
        String roomNr = Objects.requireNonNull(reservationView.getRoomsComboBox().getSelectedItem()).toString();
        String startDateStr = reservationView.getStartDateTextField().getText();
        String endDateStr = reservationView.getEndDateTextField().getText();

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStr);
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            User client = userRepository.findByEmail(email);
            Room bookedRoom = roomRepository.findRoomByNumber(roomNr);
            bookedRoom.setIsAvailable(false);
            Reservation reservation = new Reservation();
            reservation.setUser(client);
            reservation.setRoom(bookedRoom);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservationRepository.save(reservation);
            client.getReservations().add(reservation);
            bookedRoom.getReservations().add(reservation);
            userRepository.update(client);
            roomRepository.update(bookedRoom);
            reservationView.update();
        }
    }

    public void downloadReservations() {
        exportReservationsToCSV();
        exportReservationsToJSON();
        exportReservationsToXML();
        exportReservationsToDOC();
    }

    public void exportReservationsToCSV() {
        List<Reservation> reservations = reservationRepository.readAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String filename = "Reservations_" + fileDateFormat.format(new Date()) + ".csv";
        String filePath = System.getProperty("user.dir") + "/" + filename;

        try (FileWriter fileWriter = new FileWriter(filePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("\"ID\";\"Room Number\";\"Customer Name\";\"Customer Email\";\"Start Date\";\"End Date\";\"Price\"");  // Header

            for (Reservation reservation : reservations) {
                Long id = reservation.getId();
                String roomNumber = reservation.getRoom().getNrRoom();
                String customerName = reservation.getUser().getName();
                String customerEmail = reservation.getUser().getEmail();
                String startDate = dateFormat.format(reservation.getStartDate());
                String endDate = dateFormat.format(reservation.getEndDate());  // Format the end date
                Double price = reservation.getRoom().getPrice();

                System.out.println(startDate);
                printWriter.printf("\"%d\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%.2f\"\n", id, roomNumber, customerName, customerEmail, startDate, endDate, price);
            }
        } catch (IOException e) {
            System.err.println("Error while writing to CSV file: " + e.getMessage());
        }
    }

    public void exportReservationsToJSON() {
        List<Reservation> reservations = reservationRepository.readAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String filename = "Reservations_" + fileDateFormat.format(new Date()) + ".json";
        String filePath = System.getProperty("user.dir") + "/" + filename;

        JSONArray reservationsArray = new JSONArray();

        for (Reservation reservation : reservations) {
            JSONObject reservationObject = new JSONObject();
            reservationObject.put("ID", reservation.getId());
            reservationObject.put("Room Number", reservation.getRoom().getNrRoom());
            reservationObject.put("Customer Name", reservation.getUser().getName());
            reservationObject.put("Customer Email", reservation.getUser().getEmail());
            reservationObject.put("Start Date", dateFormat.format(reservation.getStartDate()));
            reservationObject.put("End Date", dateFormat.format(reservation.getEndDate()));
            reservationObject.put("Price", reservation.getRoom().getPrice());

            reservationsArray.put(reservationObject);
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(reservationsArray.toString(4));
        } catch (IOException e) {
            System.err.println("Error while writing to JSON file: " + e.getMessage());
        }
    }

    public void exportReservationsToXML() {
        List<Reservation> reservations = reservationRepository.readAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String filename = "Reservations_" + fileDateFormat.format(new Date()) + ".xml";
        String filePath = System.getProperty("user.dir") + "/" + filename;

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Reservations");
            doc.appendChild(rootElement);

            for (Reservation reservation : reservations) {
                Element reservationElement = doc.createElement("Reservation");
                rootElement.appendChild(reservationElement);

                addElement(doc, reservationElement, "ID", reservation.getId().toString());
                addElement(doc, reservationElement, "RoomNumber", reservation.getRoom().getNrRoom());
                addElement(doc, reservationElement, "CustomerName", reservation.getUser().getName());
                addElement(doc, reservationElement, "CustomerEmail", reservation.getUser().getEmail());
                addElement(doc, reservationElement, "StartDate", dateFormat.format(reservation.getStartDate()));
                addElement(doc, reservationElement, "EndDate", dateFormat.format(reservation.getEndDate()));
                addElement(doc, reservationElement, "Price", reservation.getRoom().getPrice().toString());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println("Error while writing to XML file: " + e.getMessage());
        }
    }

    private void addElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }

    public void exportReservationsToDOC() {
        List<Reservation> reservations = reservationRepository.readAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String filename = "Reservations_" + fileDateFormat.format(new Date()) + ".docx";
        String filePath = System.getProperty("user.dir") + "/" + filename;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Reservations Details");
            run.setBold(true);

            for (Reservation reservation : reservations) {
                paragraph = document.createParagraph();
                run = paragraph.createRun();
                run.setText("ID: " + reservation.getId());
                run.addBreak();
                run.setText("Room Number: " + reservation.getRoom().getNrRoom());
                run.addBreak();
                run.setText("Customer Name: " + reservation.getUser().getName());
                run.addBreak();
                run.setText("Customer Email: " + reservation.getUser().getEmail());
                run.addBreak();
                run.setText("Start Date: " + dateFormat.format(reservation.getStartDate()));
                run.addBreak();
                run.setText("End Date: " + dateFormat.format(reservation.getEndDate()));
                run.addBreak();
                run.setText("Price: " + reservation.getRoom().getPrice());
                run.addBreak();
            }

            document.write(fileOutputStream);
        } catch (Exception e) {
            System.err.println("Error while writing to DOC file: " + e.getMessage());
        }
    }
}
