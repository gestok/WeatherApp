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
import plh.team1.weatherapp.model.WeatherData;

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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    private int cityId;

    public City getCtData() {
        return ctData;
    }

    public void setCtData(City ctData) {
        this.ctData = ctData;
    }

    public WeatherData getWdData() {
        return wdData;
    }

    public void setWdData(WeatherData wdData) {
        this.wdData = wdData;
    }
    private City ctData;
    private WeatherData wdData;
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

    public void addCity(City cityToBeAdded) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int cityId = -1;

        try {
            tx.begin();

            TypedQuery<City> query = em.createNamedQuery("City.findByThisName", City.class);
            query.setParameter("thisName", cityToBeAdded.getThisName());
            List<City> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                // City exists, increment timesSearched
                City existingCity = resultList.get(0);
                existingCity.setTimesSearched(existingCity.getTimesSearched() + 1);
                cityId = existingCity.getId();
                em.merge(existingCity);
                
            } else {
                // City does not exist, add it with timesSearched 1
                cityToBeAdded.setTimesSearched(1);
                em.persist(cityToBeAdded);
                em.flush();
                cityId = cityToBeAdded.getId();
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
        setCityId(cityId);
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
    
    //adds a weatherData object to the WEATHERDATA table
    public WeatherData addWeatherData(WeatherData weatherData) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        em.getTransaction().begin();
        em.persist(weatherData);
        em.getTransaction().commit();
        return weatherData;
    }

    
    // returns a cityModel object based on id
    public City findCity(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(City.class, id);
    }

    // returns a weatherDataModel object based on id
    public WeatherData findWeatherData(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(WeatherData.class, id);
    }

    // deletes a weatherData record 
    public void deleteWeatherData(WeatherData weatherData) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.remove(weatherData);
        tx.commit();
    }

    
//    public WeatherDataModel updateTemperature(Long id, String temp) {
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("Update WeatherDataModel set temperature = :temp where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("temp", temp);
//        query.executeUpdate();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        return findById(id);
//    }
//
//    public WeatherDataModel updateUvIndex(Long id, String uvIndex) {
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("Update WeatherDataModel set uvIndex = :uvIndex where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("uvIndex", uvIndex);
//        query.executeUpdate();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        return findById(id);
//    }
//
//    public WeatherDataModel updateWeatherDesc(Long id, String weatherDesc) {
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("Update WeatherDataModel set weatherDesc = :weatherDesc where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("weatherDesc", weatherDesc);
//        query.executeUpdate();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        return findById(id);
//    }
//
//    public WeatherDataModel updateWindSpeed(Long id, String windspeed) {
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("Update WeatherDataModel set windspeed = :windspeed where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("windspeed", windspeed);
//        query.executeUpdate();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        return findById(id);
//    }
//
//    public WeatherDataModel updateDate(Long id, Date date) {
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("Update WeatherDataModel set date = :date where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("date", date);
//        query.executeUpdate();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        return findById(id);
//    }
    

}
