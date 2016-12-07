package pl.cendrzak.szymon.SubaruSsm;

import java.util.HashMap;

public class AvailableCarParameters {

    public HashMap<String, SubaruParameter> getImprezaParameters() {
        HashMap<String, SubaruParameter> parameters = new HashMap<String, SubaruParameter>();
        parameters.put("BatteryVoltageAddress", new SubaruParameter().setQuery(new SubaruQuery("0007")).setValueConverter(new ValueConverter().setValue(0.08).setOperation(Operator.MULTIPLY)));
        parameters.put("VehicleSpeedAddress", new SubaruParameter().setQuery(new SubaruQuery("0008")).setValueConverter(new ValueConverter().setValue(2.0).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineSpeedAddress", new SubaruParameter().setQuery(new SubaruQuery("0009")).setValueConverter(new ValueConverter().setValue(25.0).setOperation(Operator.MULTIPLY)));
        parameters.put("CoolantTempAddress", new SubaruParameter().setQuery(new SubaruQuery("000A")).setValueConverter(new ValueConverter().setValue(50.0).setOperation(Operator.SUBTRACT)));
        parameters.put("IgnitionAdvanceAddress", new SubaruParameter().setQuery(new SubaruQuery("000B")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AirflowSensorAddress", new SubaruParameter().setQuery(new SubaruQuery("000C")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineLoadAddress", new SubaruParameter().setQuery(new SubaruQuery("000D")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("ThrottlePositionAddress", new SubaruParameter().setQuery(new SubaruQuery("000F")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("InjectorPulseWidthAddress", new SubaruParameter().setQuery(new SubaruQuery("0010")).setValueConverter(new ValueConverter().setValue((double) 256 / (double) 1000).setOperation(Operator.MULTIPLY)));
        parameters.put("ISUDutyValveAddress", new SubaruParameter().setQuery(new SubaruQuery("0011")).setValueConverter(new ValueConverter().setValue((double) 100 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2AverageAddress", new SubaruParameter().setQuery(new SubaruQuery("0012")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2MinimumAddress", new SubaruParameter().setQuery(new SubaruQuery("0013")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2MaximumAddress", new SubaruParameter().setQuery(new SubaruQuery("0014")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("KnockCorrectionAddress", new SubaruParameter().setQuery(new SubaruQuery("0015")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AFCorrectionAddress", new SubaruParameter().setQuery(new SubaruQuery("001C")).setValueConverter(new ValueConverter().setValue(128.0).setOperation(Operator.SUBTRACT)));
        parameters.put("AtmosphericPressureAddress", new SubaruParameter().setQuery(new SubaruQuery("001F")).setValueConverter(new ValueConverter().setValue(8.0).setOperation(Operator.MULTIPLY)));
        return parameters;
    }

    public HashMap<String, SubaruParameter> getSvxParameters() {
        HashMap<String, SubaruParameter> parameters = new HashMap<String, SubaruParameter>();
        parameters.put("BatteryVoltageAddress", new SubaruParameter().setQuery(new SubaruQuery("12A2")).setValueConverter(new ValueConverter().setValue(0.08).setOperation(Operator.MULTIPLY)));
        parameters.put("VehicleSpeedAddress", new SubaruParameter().setQuery(new SubaruQuery("12A4")).setValueConverter(new ValueConverter().setValue(2.0).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineSpeedAddress", new SubaruParameter().setQuery(new SubaruQuery("1290")).setValueConverter(new ValueConverter().setValue(25.0).setOperation(Operator.MULTIPLY)));
        parameters.put("CoolantTempAddress", new SubaruParameter().setQuery(new SubaruQuery("1185")).setValueConverter(new ValueConverter().setValue(50.0).setOperation(Operator.SUBTRACT)));
        parameters.put("IgnitionAdvanceAddress", new SubaruParameter().setQuery(new SubaruQuery("10A2")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AirflowSensorAddress", new SubaruParameter().setQuery(new SubaruQuery("1283")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("EngineLoadAddress", new SubaruParameter().setQuery(new SubaruQuery("1282")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("ThrottlePositionAddress", new SubaruParameter().setQuery(new SubaruQuery("128C")).setValueConverter(new ValueConverter().setValue((double) 5 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("ISUDutyValveAddress", new SubaruParameter().setQuery(new SubaruQuery("129D")).setValueConverter(new ValueConverter().setValue((double) 100 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightAverageAddress", new SubaruParameter().setQuery(new SubaruQuery("1291")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightMinimumAddress", new SubaruParameter().setQuery(new SubaruQuery("12B1")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2RightMaximumAddress", new SubaruParameter().setQuery(new SubaruQuery("12B0")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftAverageAddress", new SubaruParameter().setQuery(new SubaruQuery("1292")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 512).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftMinimumAddress", new SubaruParameter().setQuery(new SubaruQuery("12B3")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("O2LeftMaximumAddress", new SubaruParameter().setQuery(new SubaruQuery("12B2")).setValueConverter(new ValueConverter().setValue((double) 5000 / (double) 256).setOperation(Operator.MULTIPLY)));
        parameters.put("KnockCorrectionAddress", new SubaruParameter().setQuery(new SubaruQuery("12A7")).setValueConverter(new ValueConverter().setValue(0.0).setOperation(Operator.ADD)));
        parameters.put("AFCorrectionAddress", new SubaruParameter().setQuery(new SubaruQuery("12AD")).setValueConverter(new ValueConverter().setValue(128.0).setOperation(Operator.SUBTRACT)));
        return parameters;
    }
}
