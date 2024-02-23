/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plh.team1.weatherapp.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import plh.team1.weatherapp.model.City;
import plh.team1.weatherapp.model.CurrentCondition;

public class CityRepo {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public CityRepo() {
        this.emf = Persistence.createEntityManagerFactory("weatherapp_pu");
        this.entityManager = this.emf.createEntityManager();

    }

    public CityRepo(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }

    public City addCity(City city) {
        entityManager.getTransaction().begin();
        entityManager.persist(city);
        entityManager.getTransaction().commit();
        return city;
    }

    public City find(Long id) {
        return entityManager.find(City.class, id);
    }

    public void delete(City city) {
        entityManager.getTransaction().begin();
        entityManager.remove(city);
        entityManager.getTransaction().commit();
    }

    public City update(City city) {
        City cityToUpdate = find(city.getId());
        entityManager.getTransaction().begin();
        cityToUpdate.setName(city.getName());
        cityToUpdate.setCountry(city.getCountry());
        entityManager.getTransaction().commit();
        return cityToUpdate;
    }

    
    public void addCondition(Long id, CurrentCondition currentCondition){
        entityManager.getTransaction().begin();
        City city = find(id);
        if (city != null){
            city.getCurrentConditions().add(currentCondition);            
            
        }
        entityManager.persist(city);
        entityManager.getTransaction().commit();
                
    }
    
    public void removeCondition(Long id, CurrentCondition currentCondition){
        entityManager.getTransaction().begin();
        City city = find(id);
        if(city != null){
            city.getCurrentConditions().remove(currentCondition);
        }
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
