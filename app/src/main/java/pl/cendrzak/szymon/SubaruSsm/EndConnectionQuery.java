package pl.cendrzak.szymon.SubaruSsm;

import java.io.ByteArrayOutputStream;

public class EndConnectionQuery {
    protected ByteArrayOutputStream getBytes()
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(0x12);
        bytes.write(0x00);
        bytes.write(0x00);
        bytes.write(0x00);
        return bytes;
    }
}
