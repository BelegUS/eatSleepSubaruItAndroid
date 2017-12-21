package pl.cendrzak.szymon.SubaruSsm;

public abstract class ParameterQuery extends SubaruQuery {
    String queryString;

    public byte getMsb()
    {
        String stringMsb = queryString.substring(0,2);
        return Integer.decode("0x"+stringMsb).byteValue();
    }

    public byte getLsb()
    {
        String stringLsb = queryString.substring(2,4);
        return Integer.decode("0x"+stringLsb).byteValue();
    }
}
