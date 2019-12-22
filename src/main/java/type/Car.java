package type;

import java.sql.Date;
import java.util.Objects;

public class Car {

    private long id;
    private double value;
    private Date data;


    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", value=" + value +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                Double.compare(car.value, value) == 0 &&
                Objects.equals(data, car.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, data);
    }
}
