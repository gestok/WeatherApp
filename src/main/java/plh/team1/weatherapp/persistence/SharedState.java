package plh.team1.weatherapp.persistence;

import plh.team1.weatherapp.info.WeatherDataInfo;
import plh.team1.weatherapp.info.CityInfo;
import plh.team1.weatherapp.utils.Utilities;
import plh.team1.weatherapp.model.City;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * A model state class that retains application data between scenes. The model
 * decouples the controllers from each other and makes the data (state)
 * management more centralized and easier to handle.
 */
public class SharedState {

    private static SharedState instance;
    private static EntityManagerFactory emf;

    private CityInfo city;
    private WeatherDataInfo data;
    private Utilities utilities = new Utilities();
    private int currentIndex = 0;

    private SharedState() {
    }

    /**
     * Returns a Singleton instance that shares the same data to the callers.
     *
     * @return SharedState The same instance of shared state.
     */
    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    /**
     * Stores the selected city data.
     *
     * @param city
     */
    public synchronized void setCity(CityInfo city) {
        this.city = city;
    }

    /**
     * Returns data about selected city.
     *
     * @return City
     */
    public CityInfo getCity() {
        return this.city;
    }

    /**
     * Stores the weather data response for reuse.
     *
     * @param data
     */
    public synchronized void setData(WeatherDataInfo data) {
        this.data = data;
    }

    /**
     * Returns the weather data response.
     *
     * @return WeatherData
     */
    public WeatherDataInfo getData() {
        return this.data;
    }

    public EntityManagerFactory getEmf() {
        return this.emf;
    }

    public synchronized void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static void createDB() {
        emf = Persistence.createEntityManagerFactory("weatherdataPU");
        System.out.println("Success opening Database");
    }
    
    public static void closeDB() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("Success closing Database");
        }
        
    }

    public static void addCity(City cityToBeAdded) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<City> query = em.createNamedQuery("City.findByThisName", City.class);
            query.setParameter("thisName", cityToBeAdded.getThisName());
            List<City> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                // City exists, increment timesSearched
                City existingCity = resultList.get(0);
                existingCity.setTimesSearched(existingCity.getTimesSearched() + 1);
                em.merge(existingCity);
                
            } else {
                // City does not exist, add it with timesSearched 1
                cityToBeAdded.setTimesSearched(1);
                em.persist(cityToBeAdded);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            // Log or handle the exception appropriately
            e.printStackTrace();

        } finally {
            em.close();
        }
    }
    
    public static void deleteCity(int cityId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<City> query = em.createNamedQuery("City.findById", City.class);
            query.setParameter("id", cityId);
            List<City> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                // City exists, delete
                City existingCity = resultList.get(0);
                em.remove(existingCity);                
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            // Log or handle the exception appropriately
            e.printStackTrace();

        } finally {
            em.close();
        }
    }
    

}
