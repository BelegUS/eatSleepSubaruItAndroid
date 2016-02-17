package pl.cendrzak.szymon.SubaruSsm;

public class SubaruQuery {
    String queryString;

    public SubaruQuery(String queryString)
    {
        this.queryString = queryString;
    }

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

    public byte[] getQueryBytes()
    {
        return new byte[] { 0x02, 0x78, getMsb(), getLsb(), 0x17 };
    }

    public static byte[] getEndConnectionQuery() {
        return new byte[] { 0x02, 0x12, 0x00, 0x00, 0x17};
    }
}
