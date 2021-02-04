package motorcontroller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "motorcontroller", eager = true)
@SessionScoped
public class MotorController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int OFF = 0;
	private final int ON = 1;
	private final int SPEEDINGUP = 2;
	private final String TEXT_OFF = "Off";
	
	private String controllerStatus;
	private String colorStatus;
	private boolean acelerarButton;
	private String buttonLabel;
	private boolean buttonActive;
	
	@ManagedProperty(value="#{motor}")
	private Motor motorBean;
	
	public MotorController() {
		System.out.println("El motor está apagado");
		this.controllerStatus = "Apagado";
		this.colorStatus = "color: red";
		this.acelerarButton = true;
		this.buttonLabel = "Encender";
		this.buttonActive = false;
	}
	
	public String getControllerStatus() {
		return this.controllerStatus;
	}
	
	public String getColorStatus() {
		return this.colorStatus;
	}
	
	public String getAcelerarButton() {
		if(this.acelerarButton)
			return "true";
		
		return "false";
	}
	
	public String getButtonLabel() {
		return this.buttonLabel;
	}
	
	public String getButtonActive(){
		if(this.buttonActive)
			return "active";
		
		return "";
	}
	public void setMotorBean(Motor motorBean) {
		this.motorBean = motorBean;
	}
	
	public Motor getMotorBean() {
		return this.motorBean;
	}
	
	public void encender() {
		if(motorBean.getMotorStatus() == OFF) {
			this.controllerStatus = "MOTOR ENCENDIDO";
			this.colorStatus = "color: green";
			this.acelerarButton = false;
			this.buttonLabel = "Apagar";
			this.buttonActive = true;
			motorBean.setMotorStatus(ON);
		}
		else if(motorBean.getMotorStatus() == ON || motorBean.getMotorStatus() == SPEEDINGUP) {
			this.controllerStatus = "MOTOR APAGADO";
			this.colorStatus = "color: red";
			this.acelerarButton = true;
			this.buttonLabel = "Encender";
			this.buttonActive = false;
			motorBean.setMotorStatus(OFF);
		}
	}
	
	public void acelerar() {
		this.controllerStatus = "MOTOR ACELERANDO";
		this.colorStatus = "color: orange";
		motorBean.setMotorStatus(SPEEDINGUP);
	}
}