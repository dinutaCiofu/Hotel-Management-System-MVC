import org.example.model.entities.Hotel;
import org.example.model.repository.HibernateUtil;
import org.example.model.repository.HotelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


public class ApplicationTest {
    HotelRepository hotelRepository = new HotelRepository();

    @Test
    public void testCreate(){
        // Create a mock session object
        Session mockSession = mock(Session.class);

        // Create a mock transaction object
        Transaction mockTransaction = mock(Transaction.class);

        // Configure the session to return the mock transaction
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        // Create a test object
        Hotel hotel = new Hotel();
        hotel.setId(UUID.randomUUID());
        hotel.setNume("Cavo Tagoo");

        // Set the session factory of the DAO to use the mock session
        HibernateUtil.setSessionFactory(mock(SessionFactory.class));
        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(mockSession);

        // Call the create method with the test object
        hotelRepository.save(hotel);

        // Verify that the session was used to save the test object
        verify(mockSession, times(1)).save(hotel);

        // Verify that the transaction was committed
        verify(mockTransaction, times(1)).commit();
    }

    @Test
    public void testUpdate() {
        // Create a mock session object
        Session mockSession = mock(Session.class);

        // Create a mock transaction object
        Transaction mockTransaction = mock(Transaction.class);

        // Configure the session to return the mock transaction
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        Hotel hotel = new Hotel();
        hotel.setId(UUID.randomUUID());
        hotel.setNume("CavoTagoo");

        // Set the session factory of the DAO to use the mock session
        HibernateUtil.setSessionFactory(mock(SessionFactory.class));
        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(mockSession);

        // Call the update method with the test object
        hotelRepository.update(hotel);

        // Verify that the session was used to update the test object
        verify(mockSession, times(1)).update(hotel);

        // Verify that the transaction was committed
        verify(mockTransaction, times(1)).commit();
    }

    @Test
    public void testDelete() {
        // Create a mock session object
        Session mockSession = mock(Session.class);

        // Create a mock transaction object
        Transaction mockTransaction = mock(Transaction.class);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        Hotel hotel = new Hotel();
        UUID id = UUID.randomUUID();
        hotel.setId(id);
        hotel.setNume("CavoTagoo");

        // mock the session factory and openSession() method
        HibernateUtil.setSessionFactory(mock(SessionFactory.class));
        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(mockSession);

        // mock the get() method to return an instance of YourClass with id
        Mockito.when(mockSession.get(Hotel.class, id)).thenReturn(hotel);

        // call the delete() method
        hotelRepository.delete(hotel);

        // verify that session.delete() method was called with the right argument
        Mockito.verify(mockSession).delete(hotel);

        // verify that the transaction.commit() method was called
        Mockito.verify(mockTransaction).commit();

        // verify that session.close() method was called
        Mockito.verify(mockSession).close();
    }

    @Test
    public void testGet() {
        // Create a mock session object
        Session mockSession = mock(Session.class);

        // Create a mock transaction object
        Transaction mockTransaction = mock(Transaction.class);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        Hotel hotel = new Hotel();
        UUID id = UUID.randomUUID();
        hotel.setId(id);
        hotel.setNume("CavoTagoo");

        // Configure the session to return the test object when get() is called
        when(mockSession.get(Hotel.class, id)).thenReturn(hotel);

        // Set the session factory of the DAO to use the mock session
        HibernateUtil.setSessionFactory(mock(SessionFactory.class));
        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(mockSession);

        // Call the get() method with the test ID
        Hotel founded = hotelRepository.findById(id);

        // Verify that the session was used to get the test object
        verify(mockSession, times(1)).get(Hotel.class, id);

        // Verify that the transaction was committed
        verify(mockTransaction, times(1)).commit();

        // Verify that the returned object matches the test object
        assertEquals(hotel, founded);
    }
}
