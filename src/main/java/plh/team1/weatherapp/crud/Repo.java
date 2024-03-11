package plh.team1.weatherapp.crud;

import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.serialization.WeatherData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import plh.team1.weatherapp.model.WeatherDataModel;

public class Repo implements AutoCloseable{

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public Repo() {
        this.emf = Persistence.createEntityManagerFactory("weatherapp_pu");
        this.entityManager = this.emf.createEntityManager();

    }

    public Repo(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }
    
    /**
     * Helper method that Uses Name and Country to prevent dublicates
     * @param cityModel
     * @return null or list
     */
    public List<CityModel> findCity(CityModel cityModel) {
        Query query = entityManager.createQuery(
                "SELECT c FROM CityModel c "
                + "WHERE c.cityName = :name "
                + "and c.countryName = :country"
        );
        query.setParameter("name", cityModel.getCityName());
        query.setParameter("country", cityModel.getCountryName());
        List<CityModel> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    /** adds a city to the database
     * If we intend to use the pojo later in our code the correct 
     * practice of using the method is: citydummy = addCity(citydummy);
     * @param cityToBeAdded
     * @return cityModel 
     */
    public CityModel addCity(CityModel cityToBeAdded) {
        entityManager.getTransaction().begin();
        if (findCity(cityToBeAdded) == null) { // city doesn't exist in the database
            cityToBeAdded.setTimesSearched(1);
        } else { // city exists in the database
            cityToBeAdded = findCity(cityToBeAdded).get(0); //ensures id stays the same 
            cityToBeAdded.incrementTimesSearched();
        }
        entityManager.persist(cityToBeAdded);
        entityManager.getTransaction().commit();

        return cityToBeAdded;
    }
    
    /**
     * Handles the isFavourite field
     * @param id
     * @param bool 
     */
    public void setFavourite(Long id, boolean bool) {
        CityModel cityModel = findCity(id);
        entityManager.getTransaction().begin();
        cityModel.setFavourite(bool);
        entityManager.persist(cityModel);
        entityManager.getTransaction().commit();
    }


    /**
     * Adds a weatherDataModel to the db
     * @param weatherDataModel
     * @return 
     */
    public WeatherDataModel addWeatherData(WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        entityManager.persist(weatherDataModel);
        entityManager.getTransaction().commit();
        return weatherDataModel;
    }

    /**
     * Associates the weatherDataModel entity with the city entity
     * @param id
     * @param weatherDataModel 
     */
    public void addWeatherDataToCity(Long id, WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        CityModel city = findCity(id);
        if (city != null) {
            city.getWeatherDatas().add(weatherDataModel);
        }
        weatherDataModel.setCityModel(city);
        entityManager.persist(weatherDataModel);
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    /**
     * searches for a cityModel based on given ID
     * @param id
     * @return cityModel
     */
    public CityModel findCity(Long id) {
        return entityManager.find(CityModel.class, id);
    }

    /**
     * Searches for a weatherDataModel based on a given ID
     * @param id
     * @return 
     */
    public WeatherDataModel findWeatherData(Long id) {
        return entityManager.find(WeatherDataModel.class, id);
    }

     
    /**
     * Deletes a weatherData record from the db
     * @param weatherDataModel 
     */
    public void deleteWeatherData(WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        entityManager.remove(weatherDataModel);
        entityManager.getTransaction().commit();
    }
    /**
     * Deletes all WeatherData associated with provided cityId
     * @param id 
     */
    public void deleteCityData(Long id) {
        entityManager.getTransaction().begin();
        CityModel city = findCity(id);
        Query query = entityManager.createQuery(
                "DELETE FROM WeatherDataModel w "
                + "WHERE w.cityModel.id = :id");
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();
    }
    
    /**
     * Lists all WeatherData corresponding to a city referred by id
     * @param id
     * @return 
     */
    public List<WeatherDataModel> findByCity(Long id) {
        entityManager.getTransaction().begin();
        CityModel city = findCity(id);
        Query query = entityManager.createQuery(
                "SELECT a FROM WeatherDataModel a"
                + " JOIN a.cityModel b "
                + "where b.id = :id"
        );
        query.setParameter("id", id);
        
        city.incrementTimesSearched();
        entityManager.persist(city);
        entityManager.getTransaction().commit();
        return query.getResultList();
    }

    /**
     * Returns a weatherDataModel object 
     * @param id
     * @return 
     */
    public WeatherDataModel findById(Long id) {
        Query query = entityManager.createNamedQuery("find weatherdata by id");
        query.setParameter("id", id);
        return (WeatherDataModel) query.getSingleResult();

    }
    
    /**
     * Updates temp field
     * @param id
     * @param temp
     * @return 
     */
    public WeatherDataModel updateTemperature(Long id, String temp) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update WeatherDataModel set temperature = :temp where id = :id");
        query.setParameter("id", id);
        query.setParameter("temp", temp);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return findById(id);
    }
    
    /**
     * Updates uvIndex field
     * @param id
     * @param uvIndex
     * @return 
     */
    public WeatherDataModel updateUvIndex(Long id, String uvIndex) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update WeatherDataModel set uvIndex = :uvIndex where id = :id");
        query.setParameter("id", id);
        query.setParameter("uvIndex", uvIndex);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return findById(id);
    }
    
  
    /**
     * Updates weatherDesc field
     * @param id
     * @param weatherDesc
     * @return 
     */
    public WeatherDataModel updateWeatherDesc(Long id, String weatherDesc) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update WeatherDataModel set weatherDesc = :weatherDesc where id = :id");
        query.setParameter("id", id);
        query.setParameter("weatherDesc", weatherDesc);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return findById(id);
    }
    
    /**
     * Updates windSpeed
     * @param id
     * @param windspeed
     * @return 
     */
    public WeatherDataModel updateWindSpeed(Long id, String windspeed) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update WeatherDataModel set windspeed = :windspeed where id = :id");
        query.setParameter("id", id);
        query.setParameter("windspeed", windspeed);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return findById(id);
    }
    
    /**
     * updates Date
     * @param id
     * @param date
     * @return 
     */
    public WeatherDataModel updateDate(Long id, Date date) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update WeatherDataModel set date = :date where id = :id");
        query.setParameter("id", id);
        query.setParameter("date", date);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return findById(id);
    }
    /**
     * Lists all cities that have been searched at list once
     * @return 
     */
    public List<CityModel> getCities(){
        return entityManager.createQuery("Select c from CityModel c", CityModel.class).getResultList();
    }
    
    @Override
    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
