package plh.team1.weatherapp.crud;

import plh.team1.weatherapp.model.CurrentCondition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import plh.team1.weatherapp.model.City;

public class WeatherRepo {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public WeatherRepo() {
        this.emf = Persistence.createEntityManagerFactory("weatherapp_pu");
        this.entityManager = this.emf.createEntityManager();
    }

    public WeatherRepo(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }

    public CurrentCondition add(CurrentCondition currentCondition) {
        entityManager.getTransaction().begin();
        entityManager.persist(currentCondition);
        entityManager.getTransaction().commit();
        return currentCondition;
    }

    public CurrentCondition find(Long id) {
        return entityManager.find(CurrentCondition.class, id);
    }

    public CurrentCondition update(CurrentCondition currentCondition) {
        CurrentCondition currentConditionToUpdate = find(currentCondition.getId());
        entityManager.getTransaction().begin();
        currentConditionToUpdate.setId(currentCondition.getId());
        entityManager.getTransaction().commit();
        return currentConditionToUpdate;
    }

    public void delete(Long id) {
        CurrentCondition currentConditionToDelete = find(id);
        entityManager.getTransaction().begin();
        entityManager.remove(currentConditionToDelete);
    }

    public City addCity(Long id, City city) {

        entityManager.getTransaction().begin();
        CurrentCondition condition = find(id);
        condition.setCity(city);
        entityManager.getTransaction().commit();
        return city;

    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
