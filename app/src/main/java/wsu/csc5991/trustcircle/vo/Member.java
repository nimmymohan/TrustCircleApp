package wsu.csc5991.trustcircle.vo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by sasidhav on 7/15/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

	private int id;
	private String firstName;
	private String lastName;
	private int pin;
	private int mobileNumber;
	private Circle circle;
	private List<Event> events;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}