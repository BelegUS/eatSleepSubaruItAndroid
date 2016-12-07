package pl.cendrzak.szymon.SubaruSsm;


import java.util.HashMap;

public class AvailableCars
{
    public HashMap<String, String> get() {
        HashMap<String, String> cars = new HashMap<String, String>();
        cars.put("impreza", "Subaru Impreza MY97 2.0 NA");
        cars.put("svx", "Subaru SVX MY96 3.3 NA");
        return cars;
    }
}
