package pl.cendrzak.szymon.SubaruSsm;

import java.io.ByteArrayOutputStream;

public class EngineQuery extends ParameterQuery {

    public EngineQuery(String queryString)
    {
        this.queryString = queryString;
    }

    protected ByteArrayOutputStream getBytes()
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(0x78);
        bytes.write(getMsb());
        bytes.write(getLsb());
        bytes.write(0x00);
        return bytes;
    }
}
