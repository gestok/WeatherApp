package plh.team1.weatherapp.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "CITY")
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findById", query = "SELECT c FROM City c WHERE c.id = :id"),
    @NamedQuery(name = "City.findByThisName", query = "SELECT c FROM City c WHERE c.thisName = :thisName"),
    @NamedQuery(name = "City.findAllSortedByTimesSearched", query = "SELECT c FROM City c ORDER BY c.timesSearched DESC"),
    @NamedQuery(name = "City.findByTimesSearched", query = "SELECT c FROM City c WHERE c.timesSearched = :timesSearched")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "THIS_NAME")
    private String thisName;
    @Basic(optional = false)
    @Column(name = "TIMES_SEARCHED")
    private int timesSearched;

    public City() {
    }

    public City(Integer id) {
        this.id = id;
    }

    public City(Integer id, String thisName, int timesSearched) {
        this.id = id;
        this.thisName = thisName;
        this.timesSearched = timesSearched;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public int getTimesSearched() {
        return timesSearched;
    }

    public void setTimesSearched(int timesSearched) {
        this.timesSearched = timesSearched;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uptobottom.City[ id=" + id + " ]";
    }
    
}
