package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component("engine") class Engine{} // <bean id="engine" class="com.fastcampus.ch3.Engine"/>
@Component class SuperEngine extends Engine{}
@Component class TurboEngine extends Engine{}
@Component class Door{}
@Component
class Car{
    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }

    public Car(){}

    public Car(String color, Door[] doors, Engine engine, int oil) {
        this.color = color;
        this.doors = doors;
        this.engine = engine;
        this.oil = oil;
    }

    @Value("red") String color;
    @Value("100") int oil;
    @Autowired Engine engine;
    @Autowired Door[] doors;


    public void setColor(String color) {
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }


}


public class SpringDITest {
    public static void main(String[] args){

        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        Car car = (Car) ac.getBean("car");
        Car car = ac.getBean("car", Car.class);
        Car car2 = (Car) ac.getBean(Car.class);

//        Engine engine = (Engine) ac.getBean("engine"); // byName
        Engine engine = (Engine) ac.getBean("superEngine"); //byType
        Door door = (Door) ac.getBean("door");


//        car.setColor("red");
//        car.setOil(100);
//        car.setEngine(engine);
//        car.setDoors(new Door[]{ac.getBean("door", Door.class), ac.getBean("door",Door.class)});
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);

    }
}
