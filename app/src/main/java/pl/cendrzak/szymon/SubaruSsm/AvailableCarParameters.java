package pl.cendrzak.szymon.SubaruSsm;

import java.util.HashMap;

public class AvailableCarParameters {

    public HashMap<String, String> getAvailableParameters()
    {
        HashMap<String, String> parameters = new HashMap<String,String>();
        parameters.put("BatteryVoltageAddress", "0007");
        parameters.put("VehicleSpeedAddress", "0008");
        parameters.put("EngineSpeedAddress", "0009");
        parameters.put("CoolantTempAddress", "000A");
        parameters.put("IgnitionAdvanceAddress", "000B");
        parameters.put("AirflowSensorAddress", "000C");
        parameters.put("EngineLoadAddress", "000D");
        parameters.put("ThrottlePositionAddress", "000F");
        parameters.put("InjectorPulseWidthAddress", "0010");
        parameters.put("ISUDutyValveAddress", "0011");
        parameters.put("O2AverageAddress", "0012");
        parameters.put("O2MinimumAddress", "0013");
        parameters.put("O2MaximumAddress", "0014");
        parameters.put("KnockCorrectionAddress", "0015");
        parameters.put("AFCorrectionAddress", "001C");
        parameters.put("AtmosphericPressureAddress", "001F");
        parameters.put("ManifoldPressureAddress", "0020");
        parameters.put("BoostSolenoidDutyCycleAddress", "0022");
        return parameters;
    }

}
