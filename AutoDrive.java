package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SerialPort;

public class AutoDrive {
    private SerialPort serialPort;
    private TalonSRX talonSRX1;
    private TalonSRX talonSRX2;
    private TalonSRX talonSRX3;
    private TalonSRX talonSRX10;
    private TalonSRX talonSRX11;
    private TalonSRX talonSRX12;
    private double maxSpeed;

    private double horizontalKp = 0;
    private double horizontalKi = 0;
    private double horizontalKd = 0;
    private double horizontalIntegral = 0;
	private double horizontalDerivative = 0;
    private double horizontalLastError = 0;
    private double horizontalTarget = 0;
    private double horizontalWeight = 0;

    private double distanceKp = 0;
    private double distanceKi = 0;
    private double distanceKd = 0;
    private double distanceIntegral = 0;
	private double distanceDerivative = 0;
    private double distanceLastError = 0;
    private double distanceTarget = 0;
    private double distanceWeight = 0;

    private double angleKp = 0;
    private double angleKi = 0;
    private double angleKd = 0;
    private double angleIntegral = 0;
	private double angleDerivative = 0;
    private double angleLastError = 0;
    private double angleTarget = 0;
    private double angleWeight = 0;

    public AutoDrive(){
        serialPort = new SerialPort(115200, SerialPort.Port.kUSB);
        talonSRX1 = new TalonSRX(1);
        talonSRX2 = new TalonSRX(2);
        talonSRX3 = new TalonSRX(3);
        talonSRX10 = new TalonSRX(10);
        talonSRX11 = new TalonSRX(11);
        talonSRX12 = new TalonSRX(12);
    }

    public void setHorizontalParameter(double horizontalTarget, double horizontalKp, double horizontalKi, double horizontalKd, double horizontalWeight){
        this.horizontalTarget = horizontalTarget;
        this.horizontalKp = horizontalKp;
        this.horizontalKi = horizontalKi;
        this.horizontalKd = horizontalKd;
        this.horizontalWeight = horizontalWeight;
    }

    public void setDistanceParameter(double distanceTarget, double distanceKp, double distanceKi, double distanceKd, double distanceWeight){
        this.distanceTarget = distanceTarget;
        this.distanceKp = distanceKp;
        this.distanceKi = distanceKi;
        this.distanceKd = distanceKd;
        this.distanceWeight = distanceWeight;
    }

    public void setAngleParameter(double angleTarget, double angleKp, double angleKi, double angleKd, double angleWeight){
        this.angleTarget = angleTarget;
        this.angleKp = angleKp;
        this.angleKi = angleKi;
        this.angleKd = angleKd;
        this.angleWeight = angleWeight;
    }

    public void setMaxSpeed(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    private void setMotorSpeed(double leftSpeed, double rightSpeed){
        talonSRX1.set(ControlMode.PercentOutput, limitspeed(leftSpeed));
		talonSRX2.set(ControlMode.PercentOutput, limitspeed(leftSpeed));
        talonSRX3.set(ControlMode.PercentOutput, limitspeed(leftSpeed));
        talonSRX10.set(ControlMode.PercentOutput, limitspeed(rightSpeed));
		talonSRX11.set(ControlMode.PercentOutput, limitspeed(rightSpeed));
		talonSRX12.set(ControlMode.PercentOutput, limitspeed(rightSpeed));
    }

    private double limitspeed(double speed){
		if (speed > maxSpeed){
			speed = maxSpeed;
		}else if(speed < -maxSpeed){
			speed = -maxSpeed;
		}
		return speed;
	}

    public String reciveFromOpenMv(){
        return serialPort.readString();
    }

    public void autoDrive(){
        String revString = reciveFromOpenMv();
        int lastIndex1 = revString.lastIndexOf("[") + 1;
        int lastIndex2 = revString.lastIndexOf("]");
        String processedString = revString.substring(lastIndex1, lastIndex2);
		int index1 = processedString.indexOf(",");
		int index2 = processedString.lastIndexOf(",");
		double horizontalNow = Double.valueOf(processedString.substring(0, index1));
		double distanceNow = Double.valueOf(processedString.substring(index1+1,index2));
        double angleNow = Double.valueOf(processedString.substring(index2+1));
        
        double horizontalSpeed;
        if(horizontalNow == 0){
            horizontalSpeed = 0;
        }else{
            double horizontalError = horizontalTarget - horizontalNow;
            horizontalIntegral = horizontalIntegral + horizontalError;
            horizontalDerivative = horizontalError - horizontalLastError;
            horizontalSpeed = horizontalKp*horizontalError + horizontalKi*horizontalIntegral + horizontalKd*horizontalDerivative;
            horizontalLastError = horizontalError;
        }
         
        double distanceSpeed;
        if(distanceNow == 0){
            distanceSpeed = 0;
        }else{
            double distanceError = distanceTarget - distanceNow;
            distanceIntegral = distanceIntegral + distanceError;
            distanceDerivative = distanceError - distanceLastError;
            distanceSpeed = distanceKp*distanceError + distanceKi*distanceIntegral + distanceKd*distanceDerivative;
            distanceLastError = distanceError;
        }
          
        double angleSpeed;
        if(angleNow == 0){
            angleSpeed = 0;
        }else{
            double angleError = angleTarget - angleNow;
            angleIntegral = angleIntegral + angleError;
            angleDerivative = angleError - angleLastError;
            angleSpeed = angleKp*angleError + angleKi*angleIntegral + angleKd*angleDerivative;
            angleLastError = angleError;
        }

        double leftSpeed = horizontalSpeed*horizontalWeight + distanceSpeed*distanceWeight + angleSpeed*angleWeight;
        double rightSpeed = horizontalSpeed*horizontalWeight - distanceSpeed*distanceWeight + angleSpeed*angleWeight;

        setMotorSpeed(leftSpeed, rightSpeed);
    }
}