package pl.cendrzak.szymon.SubaruSsm;

import java.io.ByteArrayOutputStream;

public class TransmissionQuery extends ParameterQuery {

    public TransmissionQuery(String queryString)
    {
        this.queryString = queryString;
    }

    protected ByteArrayOutputStream getBytes()
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(0x45);
        bytes.write(getMsb());
        bytes.write(getLsb());
        bytes.write(0x00);
        return bytes;
    }
}
