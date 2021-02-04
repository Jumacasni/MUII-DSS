package motorcontroller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "motor", eager=true)
@SessionScoped
public class Motor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int motorStatus;
	
	public Motor() {
		this.motorStatus = 0;
	}
	
	public int getMotorStatus() {
		return this.motorStatus;
	}
	
	public void setMotorStatus(int motorStatus) {
		this.motorStatus = motorStatus;
	}
}