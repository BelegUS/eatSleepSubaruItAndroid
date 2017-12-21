package pl.cendrzak.szymon.SubaruSsm;

import java.util.HashMap;

public class AvailableCarParameters {

    public HashMap<String, SubaruParameter> getImprezaParameters() {
        HashMap<String, SubaruParameter> parameters = new HashMap<String, SubaruParameter>();
        parameters.put("BatteryVoltageAddress", new SubaruParameter().setQuery(new EngineQuery("0007")).setValueConverter(new ValueConverter().setValue(0.08).setOperation(Operator.MULTIPLY)));
        parameters.put("VehicleSpeedAddress", new SubaruParameter().setQuery(new EngineQuery("0008")).setValueConverter(new ValueConverter().setValue(2.0).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineSpeedAddress", new SubaruParameter().setQuery(new EngineQuery("0009")).setValueConverter(new ValueConverter().setValue(25.0).setOperation(Operator.MULTIPLY)));
        parameters.put("CoolantTempAddress", new SubaruParameter().setQuery(new EngineQuery("000A")).setValueConverter(new ValueConverter().setValue(50.0).setOperation(Operator.SUBTRACT)));
        parameters.put("IgnitionAdvanceAddress", new SubaruParameter().setQuery(new EngineQuery("000B")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AirflowSensorAddress", new SubaruParameter().setQuery(new EngineQuery("000C")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineLoadAddress", new SubaruParameter().setQuery(new EngineQuery("000D")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("ThrottlePositionAddress", new SubaruParameter().setQuery(new EngineQuery("000F")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("InjectorPulseWidthAddress", new SubaruParameter().setQuery(new EngineQuery("0010")).setValueConverter(new ValueConverter().setValue((double) 256 / (double) 1000).setOperation(Operator.MULTIPLY)));
        parameters.put("ISUDutyValveAddress", new SubaruParameter().setQuery(new EngineQuery("0011")).setValueConverter(new ValueConverter().setValue((double) 100 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2AverageAddress", new SubaruParameter().setQuery(new EngineQuery("0012")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2MinimumAddress", new SubaruParameter().setQuery(new EngineQuery("0013")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2MaximumAddress", new SubaruParameter().setQuery(new EngineQuery("0014")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("KnockCorrectionAddress", new SubaruParameter().setQuery(new EngineQuery("0015")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AFCorrectionAddress", new SubaruParameter().setQuery(new EngineQuery("001C")).setValueConverter(new ValueConverter().setValue(128.0).setOperation(Operator.SUBTRACT)));
        parameters.put("AtmosphericPressureAddress", new SubaruParameter().setQuery(new EngineQuery("001F")).setValueConverter(new ValueConverter().setValue(8.0).setOperation(Operator.MULTIPLY)));
        return parameters;
    }

    public HashMap<String, SubaruParameter> getSvxParameters() {
        HashMap<String, SubaruParameter> parameters = new HashMap<String, SubaruParameter>();
        parameters.put("BatteryVoltageAddress", new SubaruParameter().setQuery(new EngineQuery("12A2")).setValueConverter(new ValueConverter().setValue(0.08).setOperation(Operator.MULTIPLY)));
        parameters.put("VehicleSpeedAddress", new SubaruParameter().setQuery(new EngineQuery("12A4")).setValueConverter(new ValueConverter().setValue(2.0).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineSpeedAddress", new SubaruParameter().setQuery(new EngineQuery("1290")).setValueConverter(new ValueConverter().setValue(25.0).setOperation(Operator.MULTIPLY)));
        parameters.put("CoolantTempAddress", new SubaruParameter().setQuery(new EngineQuery("1185")).setValueConverter(new ValueConverter().setValue(50.0).setOperation(Operator.SUBTRACT)));
        parameters.put("IgnitionAdvanceAddress", new SubaruParameter().setQuery(new EngineQuery("10A2")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AirflowSensorAddress", new SubaruParameter().setQuery(new EngineQuery("1283")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineLoadAddress", new SubaruParameter().setQuery(new EngineQuery("1282")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("ThrottlePositionAddress", new SubaruParameter().setQuery(new EngineQuery("128C")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("ISUDutyValveAddress", new SubaruParameter().setQuery(new EngineQuery("129D")).setValueConverter(new ValueConverter().setValue((double) 100 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightAverageAddress", new SubaruParameter().setQuery(new EngineQuery("1291")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightMinimumAddress", new SubaruParameter().setQuery(new EngineQuery("12B1")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightMaximumAddress", new SubaruParameter().setQuery(new EngineQuery("12B0")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftAverageAddress", new SubaruParameter().setQuery(new EngineQuery("1292")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftMinimumAddress", new SubaruParameter().setQuery(new EngineQuery("12B3")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftMaximumAddress", new SubaruParameter().setQuery(new EngineQuery("12B2")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("KnockCorrectionAddress", new SubaruParameter().setQuery(new EngineQuery("12A7")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AFCorrectionAddress", new SubaruParameter().setQuery(new EngineQuery("12AD")).setValueConverter(new ValueConverter().setValue(128.0).setOperation(Operator.SUBTRACT)));
        parameters.put("VSS1", new SubaruParameter().setQuery(new TransmissionQuery("0019")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("VSS2", new SubaruParameter().setQuery(new TransmissionQuery("001A")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("TCUEngineRPM", new SubaruParameter().setQuery(new TransmissionQuery("001A")).setValueConverter(new ValueConverter().setValue(250.0).setOperation(Operator.MULTIPLY)));
        parameters.put("ATFTemperature", new SubaruParameter().setQuery(new TransmissionQuery("0017")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("CurrentGear", new SubaruParameter().setQuery(new TransmissionQuery("004E")).setValueConverter(new ValueConverter().setValue(1.0).setOperation(Operator.ADD)));
        return parameters;
    }
}
