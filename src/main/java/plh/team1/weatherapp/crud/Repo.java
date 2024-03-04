package plh.team1.weatherapp.crud;

import plh.team1.weatherapp.model.CityModel;
import plh.team1.weatherapp.serialization.WeatherData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
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

    // searches city persistance unit by cityName, coutnryName to find dublicates
    // the findCity method performs an Entity search only by identifier(ID) 
    // TODO: maybe add a third field like longitude to ensure edge cases
    // Entities inside the JPQL queries are referenced by their class names
    public List<CityModel> findCity(CityModel cityModel) {
        
        Query query = entityManager.createQuery("SELECT c FROM CityModel c WHERE c.cityName = :name and c.countryName = :country");
        query.setParameter("name", cityModel.getCityName());
        query.setParameter("country", cityModel.getCountryName());
        List<CityModel> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList;

    }
    


    // adds a city object to the CITY table
    public CityModel addCity(CityModel cityModel) {
        if (findCity(cityModel) == null ) {
            entityManager.getTransaction().begin();
            entityManager.persist(cityModel);
            entityManager.getTransaction().commit();
        }
        return cityModel;
    }
    
   
    
    public WeatherDataModel addData(WeatherData weatherData) {
        WeatherDataModel weatherDataModel = new WeatherDataModel(weatherData);
        CityModel cityModel = new CityModel(weatherData);
        //needs polishing, entity manager starts on addCity method as well
        if (findCity(cityModel)==null){
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

    public void addWeatherDataToCity(Long id, WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        CityModel city = findCity(id);
        if (city != null) {
            city.getWeatherDatas().add(weatherDataModel);
        }
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    // returns a cityModel obejct based on id
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

    // deletes all the weatheData records corresponding to a specific city
    public void removeWeatherData(Long id, WeatherDataModel weatherDataModel) {
        entityManager.getTransaction().begin();
        CityModel city = findCity(id);
        if (city != null) {
            city.getWeatherDatas().remove(weatherDataModel);
        }
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
