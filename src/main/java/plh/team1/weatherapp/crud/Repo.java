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

public class Repo {

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

    // adds a cityModel object to the CITY table
    public CityModel addCity(CityModel cityToBeAdded) {
        entityManager.getTransaction().begin();
        if (entityManager.contains(cityToBeAdded)) {
            long id = findCity(cityToBeAdded).get(0).getId();
            cityToBeAdded.setId(id);
            cityToBeAdded.incrementTimesSearched();
            entityManager.merge(cityToBeAdded);
        } else {
            cityToBeAdded.setTimesSearched(0);
            entityManager.persist(cityToBeAdded);
        }


        return cityToBeAdded;
    }

    public void setFavourite(Long id, boolean bool) {
        CityModel cityModel = findCity(id);
        entityManager.getTransaction().begin();
        cityModel.setFavourite(bool);
        entityManager.persist(cityModel);
        entityManager.getTransaction().commit();
    }

    /* for testing purposes only
    should not be used as it defeats the purpose of jpa 
     */
    public WeatherDataModel addData(WeatherData weatherData) {
        WeatherDataModel weatherDataModel = new WeatherDataModel(weatherData);
        CityModel cityModel = new CityModel(weatherData);
        if (findCity(cityModel) == null) {
            addCity(cityModel);
            cityModel.getWeatherDatas().add(weatherDataModel);
        } else {
            cityModel = findCity(cityModel).get(0);
            cityModel.getWeatherDatas().add(weatherDataModel);
        }
        entityManager.getTransaction().begin();
        entityManager.persist(weatherDataModel);
        entityManager.persist(cityModel);
        entityManager.getTransaction().commit();
        return weatherDataModel;
    }

    //adds a weatherData object to the WEATHERDATA table
    public WeatherDataModel addWeatherData(WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        entityManager.persist(weatherDataModel);
        entityManager.getTransaction().commit();
        return weatherDataModel;
    }

    //bidirectional addsCityToWeatherData as well
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

    // returns a cityModel object based on id
    public CityModel findCity(Long id) {
        return entityManager.find(CityModel.class, id);
    }

    // returns a weatherDataModel object based on id
    public WeatherDataModel findWeatherData(Long id) {
        return entityManager.find(WeatherDataModel.class, id);
    }

    // deletes a weatherData record 
    public void deleteWeatherData(WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        entityManager.remove(weatherDataModel);
        entityManager.getTransaction().commit();
    }

    //delete WeatherData corresponding to a city referred by id
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

    //Lists WeatherData corresponding to a city referred by id
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

    //returns a single WeatherDataModel object queried by id.
    public WeatherDataModel findById(Long id) {
        Query query = entityManager.createNamedQuery("find weatherdata by id");
        query.setParameter("id", id);
        return (WeatherDataModel) query.getSingleResult();

    }

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

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
